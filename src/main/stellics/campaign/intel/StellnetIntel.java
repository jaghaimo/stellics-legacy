package stellics.campaign.intel;

import java.util.Set;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.SectorMapAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import stellics.Constants;

public class StellnetIntel extends BaseIntelPlugin implements BaseStellnetIntel {

    private FactionAPI faction;
    private SectorEntityToken sectorEntityToken;
    private IntelEntity intel;

    public StellnetIntel(FactionAPI f, SectorEntityToken s, IntelEntity i) {
        faction = f;
        sectorEntityToken = s;
        intel = i;
        endingTimeRemaining = 14f;

        Global.getSector().getIntelManager().addIntel(this);
        Global.getSector().addScript(this);
    }

    @Override
    public void createIntelInfo(TooltipMakerAPI info, ListInfoMode mode) {
        info.addPara(intel.getIntelTitle(), getTitleColor(mode), 0f);
        bullet(info);
        info.addPara(intel.getIntelInfo(), 3f, getBulletColorForMode(mode), Misc.getHighlightColor(), intel.getEntity(),
                sectorEntityToken.getName(), intel.getStarSystemName(""));
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info, float width, float height) {
        info.addImage(faction.getLogo(), width, 128, 10f);
        intel.createSmallDescription(info);
    }

    @Override
    public String getIcon() {
        return intel.getIcon();
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
        tags.add(Constants.TAG_STELLNET);

        return tags;
    }

    @Override
    protected void advanceImpl(float amount) {
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
        Global.getSector().removeScript(this);
    }

    public void updateTextPanel(TextPanelAPI textPanel) {
        textPanel.addPara(intel.getIntelInfo() + ".");
    }
}
