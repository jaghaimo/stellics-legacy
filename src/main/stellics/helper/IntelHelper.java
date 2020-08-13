package stellics.helper;

import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import stellics.campaign.intel.BaseStellnetIntel;
import stellics.campaign.intel.BatchStellnetIntel;
import stellics.campaign.intel.EmptyIntel;
import stellics.campaign.intel.IntelEntity;
import stellics.campaign.intel.StellnetIntel;
import stellics.campaign.intel.entity.Cargo;
import stellics.campaign.intel.entity.Industry;
import stellics.campaign.intel.entity.Officer;

public class IntelHelper {

    public static BaseStellnetIntel getCargoIntel(CargoAPI cargo) {
        if (cargo.isEmpty()) {
            return new EmptyIntel("Query cancelled - no items selected.");
        }

        cargo.sort();
        BatchStellnetIntel batchStellnetIntel = new BatchStellnetIntel();

        for (CargoStackAPI cargoStack : cargo.getStacksCopy()) {
            String cargoName = cargoStack.getDisplayName();

            try {
                List<MarketAPI> markets = MarketHelper.findMarketsWithItem(cargoStack);
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

    public static BaseStellnetIntel getIndustryIntel(String industryId, boolean notDisrupted) {
        try {
            List<MarketAPI> markets = MarketHelper.findMarketsWithIndustry(industryId, notDisrupted);
            MarketAPI market = MarketHelper.getNearestMarket(markets);
            String industryName = market.getIndustry(industryId).getCurrentName();
            IntelEntity intel = new Industry(industryName, market);

            return new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), intel);

        } catch (Exception exception) {
            String industryName = Global.getSettings().getIndustrySpec(industryId).getName();

            return new EmptyIntel("Could not find any operational " + industryName + ".");
        }
    }

    public static BaseStellnetIntel getOfficerIntel(String personality) {
        try {
            List<MarketAPI> markets = MarketHelper.findMarketsWithOfficers(personality);
            MarketAPI market = MarketHelper.getNearestMarket(markets);
            IntelEntity intel = new Officer(personality, market);

            return new StellnetIntel(market.getFaction(), market.getPrimaryEntity(), intel);
        } catch (Exception exception) {
            return new EmptyIntel("Could not find any " + personality + " officers.");
        }
    }
}
