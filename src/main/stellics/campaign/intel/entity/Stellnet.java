package stellics.campaign.intel.entity;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import stellics.campaign.intel.IntelEntity;
import stellics.campaign.intel.StellnetIntel;

public class Stellnet implements IntelEntity {

    protected String entity;
    protected MarketAPI market;

    public Stellnet(String e, MarketAPI m) {
        entity = e;
        market = m;
    }

    public String getEntity() {
        return entity;
    }

    public String getIcon() {
        return market.getFaction().getCrest();
    }

    public String getIntelTitle() {
        return "StellNET Intel";
    }

    public String getIntelInfo() {
        return "Nearest " + entity + " can be found on " + market.getName() + getStarSystemName(" in ");
    }

    public void createSmallDescription(StellnetIntel plugin, TooltipMakerAPI info) {
        LabelAPI label1 = info.addPara(getIntelInfo() + ".", 10f);
        label1.setHighlight(entity, market.getName(), getStarSystemName(""));
        label1.setHighlightColor(Misc.getHighlightColor());

        String size = String.valueOf(market.getSize());
        FactionAPI faction = market.getFaction();
        String factionName = faction.getDisplayNameWithArticle();

        LabelAPI label2 = info.addPara("It is a size " + size + " market owned by " + factionName + ".", 10f);
        label2.setHighlight(size, factionName);
        label2.setHighlightColors(Misc.getHighlightColor(), faction.getColor());
    }

    public String getStarSystemName(String prefix) {
        StarSystemAPI starSystem = market.getStarSystem();

        if (starSystem != null) {
            return prefix + starSystem.getName();
        }

        return "";
    }
}
