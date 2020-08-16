package stellics.helper;

import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;

import stellics.Constants;
import stellics.campaign.listeners.StellicsFeeListener;

public class StorageHelper {

    public static boolean add(MarketAPI market) {
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

    public static SubmarketAPI get() {
        return findFirst();
    }

    public static boolean exists() {
        return findFirst() != null;
    }

    public static boolean has(MarketAPI market) {
        return market.hasSubmarket(Constants.STORAGE);
    }

    public static boolean isFounding(MarketAPI market) {
        String marketId = "";

        // first branch will "own" a submarket
        if (market.hasSubmarket(Constants.STORAGE)) {
            marketId = market.getSubmarket(Constants.STORAGE).getMarket().getId();
        }

        return market.getId().equals(marketId);
    }

    public static void registerFeeListener() {
        SubmarketAPI submarket = get();

        if (submarket == null) {
            return;
        }

        MarketAPI market = submarket.getMarket();
        Global.getSector().getListenerManager().addListener(new StellicsFeeListener(market), true);
    }

    public static boolean remove(MarketAPI market) {
        boolean hasRemoved = false;

        if (market.hasSubmarket(Constants.STORAGE)) {
            market.removeSubmarket(Constants.STORAGE);
            hasRemoved = true;
        }

        return hasRemoved;
    }

    private static SubmarketAPI findFirst() {
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
