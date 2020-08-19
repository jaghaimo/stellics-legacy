package stellics.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;

import stellics.campaign.econ.FleetComparator;
import stellics.campaign.econ.MarketComparator;
import stellics.campaign.econ.MarketFilter;
import stellics.Constants;

public class MarketHelper {

    public static List<CargoStackAPI> findItems(List<MarketAPI> markets, String category) {
        List<CargoStackAPI> cargoStacks = new ArrayList<CargoStackAPI>();

        for (MarketAPI m : markets) {
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

    public static List<FleetMemberAPI> findShips(List<MarketAPI> markets, String size) {
        List<FleetMemberAPI> fleet = new ArrayList<FleetMemberAPI>();

        for (MarketAPI m : markets) {
            for (SubmarketAPI s : m.getSubmarketsCopy()) {
                try {
                    ((BaseSubmarketPlugin) s.getPlugin()).updateCargoPrePlayerInteraction();
                } catch (Exception exception) {
                    continue;
                }

                for (FleetMemberAPI f : s.getCargo().getMothballedShips().getMembersListCopy()) {
                    if (doesMatchSize(f, size)) {
                        fleet.add(f);
                    }
                }
            }
        }

        Collections.sort(fleet, new FleetComparator());

        return fleet;
    }

    public static List<MarketAPI> findMarkets(List<MarketFilter> filters) {
        List<MarketAPI> markets = new ArrayList<MarketAPI>();

        skipMarket: for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
            for (MarketFilter f : filters) {
                if (!f.match(m)) {
                    continue skipMarket;
                }
            }
            markets.add(m);
        }

        return markets;
    }

    public static MarketAPI getNearestMarket(List<MarketAPI> markets) {
        if (!markets.isEmpty()) {
            Collections.sort(markets, new MarketComparator());

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

    private static boolean doesMatchSize(FleetMemberAPI f, String size) {
        switch (size) {
            case Constants.FRIGATE:
                return f.isFrigate();

            case Constants.DESTROYER:
                return f.isDestroyer();

            case Constants.CRUISER:
                return f.isCruiser();

            case Constants.CAPITAL:
                return f.isCapital();

            default:
                return false;
        }
    }
}
