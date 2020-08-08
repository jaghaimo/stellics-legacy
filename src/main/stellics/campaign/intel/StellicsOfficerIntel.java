package stellics.campaign.intel;

import java.awt.Color;
import java.util.Set;

import com.fs.starfarer.api.campaign.CommDirectoryEntryAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.intel.MessageIntel;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.SectorMapAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class StellicsOfficerIntel extends MessageIntel {

    MarketAPI market;

    String personality;

    public StellicsOfficerIntel(MarketAPI m, String p) {
        market = m;
        personality = p;
    }

    @Override
    public void createIntelInfo(TooltipMakerAPI info, ListInfoMode mode) {
        String location = market.getName();
        String system = market.getStarSystem().getName();

        Color colorTitle = getTitleColor(mode);
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
        String size = String.valueOf(market.getSize());
        String faction = market.getFaction().getDisplayNameWithArticle();

        Color colorText = Misc.getTextColor();
        Color colorFaction = market.getFaction().getColor();
        Color colorHighlight = Misc.getHighlightColor();

        info.addImage(market.getFaction().getLogo(), width, 128, 10f);
        info.addPara(getMessage(location, system), 10f, colorText, colorHighlight, location, system);

        LabelAPI label = info.addPara(
                "This market has the following officers available for hire:",
                10f);
        label.setHighlight(size, faction);
        label.setHighlightColors(colorHighlight, colorFaction);
        bullet(info);

        for (CommDirectoryEntryAPI entry : market.getCommDirectory().getEntriesCopy()) {
            PersonAPI person = (PersonAPI) entry.getEntryData();

            if (!person.getPostId().equals("mercenary")) {
                continue;
            }

            String curPersonality = person.getPersonalityAPI().getDisplayName().toLowerCase();
            info.addPara(
                person.getRank() + " " + person.getNameString() + ", " + curPersonality,
                3f
                );
        }
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

    private String getMessage(String location, String system) {
        return "Nearest " + personality + " officer available for hire is located on " + location + " in " + system + ".";
    }

    private String getTitle() {
        return "StellNET Intel";
    }
}
