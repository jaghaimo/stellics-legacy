package stellics.campaign.intel;

import java.awt.Color;

import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.intel.MessageIntel;
import com.fs.starfarer.api.ui.SectorMapAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class StellicsLocationIntel extends MessageIntel {

    MarketAPI market;

    public StellicsLocationIntel(MarketAPI m) {
        market = m;
    }

    @Override
    public void createIntelInfo(TooltipMakerAPI info, ListInfoMode mode) {
        String location = market.getName();
        String system = market.getStarSystem().getName();

        Color colorTitle = market.getFaction().getBaseUIColor();
        Color colorText = getBulletColorForMode(mode);
        Color colorHighlight = Misc.getHighlightColor();

        info.addPara(getTitle(), colorTitle, 0f);
        bullet(info);
        info.addPara(getMessage(location, system), 3f, colorText, colorHighlight, location, system);
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info, float width, float height) {
        String location = market.getName();
        String system = market.getStarSystem().getName();

        info.addImage(market.getFaction().getCrest(), width, 128, 10f);
        info.addPara(getMessage(location, system), 10f);
    }

    @Override
    public String getIcon() {
        return market.getFaction().getCrest();
    }

    @Override
    public SectorEntityToken getMapLocation(SectorMapAPI map) {
        return market.getPrimaryEntity();
    }

    private String getMessage(String location, String system) {
        return "Nearest operational branch is located on " + location + " in " + system + ".";
    }

    private String getTitle() {
        return "Stellar Logistics Branch";
    }
}
