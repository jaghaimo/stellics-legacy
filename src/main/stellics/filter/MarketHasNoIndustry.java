package stellics.filter;

import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class MarketHasNoIndustry implements MarketFilter {

    private String industry;

    public MarketHasNoIndustry(String i) {
        industry = i;
    }

    public boolean match(MarketAPI market) {
        return !market.hasIndustry(industry);
    }
}
