package stellics.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterManager {

    private List<MarketFilter> marketFilters;
    private List<SubmarketFilter> submarketFilters;

    public FilterManager() {
        marketFilters = new ArrayList<MarketFilter>();
        submarketFilters = new ArrayList<SubmarketFilter>();
    }

    public boolean add(MarketFilter mf) {
        return marketFilters.add(mf);
    }

    public boolean add(SubmarketFilter sf) {
        return submarketFilters.add(sf);
    }

    public void clear() {
        marketFilters.clear();
        submarketFilters.clear();
    }

    public List<MarketFilter> getMarketFiltersCopy() {
        return new ArrayList<MarketFilter>(marketFilters);
    }

    public List<SubmarketFilter> getSubmarketFiltersCopy() {
        return new ArrayList<SubmarketFilter>(submarketFilters);
    }
}
