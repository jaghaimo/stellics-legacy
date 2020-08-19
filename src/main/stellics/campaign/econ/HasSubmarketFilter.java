package stellics.campaign.econ;

import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class HasSubmarketFilter implements MarketFilter {

    private String submarket;

    public HasSubmarketFilter(String s) {
        submarket = s;
    }

    public boolean match(MarketAPI market) {
        return market.hasSubmarket(submarket);
    }
}
