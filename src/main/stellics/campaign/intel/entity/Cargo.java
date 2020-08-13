package stellics.campaign.intel.entity;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class Cargo extends Stellnet {

    public Cargo(String c, MarketAPI m) {
        super(c, m);
    }

    @Override
    public String getIntelTitle() {
        return "StellNET Cargo Intel";
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info) {
        super.createSmallDescription(info);
    }
}
