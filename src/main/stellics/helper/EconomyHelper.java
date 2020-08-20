package stellics.helper;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;

import stellics.filter.Filter;
import stellics.filter.MarketFilter;
import stellics.filter.SubmarketFilter;

public class EconomyHelper {

    public static List<SubmarketAPI> getSubmarkets(List<MarketAPI> markets, List<SubmarketFilter> filters) {
        List<SubmarketAPI> submarkets = new ArrayList<SubmarketAPI>();

        for (MarketAPI m : markets) {
            skipSubmarket: for (SubmarketAPI s : m.getSubmarketsCopy()) {
                for (Filter<SubmarketAPI>f : filters) {
                    if (!f.match(s)) {
                        continue skipSubmarket;
                    }
                }
                submarkets.add(s);
            }
        }

        return submarkets;
    }

    public static List<MarketAPI> getMarkets(List<MarketFilter> filters) {
        return getMarkets(Global.getSector().getEconomy().getMarketsCopy(), filters);
    }

    public static List<MarketAPI> getMarkets(List<MarketAPI> ms, List<MarketFilter> filters) {
        List<MarketAPI> markets = new ArrayList<MarketAPI>();

        skipMarket: for (MarketAPI m : ms) {
            for (MarketFilter f : filters) {
                if (!f.match(m)) {
                    continue skipMarket;
                }
            }
            markets.add(m);
        }

        return markets;
    }
}
