package stellics.filter;

import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class MarketHasSubmarket implements MarketFilter {

    private String submarket;

    public MarketHasSubmarket(String s) {
        submarket = s;
    }

    public boolean match(MarketAPI market) {
        return market.hasSubmarket(submarket);
    }
}
