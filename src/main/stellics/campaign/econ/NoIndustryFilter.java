package stellics.campaign.econ;

import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class NoIndustryFilter implements MarketFilter {

    private String industry;

    public NoIndustryFilter(String i) {
        industry = i;
    }

    public boolean match(MarketAPI market) {
        return !market.hasIndustry(industry);
    }
}
