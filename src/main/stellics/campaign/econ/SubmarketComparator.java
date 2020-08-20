package stellics.campaign.econ;

import java.util.Comparator;

import com.fs.starfarer.api.campaign.econ.SubmarketAPI;

/**
 * Compares two submarkets based on the parent market distance to player fleet.
 */
public class SubmarketComparator implements Comparator<SubmarketAPI> {

    MarketComparator marketComparator;

    public SubmarketComparator() {
        marketComparator = new MarketComparator();
    }

    @Override
    public int compare(SubmarketAPI s1, SubmarketAPI s2) {
        return marketComparator.compare(s1.getMarket(), s2.getMarket());
    }
}
