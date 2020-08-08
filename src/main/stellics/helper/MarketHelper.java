package stellics.helper;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

public class MarketHelper {

    public List<MarketAPI> findMarketsWithSubmarket(String submarket) {
        List<MarketAPI> markets = new ArrayList<MarketAPI>();

        for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
            if (m.hasSubmarket(submarket)) {
                markets.add(m);
            }
        }

        return markets;
    }

    public MarketAPI findNearestMarket(List<MarketAPI> markets) {
        MarketAPI market = null;

        Vector2f playerLocationHs = Global.getSector().getPlayerFleet().getLocationInHyperspace();
        Vector2f playerLocation = Global.getSector().getPlayerFleet().getLocation();

        float minDistanceHs = Float.MAX_VALUE;
        float minDistance = Float.MAX_VALUE;

        for (MarketAPI m : markets) {
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
}
