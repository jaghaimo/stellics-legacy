package stellics.helper;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

import stellics.campaign.intel.BaseStellnetIntel;
import stellics.campaign.intel.BatchStellnetIntel;
import stellics.campaign.intel.EmptyIntel;
import stellics.campaign.intel.IntelEntity;
import stellics.campaign.intel.StellnetIntel;
import stellics.campaign.intel.entity.Cargo;
import stellics.campaign.intel.entity.Industry;
import stellics.campaign.intel.entity.Officer;
import stellics.campaign.intel.entity.Ship;
import stellics.filter.MarketHasIndustry;
import stellics.filter.MarketHasOfficer;
import stellics.filter.MarketHasShip;
import stellics.filter.SubmarketFilter;
import stellics.filter.FilterManager;
import stellics.filter.MarketFilter;
import stellics.filter.MarketHasCargoStack;

public class IntelHelper {

    public static BaseStellnetIntel getFleetIntel(FilterManager filterManager, List<FleetMemberAPI> fleet) {
        if (fleet.isEmpty()) {
            return new EmptyIntel("Query cancelled - no ships selected.");
        }

        BatchStellnetIntel batchStellnetIntel = new BatchStellnetIntel();
        List<SubmarketFilter> sFilter = filterManager.getSubmarketFiltersCopy();

        for (FleetMemberAPI ship : fleet) {
            String shipName = ship.getShipName();
            String shipClass = ship.getHullSpec().getHullName();
            List<MarketFilter> mFilter = filterManager.getMarketFiltersCopy();
            mFilter.add(new MarketHasShip(ship));

            try {
                List<MarketAPI> markets = EconomyHelper.getMarkets(mFilter);
                List<SubmarketAPI> submarkets = EconomyHelper.getSubmarkets(markets, sFilter);
                SubmarketAPI submarket = MarketHelper.getNearestSubmarket(submarkets);
                MarketAPI market = submarket.getMarket();
                IntelEntity entity = new Ship(shipName, shipClass, market);
                BaseStellnetIntel intel = new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), entity);
                batchStellnetIntel.add(intel);
            } catch (Exception exception) {
                BaseStellnetIntel intel = new EmptyIntel("Could not find any market selling " + shipName + ".");
                batchStellnetIntel.add(intel);
            }
        }

        return batchStellnetIntel;
    }

    public static BaseStellnetIntel getCargoIntel(FilterManager filterManager, CargoAPI cargo) {
        if (cargo.isEmpty()) {
            return new EmptyIntel("Query cancelled - no items selected.");
        }

        cargo.sort();
        BatchStellnetIntel batchStellnetIntel = new BatchStellnetIntel();
        List<SubmarketFilter> sFilter = filterManager.getSubmarketFiltersCopy();

        for (CargoStackAPI cargoStack : cargo.getStacksCopy()) {
            String cargoName = cargoStack.getDisplayName();
            List<MarketFilter> mFilter = filterManager.getMarketFiltersCopy();
            mFilter.add(new MarketHasCargoStack(cargoStack));

            try {
                List<MarketAPI> markets = EconomyHelper.getMarkets(mFilter);
                List<SubmarketAPI> submarkets = EconomyHelper.getSubmarkets(markets, sFilter);
                SubmarketAPI submarket = MarketHelper.getNearestSubmarket(submarkets);
                MarketAPI market = submarket.getMarket();
                IntelEntity entity = new Cargo(cargoName, market);
                BaseStellnetIntel intel = new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), entity);
                batchStellnetIntel.add(intel);
            } catch (Exception exception) {
                BaseStellnetIntel intel = new EmptyIntel("Could not find any market selling " + cargoName + ".");
                batchStellnetIntel.add(intel);
            }
        }

        return batchStellnetIntel;
    }

    public static BaseStellnetIntel getIndustryIntel(List<MarketFilter> filters, String industryId,
            boolean notDisrupted) {
        try {
            filters.add(new MarketHasIndustry(industryId, notDisrupted));
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

    public static BaseStellnetIntel getOfficerIntel(List<MarketFilter> filters, String personality) {
        try {
            filters.add(new MarketHasOfficer(personality));
            List<MarketAPI> markets = EconomyHelper.getMarkets(filters);
            MarketAPI market = MarketHelper.getNearestMarket(markets);
            IntelEntity intel = new Officer(personality, market);

            return new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), intel);
        } catch (Exception exception) {
            return new EmptyIntel("Could not find any " + personality + " officers.");
        }
    }
}
