package stellics;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class StellicsModPlugin extends BaseModPlugin {

    private final String SETTINGS_FILE = "stellics_settings.json";

    private JSONObject settings;

    private StorageService storageService;

    @Override
    public void onApplicationLoad() throws Exception {
        settings = Global.getSettings().loadJSON(SETTINGS_FILE);
        storageService = new StorageService();
    }

    @Override
    public void onNewGameAfterEconomyLoad() {
        boolean seedPrism = getSetting("seedPrism", true);
        int seedFactions = getSetting("seedFactions", 2);
        int seedMinimalSize = getSetting("seedFactionsMinimalSize", 6);
        double seedProbability = getSetting("seedFactionsProbability", 1.0);

        seedPrism(seedPrism);
        seedFactions(seedFactions, seedMinimalSize, seedProbability);
    }

    @Override
    public void onGameLoad(boolean newGame) {
        storageService.registerFeeListener();
    }

    private boolean getSetting(String key, boolean defaultValue) {
        try {
            return settings.getBoolean(key);
        } catch (JSONException exception) {
            return defaultValue;
        }
    }

    private double getSetting(String key, double defaultValue) {
        try {
            return settings.getDouble(key);
        } catch (JSONException exception) {
            return defaultValue;
        }
    }

    private int getSetting(String key, int defaultValue) {
        try {
            return settings.getInt(key);
        } catch (JSONException exception) {
            return defaultValue;
        }
    }

    private void seedFaction(int seedFactions, int seedMinimalSize, double seedProbability, MarketAPI market) {
        int marketSize = market.getSize();

        if (seedMinimalSize > marketSize) {
            return;
        }

        String faction = market.getFactionId();

        if (seedFactions == 1 && faction.equals("independent")) {
            seedMarket(market, seedProbability);
        } else if (seedFactions == 2 && !faction.equals("pirates")) {
            seedMarket(market, seedProbability);
        } else if (seedFactions == 3) {
            seedMarket(market, seedProbability);
        }
    }

    private void seedFactions(int seedFactions, int seedMinimalSize, double seedProbability) {
        // don't add branches and storages to any factions
        if (seedFactions == 0) {
            return;
        }

        // spread branches and storages across the galaxy
        for (MarketAPI marketCopy : Global.getSector().getEconomy().getMarketsCopy()) {
            // we need an actual market instead of copy
            MarketAPI market = Global.getSector().getEconomy().getMarket(marketCopy.getId());
            seedFaction(seedFactions, seedMinimalSize, seedProbability, market);
        }
    }

    private void seedMarket(MarketAPI market, double seedProbability) {
        if (seedProbability > Math.random() && !storageService.has(market)) {
            market.addIndustry(Constants.BRANCH);
            storageService.add(market);
        }
    }

    private void seedPrism(boolean seedPrism) {
        MarketAPI market = Global.getSector().getEconomy().getMarket("nex_prismFreeport");

        if (market == null) {
            return;
        }

        // we have a Nexerelin game with Prism Freeport enabled, and we want to use it
        if (seedPrism && !storageService.has(market)) {
            seedMarket(market, 1);
        }
    }
}
