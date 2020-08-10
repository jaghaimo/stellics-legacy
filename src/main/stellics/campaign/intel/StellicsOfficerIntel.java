package stellics.campaign.intel;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.campaign.CommDirectoryEntryAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class StellicsOfficerIntel extends StellicsStellnetIntel {

    private String personality;

    public StellicsOfficerIntel(MarketAPI m, String p) {
        super(m);
        personality = p;
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info, float width, float height) {
        super.createSmallDescription(info, width, height);
        String size = String.valueOf(market.getSize());
        String faction = market.getFaction().getDisplayNameWithArticle();
        LabelAPI label = info.addPara(
                "It is a size " + size + " market owned by " + faction + ".",
                10f);
        label.setHighlight(size, faction);
        label.setHighlightColors(Misc.getHighlightColor(), market.getFaction().getColor());
        listOfficers(info);
        unindent(info);
    }

    protected void addToInfo(TooltipMakerAPI info, float pad) {
        String location = market.getName();
        String system = market.getStarSystem().getName();
        info.addPara(
                "Nearest " + personality + " officer can be found on " + location + " in " + system + ".",
                pad,
                Misc.getTextColor(),
                Misc.getHighlightColor(),
                personality,
                location,
                system
                );
    }

    private List<String> getAllOfficers() {
        ArrayList<String> officers = new ArrayList<String>();

        for (CommDirectoryEntryAPI entry : market.getCommDirectory().getEntriesCopy()) {
            PersonAPI person = (PersonAPI) entry.getEntryData();

            if (!person.getPostId().equals("mercenary")) {
                continue;
            }

            String curPersonality = person.getPersonalityAPI().getDisplayName().toLowerCase();
            officers.add(person.getRank() + " " + person.getNameString() + ", " + curPersonality);
        }

        return officers;
    }

    private void listOfficers(TooltipMakerAPI info) {
        List<String> officers = getAllOfficers();

        if (officers.isEmpty()) {
            return;
        }

        info.addPara("This market has the following officers available for hire:", 10f);

        for (String officer : officers) {
            bullet(info);
            info.addPara(officer, 3f);
        }
    }
}
