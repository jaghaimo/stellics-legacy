package stellics.helper;

import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

import stellics.campaign.intel.TextPanelAwareIntel;
import stellics.campaign.intel.BatchStellnetIntel;
import stellics.campaign.intel.EmptyIntel;
import stellics.campaign.intel.IntelEntity;
import stellics.campaign.intel.StellnetIntel;
import stellics.campaign.intel.entity.Administrator;
import stellics.campaign.intel.entity.Cargo;
import stellics.campaign.intel.entity.Industry;
import stellics.campaign.intel.entity.Officer;
import stellics.campaign.intel.entity.Ship;
import stellics.filter.FilterManager;
import stellics.filter.MarketFilter;
import stellics.filter.SubmarketFilter;
import stellics.filter.SubmarketHasCargoStack;
import stellics.filter.SubmarketHasShip;

public class IntelHelper {

    public static TextPanelAwareIntel getFleetIntel(FilterManager filterManager, List<FleetMemberAPI> fleet) {
        if (fleet.isEmpty()) {
            return new EmptyIntel("Query cancelled - no ships selected.");
        }

        BatchStellnetIntel batchStellnetIntel = new BatchStellnetIntel();
        List<MarketFilter> mFilter = filterManager.listMarketFilters();

        for (FleetMemberAPI ship : fleet) {
            String shipName = ship.getShipName();
            List<SubmarketFilter> sFilter = filterManager.listSubmarketFilters();
            sFilter.add(new SubmarketHasShip(ship));
            List<MarketAPI> markets = EconomyHelper.getMarkets(mFilter);
            List<SubmarketAPI> submarkets = EconomyHelper.getSubmarkets(markets, sFilter);
            SubmarketAPI submarket = MarketHelper.getNearestSubmarket(submarkets);

            try {
                MarketAPI market = submarket.getMarket();
                IntelEntity entity = new Ship(shipName, ship, market);
                TextPanelAwareIntel intel = new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), entity);
                batchStellnetIntel.add(intel);
            } catch (Exception exception) {
            }
        }

        return batchStellnetIntel;
    }

    public static TextPanelAwareIntel getCargoIntel(FilterManager filterManager, CargoAPI cargo) {
        if (cargo.isEmpty()) {
            return new EmptyIntel("Query cancelled - no items selected.");
        }

        cargo.sort();
        BatchStellnetIntel batchStellnetIntel = new BatchStellnetIntel();
        List<MarketFilter> mFilter = filterManager.listMarketFilters();

        for (CargoStackAPI cargoStack : cargo.getStacksCopy()) {
            String cargoName = cargoStack.getDisplayName();
            List<SubmarketFilter> sFilter = filterManager.listSubmarketFilters();
            sFilter.add(new SubmarketHasCargoStack(cargoStack));
            List<MarketAPI> markets = EconomyHelper.getMarkets(mFilter);
            List<SubmarketAPI> submarkets = EconomyHelper.getSubmarkets(markets, sFilter);
            SubmarketAPI submarket = MarketHelper.getNearestSubmarket(submarkets);

            try {
                MarketAPI market = submarket.getMarket();
                IntelEntity entity = new Cargo(cargoName, market);
                TextPanelAwareIntel intel = new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), entity);
                batchStellnetIntel.add(intel);
            } catch (Exception exception) {
            }
        }

        return batchStellnetIntel;
    }

    public static TextPanelAwareIntel getIndustryIntel(List<MarketFilter> filters, String industryId) {
        try {
            List<MarketAPI> markets = EconomyHelper.getMarkets(filters);
            MarketAPI market = MarketHelper.getNearestMarket(markets);
            String industryName = market.getIndustry(industryId).getCurrentName();
            IntelEntity intel = new Industry(industryName, market);

            return new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), intel);

        } catch (Exception exception) {
            String industryName = Global.getSettings().getIndustrySpec(industryId).getName();

            return new EmptyIntel("Could not find any operational " + industryName + ".");
        }
    }

    public static TextPanelAwareIntel getAdministratorIntel(List<MarketFilter> filters) {
        try {
            List<MarketAPI> markets = EconomyHelper.getMarkets(filters);
            MarketAPI market = MarketHelper.getNearestMarket(markets);
            IntelEntity intel = new Administrator(market);

            return new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), intel);
        } catch (Exception exception) {
            return new EmptyIntel("Could not find any administrators.");
        }
    }

    public static TextPanelAwareIntel getOfficerIntel(List<MarketFilter> filters, String personality) {
        try {
            List<MarketAPI> markets = EconomyHelper.getMarkets(filters);
            MarketAPI market = MarketHelper.getNearestMarket(markets);
            IntelEntity intel = new Officer(personality, market);

            return new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), intel);
        } catch (Exception exception) {
            return new EmptyIntel("Could not find any " + personality + " officers.");
        }
    }
}
