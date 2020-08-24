package stellics.helper;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.FleetDataAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

import stellics.filter.CargoStackFilter;
import stellics.filter.FleetMemberFilter;
import stellics.filter.MarketFilter;
import stellics.filter.SubmarketFilter;

public class EconomyHelper {

    public static List<CargoStackAPI> getCargoStacks(CargoAPI cargo, List<CargoStackFilter> filters) {
        List<CargoStackAPI> cargoStacks = new ArrayList<CargoStackAPI>();

        skipCargoStack: for (CargoStackAPI c : cargo.getStacksCopy()) {
            for (CargoStackFilter csf : filters) {
                if (!csf.match(c)) {
                    continue skipCargoStack;
                }
            }
            cargoStacks.add(c);
        }

        return cargoStacks;
    }

    public static List<FleetMemberAPI> getFleetMembers(FleetDataAPI fleetData, List<FleetMemberFilter> filters) {
        List<FleetMemberAPI> fleetMembers = new ArrayList<FleetMemberAPI>();

        skipFleetMemeber: for (FleetMemberAPI f : fleetData.getMembersListCopy()) {
            for (FleetMemberFilter fmf : filters) {
                if (!fmf.match(f)) {
                    continue skipFleetMemeber;
                }
            }
            fleetMembers.add(f);
        }

        return fleetMembers;
    }

    public static List<SubmarketAPI> getSubmarkets(List<MarketAPI> markets, List<SubmarketFilter> filters) {
        List<SubmarketAPI> submarkets = new ArrayList<SubmarketAPI>();

        for (MarketAPI m : markets) {
            skipSubmarket: for (SubmarketAPI s : m.getSubmarketsCopy()) {
                for (SubmarketFilter sf : filters) {
                    if (!sf.match(s)) {
                        continue skipSubmarket;
                    }
                }
                submarkets.add(s);
            }
        }

        return submarkets;
    }

    public static List<MarketAPI> getMarkets(List<MarketFilter> filters) {
        return getMarkets(Global.getSector().getEconomy().getMarketsCopy(), filters);
    }

    public static List<MarketAPI> getMarkets(List<MarketAPI> ms, List<MarketFilter> filters) {
        List<MarketAPI> markets = new ArrayList<MarketAPI>();

        skipMarket: for (MarketAPI m : ms) {
            for (MarketFilter mf : filters) {
                if (!mf.match(m)) {
                    continue skipMarket;
                }
            }
            markets.add(m);
        }

        return markets;
    }
}
