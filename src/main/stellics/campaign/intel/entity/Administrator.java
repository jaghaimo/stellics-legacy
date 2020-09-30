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

public class Administrator extends Stellnet {

    private List<String> administrators = new ArrayList<String>();

    public Administrator(MarketAPI m) {
        super("administrator", m);
        addAdministrators();
    }

    @Override
    public String getIcon() {
        return Global.getSettings().getSpriteName("intel", "staff");
    }

    @Override
    public String getIntelTitle() {
        return "StellNET Administrator Intel";
    }

    @Override
    public void createSmallDescription(StellnetIntel plugin, TooltipMakerAPI info) {
        super.createSmallDescription(plugin, info);

        if (administrators.isEmpty()) {
            return;
        }

        info.addPara("This market has the following administrators available for hire:", 10f);
        plugin.bullet(info);

        for (String administrator : administrators) {
            info.addPara(administrator, 3f);
        }

        plugin.unindent(info);
    }

    private void addAdministrators() {
        for (CommDirectoryEntryAPI entry : market.getCommDirectory().getEntriesCopy()) {
            PersonAPI person = (PersonAPI) entry.getEntryData();

            if (person.getPostId().equals(Ranks.POST_FREELANCE_ADMIN)) {
                administrators.add(person.getRank() + " " + person.getNameString());
            }
        }
    }
}
