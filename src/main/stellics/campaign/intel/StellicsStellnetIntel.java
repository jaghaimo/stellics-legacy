package stellics.campaign.intel;

import java.awt.Color;
import java.util.Set;

import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.intel.MessageIntel;
import com.fs.starfarer.api.ui.SectorMapAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

abstract public class StellicsStellnetIntel extends MessageIntel {

    protected MarketAPI market;

    public StellicsStellnetIntel(MarketAPI m) {
        market = m;
    }

    @Override
    public void createIntelInfo(TooltipMakerAPI info, ListInfoMode mode) {
        Color colorTitle = getTitleColor(mode);
        info.addPara(getTitle(), colorTitle, 0f);
        bullet(info);
        addToInfo(info, 3f);
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info, float width, float height) {
        info.addImage(market.getFaction().getLogo(), width, 128, 10f);
        addToInfo(info, 10f);
    }

    @Override
    public String getIcon() {
        return market.getFaction().getCrest();
    }

    @Override
    public SectorEntityToken getMapLocation(SectorMapAPI map) {
        return market.getPrimaryEntity();
    }

    @Override
    public String getSmallDescriptionTitle() {
        return getTitle();
    }

    @Override
    public Set<String> getIntelTags(SectorMapAPI map) {
        Set<String> tags = super.getIntelTags(map);
        tags.add(market.getFactionId());
        tags.add("StellNET");

        return tags;
    }

    abstract protected void addToInfo(TooltipMakerAPI info, float pad);

    protected String getTitle() {
        return "StellNET Intel";
    }
}
