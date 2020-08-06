package stellics.campaign.abilities;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.abilities.BaseDurationAbility;

import stellics.StorageService;
import stellics.campaign.intel.StellicsLocationIntel;

public class StellicsLocation extends BaseDurationAbility {

    @Override
    protected void activateImpl() {
        StorageService storageService = new StorageService();
        MarketAPI market = storageService.findNearestMarket();

        if (market != null) {
            StellicsLocationIntel intel = new StellicsLocationIntel(market);
            Global.getSector().getIntelManager().addIntel(intel);
        }
    }

    @Override
    protected void applyEffect(float arg0, float arg1) {
    }

    @Override
    protected void cleanupImpl() {
    }

    @Override
    protected void deactivateImpl() {
    }
}
