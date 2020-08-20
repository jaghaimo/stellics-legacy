package stellics.filter;

import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class MarketNonHostile implements MarketFilter {

    public boolean match(MarketAPI market) {
        return market.getFaction().getRelToPlayer().getLevel().isAtWorst(RepLevel.SUSPICIOUS);
    }
}
