package stellics.campaign.intel.entity;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class Ship extends Stellnet {

    private String shipClass;

    public Ship(String e, String s, MarketAPI m) {
        super(e, m);
        shipClass = s;
    }

    @Override
    public String getIntelTitle() {
        return "StellNET Ship Intel";
    }

    @Override
    public String getIntelInfo() {
        String name = market.getName();
        StarSystemAPI starSystem = market.getStarSystem();

        if (starSystem != null) {
            name += " in " + starSystem.getName();
        }

        return entity + ", the " + shipClass + "-class ship can be found on " + name;
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info) {
        super.createSmallDescription(info);
    }
}
