package stellics.filter;

import com.fs.starfarer.api.campaign.econ.SubmarketAPI;

public class SubmarketNonCustom implements SubmarketFilter {

    public boolean match(SubmarketAPI submarket) {
        return submarket.getPlugin().isOpenMarket() || submarket.getPlugin().isBlackMarket();
    }
}
