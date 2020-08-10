package stellics.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CommDirectoryEntryAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;

import stellics.campaign.econ.MarketComparator;

public class MarketHelper {

    public List<MarketAPI> findMarketsWithOfficers(String personality) {
        List<MarketAPI> markets = new ArrayList<MarketAPI>();

        for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
            for (CommDirectoryEntryAPI entry: m.getCommDirectory().getEntriesCopy()) {
                PersonAPI person = (PersonAPI) entry.getEntryData();

                if (!person.getPostId().equals("mercenary")) {
                    continue;
                }

                if (personality.equals(person.getPersonalityAPI().getId())) {
                    markets.add(m);
                }
            }
        }

        sortMarkets(markets);

        return markets;
    }

    public List<MarketAPI> findMarketsWithSubmarket(String submarket) {
        List<MarketAPI> markets = new ArrayList<MarketAPI>();

        for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
            if (m.hasSubmarket(submarket)) {
                markets.add(m);
            }
        }

        sortMarkets(markets);

        return markets;
    }

    public MarketAPI getNearestMarket(List<MarketAPI> markets) {
        if (!markets.isEmpty()) {
            sortMarkets(markets);

            return markets.get(0);
        }

        return null;
    }

    public void sortMarkets(List<MarketAPI> markets) {
        Collections.sort(markets, new MarketComparator());
    }
}
