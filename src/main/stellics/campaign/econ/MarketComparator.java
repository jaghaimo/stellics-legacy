package stellics.campaign.econ;

import java.util.Comparator;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.util.Misc;

import org.lwjgl.util.vector.Vector2f;

/**
 * Compares two markets based on the distance to player.
 */
public class MarketComparator implements Comparator<MarketAPI> {

    @Override
    public int compare(MarketAPI m1, MarketAPI m2) {
       return compare(
                m1.getPrimaryEntity().getLocationInHyperspace(),
                m2.getPrimaryEntity().getLocationInHyperspace()
                );
    }

    private int compare(Vector2f m1, Vector2f m2) {
        float mDistance1 = Misc.getDistanceToPlayerLY(m1);
        float mDistance2 = Misc.getDistanceToPlayerLY(m2);

        return (int) (mDistance1 - mDistance2);
    }
}
