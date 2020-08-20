package stellics.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;

import stellics.Constants;
import stellics.campaign.econ.FleetComparator;
import stellics.campaign.econ.MarketComparator;
import stellics.campaign.econ.SubmarketComparator;
import stellics.filter.SubmarketFilter;

public class MarketHelper {

    public static List<CargoStackAPI> findItems(List<MarketAPI> markets, List<SubmarketFilter> filters,
            String category) {
        List<CargoStackAPI> cargoStacks = new ArrayList<CargoStackAPI>();
        List<SubmarketAPI> submarkets = EconomyHelper.getSubmarkets(markets, filters);

        for (SubmarketAPI s : submarkets) {
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

        return cargoStacks;
    }

    public static List<FleetMemberAPI> findShips(List<MarketAPI> markets, List<SubmarketFilter> filters, String size) {
        List<FleetMemberAPI> fleet = new ArrayList<FleetMemberAPI>();
        List<SubmarketAPI> submarkets = EconomyHelper.getSubmarkets(markets, filters);

        for (SubmarketAPI s : submarkets) {
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

        Collections.sort(fleet, new FleetComparator());

        return fleet;
    }

    public static MarketAPI getNearestMarket(List<MarketAPI> markets) {
        if (!markets.isEmpty()) {
            Collections.sort(markets, new MarketComparator());

            return markets.get(0);
        }

        return null;
    }

    public static SubmarketAPI getNearestSubmarket(List<SubmarketAPI> submarkets) {
        if (!submarkets.isEmpty()) {
            Collections.sort(submarkets, new SubmarketComparator());

            return submarkets.get(0);
        }

        return null;
    }

    // TODO: CargoStackAPI filter
    private static boolean doesMatchCategory(CargoStackAPI c, String category) {
        switch (category) {
            case Constants.WEAPON:
                return c.isWeaponStack();

            case Constants.FIGHTER:
                return c.isFighterWingStack();

            case Constants.MODSPEC:
                return c.isSpecialStack() && c.getSpecialDataIfSpecial().getId().equals(category);

            case Constants.BLUEPRINT:
                return c.isSpecialStack() && c.getSpecialDataIfSpecial().getId().endsWith("_bp");

            default:
                return false;
        }
    }

    // TODO: FleetMemeberFilter
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
