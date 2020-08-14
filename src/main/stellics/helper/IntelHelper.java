package stellics.helper;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import stellics.campaign.econ.*;
import stellics.campaign.intel.*;
import stellics.campaign.intel.entity.*;

public class IntelHelper {

    public static BaseStellnetIntel getCargoIntel(List<MarketFilter> filters, CargoAPI cargo) {
        if (cargo.isEmpty()) {
            return new EmptyIntel("Query cancelled - no items selected.");
        }

        cargo.sort();
        BatchStellnetIntel batchStellnetIntel = new BatchStellnetIntel();

        for (CargoStackAPI cargoStack : cargo.getStacksCopy()) {
            String cargoName = cargoStack.getDisplayName();
            List<MarketFilter> f = new ArrayList<MarketFilter>(filters);
            f.add(new HasCargoFilter(cargoStack));

            try {
                List<MarketAPI> markets = MarketHelper.findMarkets(f);
                MarketAPI market = MarketHelper.getNearestMarket(markets);
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

    public static BaseStellnetIntel getIndustryIntel(List<MarketFilter> filters, String industryId, boolean notDisrupted) {
        try {
            filters.add(new HasIndustryFilter(industryId, notDisrupted));
            List<MarketAPI> markets = MarketHelper.findMarkets(filters);
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
            filters.add(new HasOfficerFilter(personality));
            List<MarketAPI> markets = MarketHelper.findMarkets(filters);
            MarketAPI market = MarketHelper.getNearestMarket(markets);
            IntelEntity intel = new Officer(personality, market);

            return new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), intel);
        } catch (Exception exception) {
            return new EmptyIntel("Could not find any " + personality + " officers.");
        }
    }
}
