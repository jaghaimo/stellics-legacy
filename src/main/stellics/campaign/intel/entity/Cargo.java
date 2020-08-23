package stellics.campaign.intel.entity;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class Cargo extends Stellnet {

    public Cargo(String e, MarketAPI m) {
        super(e, m);
    }

    @Override
    public String getIcon() {
        return Global.getSettings().getSpriteName("intel", "cargo");
    }

    @Override
    public String getIntelTitle() {
        return "StellNET Cargo Intel";
    }
}
