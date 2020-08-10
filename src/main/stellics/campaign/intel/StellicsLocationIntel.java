package stellics.campaign.intel;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class StellicsLocationIntel extends StellicsStellnetIntel {

    public StellicsLocationIntel(MarketAPI m) {
        super(m);
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info, float width, float height) {
        super.createSmallDescription(info, width, height);
        String size = String.valueOf(market.getSize());
        String faction = market.getFaction().getDisplayNameWithArticle();
        LabelAPI label = info.addPara(
                "It is a size " + size + " market owned by " + faction + " with the following industries:",
                10f);
        label.setHighlight(size, faction);
        label.setHighlightColors(Misc.getHighlightColor(), market.getFaction().getColor());
        bullet(info);

        for (Industry industry : market.getIndustries()) {
            info.addPara(industry.getCurrentName(), 3f);
        }
    }

    protected void addToInfo(TooltipMakerAPI info, float pad) {
        String location = market.getName();
        String system = market.getStarSystem().getName();
        info.addPara(
                "Nearest operational branch is located on " + location + " in " + system + ".",
                pad,
                Misc.getTextColor(),
                Misc.getHighlightColor(),
                location, system);
    }
}
