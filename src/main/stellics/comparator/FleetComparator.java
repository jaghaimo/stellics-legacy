package stellics.comparator;

import java.util.Comparator;

import com.fs.starfarer.api.fleet.FleetMemberAPI;

/**
 * Compares two ships based on their hull name.
 */
public class FleetComparator implements Comparator<FleetMemberAPI> {

    @Override
    public int compare(FleetMemberAPI f1, FleetMemberAPI f2) {
        String f1Name = f1.getHullSpec().getHullName();
        String f2Name = f2.getHullSpec().getHullName();

        return f1Name.compareTo(f2Name);
    }
}
