package stellics.campaign.econ;

import com.fs.starfarer.api.campaign.CommDirectoryEntryAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;

public class HasOfficerFilter implements MarketFilter {

    private String personality;

    public HasOfficerFilter(String p) {
        personality = p;
    }

    public boolean match(MarketAPI market) {
        for (CommDirectoryEntryAPI entry : market.getCommDirectory().getEntriesCopy()) {
            PersonAPI person = (PersonAPI) entry.getEntryData();

            if (!person.getPostId().equals("mercenary")) {
                continue;
            }

            if (personality.equals(person.getPersonalityAPI().getId())) {
                return true;
            }
        }

        return false;
    }
}
