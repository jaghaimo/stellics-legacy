package stellics.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.CommDirectoryEntryAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;

import stellics.campaign.econ.MarketComparator;
import stellics.Constants;

public class MarketHelper {

    public static List<CargoStackAPI> findItemsInMarkets(String category) {
        List<CargoStackAPI> cargoStacks = new ArrayList<CargoStackAPI>();

        for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
            for (SubmarketAPI s : m.getSubmarketsCopy()) {
                try {
                    ((BaseSubmarketPlugin) s.getPlugin()).updateCargoPrePlayerInteraction();
                } catch (Exception exception) {
                    continue;
                }

                for (CargoStackAPI c : s.getCargo().getStacksCopy()) {
                    if (doesMatchCategory(c, category)) {
                        cargoStacks.add(c);
                    }
                }
            }
        }

        return cargoStacks;
    }

    public static List<MarketAPI> findMarketsWithItem(CargoStackAPI cargoStack) {
        List<MarketAPI> markets = new ArrayList<MarketAPI>();

        foundMarket:
        for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
            for (SubmarketAPI s : m.getSubmarketsCopy()) {
                for (CargoStackAPI c : s.getCargo().getStacksCopy()) {
                    // ideally this should be done based on some unique identifier
                    if (cargoStack.getDisplayName().equals(c.getDisplayName())) {
                        markets.add(m);
                        continue foundMarket;
                    }
                }
            }
        }

        return markets;
    }

    public static List<MarketAPI> findMarketsWithIndustry(String industry, boolean notDisrupted) {
        List<MarketAPI> markets = new ArrayList<MarketAPI>();

        for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
            if (m.hasIndustry(industry)) {
                if (notDisrupted && m.getIndustry(industry).isDisrupted()) {
                    continue;
                }

                markets.add(m);
            }
        }

        return markets;
    }

    public static List<MarketAPI> findMarketsWithOfficers(String personality) {
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

        return markets;
    }

    public static MarketAPI getNearestMarket(List<MarketAPI> markets) {
        if (!markets.isEmpty()) {
            sortMarkets(markets);

            return markets.get(0);
        }

        return null;
    }

    private static boolean doesMatchCategory(CargoStackAPI c, String category) {
        switch (category) {
            case Constants.WEAPON:
                return c.isWeaponStack();

            case Constants.FIGHTER:
                return c.isFighterWingStack();

            case Constants.MODSPEC:
            case Constants.BLUEPRINT:
                return c.isSpecialStack() && c.getSpecialDataIfSpecial().getId().equals(category);

            default:
                return false;
        }
    }

    private static void sortMarkets(List<MarketAPI> markets) {
        Collections.sort(markets, new MarketComparator());
    }
}
