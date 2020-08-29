package stellics.filter;

import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import stellics.campaign.StellnetDialogOption;

public class MarketNonHostile implements MarketFilter {

    private StellnetDialogOption option;

    public MarketNonHostile(StellnetDialogOption o) {
        option = o;
    }

    public boolean match(MarketAPI market) {
        if (option == StellnetDialogOption.MARKET_FACTION_NON_HOSTILE) {
            return market.getFaction().getRelToPlayer().getLevel().isAtWorst(RepLevel.SUSPICIOUS);
        }

        return true;
    }
}
