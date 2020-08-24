package stellics.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;

import stellics.campaign.StellnetDialogOption;
import stellics.campaign.econ.FleetComparator;
import stellics.campaign.econ.MarketComparator;
import stellics.campaign.econ.SubmarketComparator;
import stellics.filter.CargoStackFilter;
import stellics.filter.FilterManager;
import stellics.filter.FleetMemberFilter;

public class MarketHelper {

    public static List<CargoStackAPI> findItems(FilterManager filterManager, StellnetDialogOption option) {
        List<MarketAPI> markets = EconomyHelper.getMarkets(filterManager.listMarketFilters());
        List<SubmarketAPI> submarkets = EconomyHelper.getSubmarkets(markets, filterManager.listSubmarketFilters());
        List<CargoStackFilter> cargoStackFilters = filterManager.listCargoFilters(option);
        List<CargoStackAPI> cargoStacks = new ArrayList<CargoStackAPI>();

        for (SubmarketAPI s : submarkets) {
            try {
                ((BaseSubmarketPlugin) s.getPlugin()).updateCargoPrePlayerInteraction();
            } catch (Exception exception) {
                continue;
            }

            cargoStacks.addAll(EconomyHelper.getCargoStacks(s.getCargo(), cargoStackFilters));
        }

        return cargoStacks;
    }

    public static List<FleetMemberAPI> findShips(FilterManager filterManager, StellnetDialogOption option) {
        List<MarketAPI> markets = EconomyHelper.getMarkets(filterManager.listMarketFilters());
        List<SubmarketAPI> submarkets = EconomyHelper.getSubmarkets(markets, filterManager.listSubmarketFilters());
        List<FleetMemberFilter> fleetMemeberFilters = filterManager.listFleetFilters(option);
        List<FleetMemberAPI> fleetMembers = new ArrayList<FleetMemberAPI>();

        for (SubmarketAPI s : submarkets) {
            try {
                ((BaseSubmarketPlugin) s.getPlugin()).updateCargoPrePlayerInteraction();
            } catch (Exception exception) {
                continue;
            }

            fleetMembers.addAll(EconomyHelper.getFleetMembers(s.getCargo().getMothballedShips(), fleetMemeberFilters));
        }

        Collections.sort(fleetMembers, new FleetComparator());

        return fleetMembers;
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
}
