package stellics.filter;

import com.fs.starfarer.api.campaign.CommDirectoryEntryAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;

public class MarketHasAdministrator implements MarketFilter {

    public boolean match(MarketAPI market) {
        for (CommDirectoryEntryAPI entry : market.getCommDirectory().getEntriesCopy()) {
            PersonAPI person = (PersonAPI) entry.getEntryData();

            if (person.getPostId().equals(Ranks.POST_FREELANCE_ADMIN)) {
                return true;
            }
        }

        return false;
    }
}
