package stellics.campaign.intel.entity;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.util.Misc;

public class Ship extends Stellnet {

    private FleetMemberAPI ship;

    public Ship(String e, FleetMemberAPI s, MarketAPI m) {
        super(e, m);
        ship = s;
    }

    @Override
    public String getIcon() {
        return Global.getSettings().getSpriteName("intel", "ship");
    }

    @Override
    public String getIntelTitle() {
        return "StellNET Ship Intel";
    }

    @Override
    public String getIntelInfo() {
        String name = market.getName() + getStarSystemName(" in ");
        String shipClass = ship.getHullSpec().getHullNameWithDashClass();
        String shipCost = Misc.getDGSCredits(ship.getBaseBuyValue());

        return entity + ", the " + shipClass + " ship can be bought on " + name + " for " + shipCost;
    }
}
