package stellics.campaign.econ;

import java.util.Comparator;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

/**
 * Compares two markets based on the distance to player fleet.
 */
public class MarketComparator implements Comparator<MarketAPI> {

    Vector2f playerLocation;

    public MarketComparator() {
        playerLocation = Global.getSector().getPlayerFleet().getLocationInHyperspace();
    }

    @Override
    public int compare(MarketAPI m1, MarketAPI m2) {
       return compare(
                m1.getPrimaryEntity().getLocationInHyperspace(),
                m2.getPrimaryEntity().getLocationInHyperspace()
                );
    }

    private int compare(Vector2f m1, Vector2f m2) {
        float mDistance1 = MathUtils.getDistanceSquared(m1, playerLocation);
        float mDistance2 = MathUtils.getDistanceSquared(m2, playerLocation);

        return (int) (mDistance1 - mDistance2);
    }
}
