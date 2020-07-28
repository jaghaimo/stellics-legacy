package stellics.campaign.econ.impl;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;

import stellics.StorageService;

public class StellicsBranch extends BaseIndustry {

    private StorageService storageService;

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

        if (getStorageService().isFounding(market)) {
            return;
        }

        if (getStorageService().remove(market)) {
            writeMessage("Stellar Logistics Branch on %s has been disrupted and ceased its operations.", market.getName());
        }
    }

    @Override
    protected void disruptionFinished() {
        super.disruptionFinished();

        if (getStorageService().isFounding(market)) {
            return;
        }

        if (getStorageService().add(market)) {
            writeMessage("Stellar Logistics Branch on %s has resumed its operations.", market.getName());
        }
    }

    @Override
    public void finishBuildingOrUpgrading() {
        super.finishBuildingOrUpgrading();

        if (getStorageService().add(market)) {
            writeMessage("Stellar Logistics Branch on %s is now open.", market.getName());
        }
    }

    @Override
    public void notifyBeingRemoved(MarketAPI.MarketInteractionMode mode, boolean forUpgrade) {
        super.notifyBeingRemoved(mode, forUpgrade);

        if (getStorageService().remove(market)) {
            writeMessage("Stellar Logistics Branch on %s has been closed.", market.getName());
        }
    }

    private StorageService getStorageService() {
        if (storageService == null) {
            storageService = new StorageService();
        }

        return storageService;
    }

    private void writeMessage(String message, String location) {
        Global.getSector().getCampaignUI().addMessage(String.format(message, location));
    }
}
