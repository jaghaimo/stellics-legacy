package stellics;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.LocationAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;

import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

import stellics.campaign.listeners.StellicsFeeListener;

public class StorageService {

    public boolean add(MarketAPI market) {
        if (market.hasSubmarket(Constants.STORAGE)) {
            return false;
        }

        SubmarketAPI stellicsStorage = findFirst();

        if (stellicsStorage != null) {
            market.addSubmarket(stellicsStorage);
        } else {
            market.addSubmarket(Constants.STORAGE);
            registerFeeListener();
        }

        return true;
    }

    public MarketAPI findNearestMarket() {
        MarketAPI market = null;
        List<MarketAPI> marketCopy = Global.getSector().getEconomy().getMarketsCopy();

        Vector2f playerLocationHs = Global.getSector().getPlayerFleet().getLocationInHyperspace();
        Vector2f playerLocation = Global.getSector().getPlayerFleet().getLocation();

        float minDistanceHs = Float.MAX_VALUE;
        float minDistance = Float.MAX_VALUE;

        for (MarketAPI m : marketCopy) {
            // skip systems with no submarket
            if (!has(m)) {
                continue;
            }

            float curDistanceHs = MathUtils.getDistanceSquared(m.getLocationInHyperspace(), playerLocationHs);
            float curDistance = MathUtils.getDistanceSquared(m.getLocation(), playerLocation);

            // this market is in a closer system
            if (curDistanceHs < minDistanceHs) {
                minDistanceHs = curDistanceHs;
                minDistance = curDistance;
                market = m;
                continue;
            }

            // same system as best one but closer
            if (curDistanceHs == minDistanceHs && curDistance < minDistance) {
                minDistanceHs = curDistanceHs;
                minDistance = curDistance;
                market = m;
                continue;
            }
        }

        return market;
    }

    public SubmarketAPI get() {
        return findFirst();
    }

    public boolean exists() {
        return findFirst() != null;
    }

    public boolean has(MarketAPI market) {
        return market.hasSubmarket(Constants.STORAGE);
    }

    public boolean isFounding(MarketAPI market) {
        String marketId = "";

        // first branch will "own" a submarket
        if (market.hasSubmarket(Constants.STORAGE)) {
            marketId = market.getSubmarket(Constants.STORAGE).getMarket().getId();
        }

        return market.getId().equals(marketId);
    }

    public void registerFeeListener() {
        SubmarketAPI submarket = get();

        if (submarket == null) {
            return;
        }

        MarketAPI market = submarket.getMarket();
        Global.getSector().getListenerManager().addListener(new StellicsFeeListener(market), true);
    }

    public boolean remove(MarketAPI market) {
        boolean hasRemoved = false;

        if (market.hasSubmarket(Constants.STORAGE)) {
            market.removeSubmarket(Constants.STORAGE);
            hasRemoved = true;
        }

        return hasRemoved;
    }

    private SubmarketAPI findFirst() {
        SubmarketAPI stellicsStorage;
        List<MarketAPI> marketCopy = Global.getSector().getEconomy().getMarketsCopy();

        for (MarketAPI marketApi : marketCopy) {
            stellicsStorage = marketApi.getSubmarket(Constants.STORAGE);
            if (stellicsStorage != null) {
                return stellicsStorage;
            }
        }

        return null;
    }
}
