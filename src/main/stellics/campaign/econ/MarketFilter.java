package stellics.campaign.econ;

import com.fs.starfarer.api.campaign.econ.MarketAPI;

public interface MarketFilter {

    public boolean match(MarketAPI market);
}
