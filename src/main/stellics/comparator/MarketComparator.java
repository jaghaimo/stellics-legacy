package stellics.comparator;

import java.util.Comparator;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.util.Misc;

import org.lwjgl.util.vector.Vector2f;

/**
 * Compares two markets based on the distance to player fleet.
 */
public class MarketComparator implements Comparator<MarketAPI> {

    CampaignFleetAPI playerFleet;

    public MarketComparator() {
        playerFleet = Global.getSector().getPlayerFleet();
    }

    @Override
    public int compare(MarketAPI m1, MarketAPI m2) {
        int comparison = compare(m1.getPrimaryEntity().getLocationInHyperspace(),
                m2.getPrimaryEntity().getLocationInHyperspace(), playerFleet.getLocationInHyperspace());

        // same system, use local coordinates
        if (comparison == 0) {
            comparison = compare(m1.getPrimaryEntity().getLocation(), m2.getPrimaryEntity().getLocation(),
                    playerFleet.getLocation());
        }

        return comparison;
    }

    private int compare(Vector2f m1, Vector2f m2, Vector2f p) {
        float mDistance1 = Misc.getDistance(m1, p);
        float mDistance2 = Misc.getDistance(m2, p);

        return (int) (mDistance1 - mDistance2);
    }
}
