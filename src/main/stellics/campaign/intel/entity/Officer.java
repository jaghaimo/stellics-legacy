package stellics.campaign.intel.entity;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.campaign.CommDirectoryEntryAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class Officer extends Stellnet {

    private List<String> officers = new ArrayList<String>();

    public Officer(String p, MarketAPI m) {
        super(p + " officer", m);
        addOfficers();
    }

    @Override
    public String getIntelTitle() {
        return "StellNET Officer Intel";
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info) {
        super.createSmallDescription(info);

        if (officers.isEmpty()) {
            return;
        }

        info.addPara("This market has the following officers available for hire:", 10f);

        for (String officer : officers) {
            info.addPara(" - " + officer, 3f);

        }
    }

    private void addOfficers() {
        for (CommDirectoryEntryAPI entry : market.getCommDirectory().getEntriesCopy()) {
            PersonAPI person = (PersonAPI) entry.getEntryData();

            if (!person.getPostId().equals("mercenary")) {
                continue;
            }

            String curPersonality = person.getPersonalityAPI().getDisplayName().toLowerCase();
            officers.add(person.getRank() + " " + person.getNameString() + ", " + curPersonality);
        }
    }
}