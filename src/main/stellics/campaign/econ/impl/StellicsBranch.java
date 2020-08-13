package stellics.campaign.econ.impl;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;

import stellics.helper.StorageHelper;
import stellics.campaign.intel.StellnetIntel;
import stellics.campaign.intel.entity.Branch;

public class StellicsBranch extends BaseIndustry {

    private StorageHelper storageHelper;

    @Override
    public void apply() {
        super.apply(true);

        int size = market.getSize();
        demand(Commodities.CREW, size - 1);
        demand(Commodities.FUEL, size - 1);

        if (!isFunctional()) {
            demand.clear();
            supply.clear();
        }
    }

    @Override
    public void notifyDisrupted() {
        super.notifyDisrupted();

        if (getStorageHelper().isFounding(market)) {
            return;
        }

        if (getStorageHelper().remove(market)) {
            queueIntel(Branch.Action.DISRUPT);
        }
    }

    @Override
    protected void disruptionFinished() {
        super.disruptionFinished();

        if (getStorageHelper().isFounding(market)) {
            return;
        }

        if (getStorageHelper().add(market)) {
            queueIntel(Branch.Action.RESUME);
        }
    }

    @Override
    public void finishBuildingOrUpgrading() {
        super.finishBuildingOrUpgrading();

        if (getStorageHelper().add(market)) {
            queueIntel(Branch.Action.OPEN);
        }
    }

    @Override
    public void notifyBeingRemoved(MarketAPI.MarketInteractionMode mode, boolean forUpgrade) {
        super.notifyBeingRemoved(mode, forUpgrade);

        if (getStorageHelper().remove(market)) {
            queueIntel(Branch.Action.CLOSE);
        }
    }

    private StorageHelper getStorageHelper() {
        if (storageHelper == null) {
            storageHelper = new StorageHelper();
        }

        return storageHelper;
    }

    private void queueIntel(Branch.Action action) {
        Branch branch = new Branch(market, action);
        StellnetIntel intel = new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), branch);
        Global.getSector().getIntelManager().queueIntel(intel);
    }
}
