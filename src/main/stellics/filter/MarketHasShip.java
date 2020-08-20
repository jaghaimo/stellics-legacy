package stellics.filter;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

public class MarketHasShip implements MarketFilter {

    private FleetMemberAPI ship;

    public MarketHasShip(FleetMemberAPI s) {
        ship = s;
    }

    public boolean match(MarketAPI market) {
        for (SubmarketAPI s : market.getSubmarketsCopy()) {
            for (FleetMemberAPI f : s.getCargo().getMothballedShips().getMembersListCopy()) {
                if (ship.getId().equals(f.getId())) {
                    return true;
                }
            }
        }

        return false;
    }
}
