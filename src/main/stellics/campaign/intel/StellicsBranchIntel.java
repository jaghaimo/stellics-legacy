package stellics.campaign.intel;

import java.awt.Color;
import java.util.Set;

import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.intel.MessageIntel;
import com.fs.starfarer.api.ui.SectorMapAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class StellicsBranchIntel extends MessageIntel {

    public enum Action {
        OPEN("Branch has been opened.", "opened"),
        CLOSE("Branch has been closed.", "closed"),
        DISRUPT("Branch has been temporarily closed.", "temporarily closed"),
        RESUME("Branch has been reopened.", "reopened");

        String message;

        String highlight;

        Action(String m, String h) {
            message = m;
            highlight = h;
        }

        public String getMessage() {
            return message;
        }

        public String getHighlight() {
            return highlight;
        }
    }

    private MarketAPI market;

    private Action action;

    public StellicsBranchIntel(MarketAPI m, Action a) {
        market = m;
        action = a;
    }

    @Override
    public void createIntelInfo(TooltipMakerAPI info, ListInfoMode mode) {
        Color colorTitle = market.getFaction().getBaseUIColor();
        Color colorText = getBulletColorForMode(mode);
        Color colorHighlight = Misc.getHighlightColor();

        info.addPara(getTitle(), colorTitle, 0f);
        bullet(info);
        info.addPara(action.getMessage(), 3f, colorText, colorHighlight, action.getHighlight());
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info, float width, float height) {
        info.addImage(market.getFaction().getLogo(), width, 128, 10f);
        info.addPara(action.getMessage(), 10f);
    }

    @Override
    public String getIcon() {
        return market.getFaction().getLogo();
    }

    @Override
    public Set<String> getIntelTags(SectorMapAPI map) {
        Set<String> tags = super.getIntelTags(map);
        tags.add(market.getFactionId());
        tags.add("StellNET");

        return tags;
    }

    @Override
    public SectorEntityToken getMapLocation(SectorMapAPI map) {
        super.getMapLocation(map);

        return market.getPrimaryEntity();
    }

    @Override
    public String getSortString() {
        return getTitle();
    }

    @Override
    public String getSmallDescriptionTitle() {
        return getTitle();
    }

    private String getTitle() {
        return "Stellar Logisticts Branch at " + market.getName();
    }
}