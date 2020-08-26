package stellics;

import java.util.HashSet;
import java.util.Set;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import stellics.helper.StorageHelper;

public class StellicsModPlugin extends BaseModPlugin {

    private final String SETTINGS_FILE = "stellics_settings.json";

    private JSONObject settings;
    private StellicsSettings stellicsSettings;

    @Override
    public void onApplicationLoad() throws Exception {
        settings = Global.getSettings().loadJSON(SETTINGS_FILE);
    }

    @Override
    public void onNewGameAfterEconomyLoad() {
        stellicsSettings = new StellicsSettings(settings, loadFactionList("data/config/stellics/faction_blacklist.csv"),
                loadFactionList("data/config/stellics/faction_whitelist.csv"));

        seedPrism();
        seedFactions();
    }

    @Override
    public void onGameLoad(boolean newGame) {
        StorageHelper.registerFeeListener();
    }

    private Set<String> loadFactionList(String path) {
        Set<String> factionList = new HashSet<>();

        try {
            JSONArray config = Global.getSettings().getMergedSpreadsheetDataForMod("id", path, "stellics");
            for (int i = 0; i < config.length(); i++) {
                JSONObject row = config.getJSONObject(i);
                String factionId = row.getString("id");
                factionList.add(factionId);
            }
        } catch (Exception exception) {
        }

        return factionList;
    }

    private boolean canSeed(MarketAPI market) {
        String faction = market.getFactionId();
        int marketSize = market.getSize();
        int submarketCount = market.getSubmarketsCopy().size();

        if (!stellicsSettings.canSeed(faction)) {
            return false;
        }

        if (stellicsSettings.getSeedMinimalSize() > marketSize) {
            return false;
        }

        if (stellicsSettings.getSeedMaxSubmarkets() < submarketCount) {
            return false;
        }

        return true;
    }

    private void seedFactions() {
        // spread branches and storages across the galaxy
        for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()) {
            if (canSeed(market)) {
                double seedProbability = stellicsSettings.getSeedProbability();
                seedMarket(market, seedProbability);
            }
        }
    }

    private void seedMarket(MarketAPI market, double seedProbability) {
        if (seedProbability > Math.random() && !StorageHelper.has(market)) {
            market.addIndustry(Constants.BRANCH);
            StorageHelper.add(market);
        }
    }

    private void seedPrism() {
        MarketAPI market = Global.getSector().getEconomy().getMarket("nex_prismFreeport");

        if (market == null) {
            return;
        }

        // we have a Nexerelin game with Prism Freeport enabled, and we want to use it
        if (stellicsSettings.isSeedPrism() && !StorageHelper.has(market)) {
            seedMarket(market, 1);
        }
    }
}
