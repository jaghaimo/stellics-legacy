package stellics.campaign.intel;

import java.util.Set;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.SectorMapAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class StellnetIntel extends BaseIntelPlugin implements BaseStellnetIntel {

    private FactionAPI faction;
    private SectorEntityToken sectorEntityToken;
    private IntelEntity intel;

    public StellnetIntel(FactionAPI f, SectorEntityToken s, IntelEntity i) {
        faction = f;
        sectorEntityToken = s;
        intel = i;
        endingTimeRemaining = 7f;
    }

    @Override
    public void createIntelInfo(TooltipMakerAPI info, ListInfoMode mode) {
        info.addPara(intel.getIntelTitle(), getTitleColor(mode), 0f);
        bullet(info);
        info.addPara(intel.getIntelInfo(), 3f, getBulletColorForMode(mode), Misc.getHighlightColor(), intel.getEntity(),
                sectorEntityToken.getName(), getStarSystemName());
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info, float width, float height) {
        info.addImage(faction.getLogo(), width, 128, 10f);
        intel.createSmallDescription(info);
    }

    @Override
    public String getIcon() {
        return faction.getCrest();
    }

    @Override
    public SectorEntityToken getMapLocation(SectorMapAPI map) {
        return sectorEntityToken;
    }

    @Override
    public String getSmallDescriptionTitle() {
        return intel.getIntelTitle();
    }

    @Override
    public FactionAPI getFactionForUIColors() {
        return faction;
    }

    @Override
    public Set<String> getIntelTags(SectorMapAPI map) {
        Set<String> tags = super.getIntelTags(map);
        tags.add(faction.getId());
        tags.add("StellNET");

        return tags;
    }

    @Override
    protected void advanceImpl(float amount) {
        // week of active + week day of ending
        float days = Global.getSector().getClock().convertToDays(amount);
        endingTimeRemaining -= days;
        if (!isEnding() && endingTimeRemaining <= 0) {
            endingTimeRemaining = 7f;
            ending = true;
        }
    }

    @Override
    protected void notifyEnded() {
        Global.getSector().getIntelManager().removeIntel(this);
    }

    public void trigger() {
        Global.getSector().getIntelManager().addIntel(this);
        Global.getSector().addScript(this);
    }

    public void updateTextPanel(TextPanelAPI textPanel) {
        textPanel.addPara(intel.getIntelInfo());
    }

    private String getStarSystemName() {
        StarSystemAPI starSystem = sectorEntityToken.getStarSystem();

        if (starSystem != null) {
            return starSystem.getName();
        }

        return "";
    }
}
