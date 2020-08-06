package stellics;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import org.json.JSONObject;

public class StellicsModPlugin extends BaseModPlugin {

    private final String SETTINGS_FILE = "stellics_settings.json";

    private JSONObject settings;

    private StellicsSettings stellicsSettings;

    private StorageService storageService;

    @Override
    public void onApplicationLoad() throws Exception {
        settings = Global.getSettings().loadJSON(SETTINGS_FILE);
    }

    @Override
    public void onNewGameAfterEconomyLoad() {
        stellicsSettings = new StellicsSettings(settings);
        storageService = new StorageService();

        seedPrism();
        seedFactions();
    }

    @Override
    public void onGameLoad(boolean newGame) {
        storageService = new StorageService();
        storageService.registerFeeListener();
    }

    private void seedFaction(MarketAPI market) {
        int marketSize = market.getSize();

        if (stellicsSettings.getSeedMinimalSize() > marketSize) {
            return;
        }

        int submarketCount = market.getSubmarketsCopy().size();

        if (stellicsSettings.getSeedMaxSubmarkets() < submarketCount) {
            return;
        }

        String faction = market.getFactionId();
        int seedFactions = stellicsSettings.getSeedFactions();
        double seedProbability = stellicsSettings.getSeedProbability();

        if (seedFactions == 1 && faction.equals("independent")) {
            seedMarket(market, seedProbability);
        } else if (seedFactions == 2 && !faction.equals("pirates")) {
            seedMarket(market, seedProbability);
        } else if (seedFactions == 3) {
            seedMarket(market, seedProbability);
        }
    }

    private void seedFactions() {
        // don't add branches and storages to any factions
        if (stellicsSettings.getSeedFactions() == 0) {
            return;
        }

        // spread branches and storages across the galaxy
        for (MarketAPI marketCopy : Global.getSector().getEconomy().getMarketsCopy()) {
            // we need an actual market instead of copy
            MarketAPI market = Global.getSector().getEconomy().getMarket(marketCopy.getId());
            seedFaction(market);
        }
    }

    private void seedMarket(MarketAPI market, double seedProbability) {
        if (seedProbability > Math.random() && !storageService.has(market)) {
            market.addIndustry(Constants.BRANCH);
            storageService.add(market);
        }
    }

    private void seedPrism() {
        MarketAPI market = Global.getSector().getEconomy().getMarket("nex_prismFreeport");

        if (market == null) {
            return;
        }

        // we have a Nexerelin game with Prism Freeport enabled, and we want to use it
        if (stellicsSettings.isSeedPrism() && !storageService.has(market)) {
            seedMarket(market, 1);
        }
    }
}
