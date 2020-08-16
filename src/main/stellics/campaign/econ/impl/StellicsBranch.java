package stellics.campaign.econ.impl;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;

import stellics.helper.StorageHelper;
import stellics.campaign.intel.StellnetIntel;
import stellics.campaign.intel.entity.Branch;

public class StellicsBranch extends BaseIndustry {

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
    public void reapply() {
        super.reapply();

        if (isFunctional()) {
            StorageHelper.add(market);
        }
    }

    @Override
    public void notifyDisrupted() {
        super.notifyDisrupted();

        if (StorageHelper.isFounding(market)) {
            return;
        }

        if (StorageHelper.remove(market)) {
            queueIntel(Branch.Action.DISRUPT);
        }
    }

    @Override
    protected void disruptionFinished() {
        super.disruptionFinished();

        if (StorageHelper.isFounding(market)) {
            return;
        }

        if (StorageHelper.add(market)) {
            queueIntel(Branch.Action.RESUME);
        }
    }

    @Override
    public void finishBuildingOrUpgrading() {
        super.finishBuildingOrUpgrading();

        if (StorageHelper.add(market)) {
            queueIntel(Branch.Action.OPEN);
        }
    }

    @Override
    public void notifyBeingRemoved(MarketAPI.MarketInteractionMode mode, boolean forUpgrade) {
        super.notifyBeingRemoved(mode, forUpgrade);

        if (StorageHelper.remove(market)) {
            queueIntel(Branch.Action.CLOSE);
        }
    }

    private void queueIntel(Branch.Action action) {
        Branch branch = new Branch(market, action);
        StellnetIntel intel = new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), branch);
        Global.getSector().getIntelManager().queueIntel(intel);
    }
}
