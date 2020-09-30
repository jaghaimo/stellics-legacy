package stellics.campaign.intel.entity;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import stellics.campaign.intel.StellnetIntel;

public class Industry extends Stellnet {

    public Industry(String e, MarketAPI m) {
        super(e, m);
    }

    @Override
    public String getIcon() {
        return Global.getSettings().getSpriteName("intel", "branch");
    }

    @Override
    public String getIntelTitle() {
        return "StellNET Industry Intel";
    }

    @Override
    public void createSmallDescription(StellnetIntel plugin, TooltipMakerAPI info) {
        super.createSmallDescription(plugin, info);
        info.addPara("This market has the following industries:", 10f);
        plugin.bullet(info);

        for (com.fs.starfarer.api.campaign.econ.Industry industry : market.getIndustries()) {
            info.addPara(industry.getCurrentName(), 3f);
        }

        plugin.unindent(info);
    }
}
