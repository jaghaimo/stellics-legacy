package stellics.campaign.econ.impl;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;

import java.util.List;

public class StellicsBranch extends BaseIndustry {

    @Override
    public void apply() {
        super.apply(true);

        int size = market.getSize();
        demand(Commodities.CREW, size - 1);
        demand(Commodities.FUEL, size - 1);

        if (!isFunctional()) {
            supply.clear();
        }
    }

    @Override
    public boolean canShutDown() {
        return false;
    }

    @Override
    public void finishBuildingOrUpgrading() {
        super.finishBuildingOrUpgrading();

        if (market.getSubmarket("STELLICS_STORAGE") == null) {
            initializeStorage();
        }
    }

    @Override
    public boolean showShutDown() {
        return false;
    }

    private void initializeStorage() {
        SubmarketAPI stellicsStorage;
        List<MarketAPI> marketCopy = Global.getSector().getEconomy().getMarketsCopy();

        for (MarketAPI marketApi : marketCopy) {
            stellicsStorage = marketApi.getSubmarket("STELLICS_STORAGE");
            if (stellicsStorage != null) {
                market.addSubmarket(stellicsStorage);
                return;
            }
        }

        market.addSubmarket("STELLICS_STORAGE");
    }
}
