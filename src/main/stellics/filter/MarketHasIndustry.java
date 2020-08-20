package stellics.filter;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class MarketHasIndustry implements MarketFilter {

    private String industry;
    private boolean nonDisrupted;

    public MarketHasIndustry(String i, boolean nd) {
        industry = i;
        nonDisrupted = nd;
    }

    public boolean match(MarketAPI market) {
        Industry i = market.getIndustry(industry);

        if (i == null) {
            return false;
        }

        if (nonDisrupted) {
            return !i.isDisrupted();
        }

        return true;
    }
}
