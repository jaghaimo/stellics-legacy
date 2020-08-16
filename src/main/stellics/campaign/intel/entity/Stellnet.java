package stellics.campaign.intel.entity;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import stellics.campaign.intel.IntelEntity;

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

    public String getIntelTitle() {
        return "StellNET Intel";
    }

    public String getIntelInfo() {
        String name = market.getName();
        StarSystemAPI starSystem = market.getStarSystem();

        if (starSystem != null) {
            name += " in " + starSystem.getName();
        }

        return "Nearest " + entity + " can be found on " + name;
    }

    public void createSmallDescription(TooltipMakerAPI info) {
        LabelAPI label1 = info.addPara(getIntelInfo(), 10f);
        label1.setHighlight(entity, market.getName(), market.getStarSystem().getName());
        label1.setHighlightColor(Misc.getHighlightColor());


        String size = String.valueOf(market.getSize());
        FactionAPI faction = market.getFaction();
        String factionName = faction.getDisplayNameWithArticle();

        LabelAPI label2 = info.addPara(
                "It is a size " + size + " market owned by " + factionName + ".",
                10f);
        label2.setHighlight(size, factionName);
        label2.setHighlightColors(Misc.getHighlightColor(), faction.getColor());
    }
}
