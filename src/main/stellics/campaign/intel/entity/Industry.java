package stellics.campaign.intel.entity;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class Industry extends Stellnet {

    public Industry(String i, MarketAPI m) {
        super(i, m);
    }

    @Override
    public String getIntelTitle() {
        return "StellNET Industry Intel";
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info) {
        super.createSmallDescription(info);
        info.addPara("This market has the following industries:", 10f);

        for (com.fs.starfarer.api.campaign.econ.Industry industry : market.getIndustries()) {
            info.addPara(" - " + industry.getCurrentName(), 3f);
        }
    }
}
