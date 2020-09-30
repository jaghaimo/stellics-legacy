package stellics.campaign.intel.entity;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CommDirectoryEntryAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import stellics.campaign.intel.StellnetIntel;

public class Officer extends Stellnet {

    private List<String> officers = new ArrayList<String>();

    public Officer(String e, MarketAPI m) {
        super(e + " officer", m);
        addOfficers();
    }

    @Override
    public String getIcon() {
        return Global.getSettings().getSpriteName("intel", "staff");
    }

    @Override
    public String getIntelTitle() {
        return "StellNET Officer Intel";
    }

    @Override
    public void createSmallDescription(StellnetIntel plugin, TooltipMakerAPI info) {
        super.createSmallDescription(plugin, info);

        if (officers.isEmpty()) {
            return;
        }

        info.addPara("This market has the following officers available for hire:", 10f);
        plugin.bullet(info);

        for (String officer : officers) {
            info.addPara(officer, 3f);
        }

        plugin.unindent(info);
    }

    private void addOfficers() {
        for (CommDirectoryEntryAPI entry : market.getCommDirectory().getEntriesCopy()) {
            PersonAPI person = (PersonAPI) entry.getEntryData();

            if (person.getPostId().equals(Ranks.POST_MERCENARY)) {
                String curPersonality = person.getPersonalityAPI().getDisplayName().toLowerCase();
                officers.add(person.getRank() + " " + person.getNameString() + ", " + curPersonality);
            }
        }
    }
}
