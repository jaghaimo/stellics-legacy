package stellics.filter;

import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

public class SubmarketHasShip implements SubmarketFilter {

    private FleetMemberAPI ship;

    public SubmarketHasShip(FleetMemberAPI s) {
        ship = s;
    }

    public boolean match(SubmarketAPI submarket) {
        for (FleetMemberAPI f : submarket.getCargo().getMothballedShips().getMembersListCopy()) {
            if (ship.getId().equals(f.getId())) {
                return true;
            }
        }

        return false;
    }
}
