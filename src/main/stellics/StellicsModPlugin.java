package stellics;

import java.util.HashSet;
import java.util.Set;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import stellics.helper.StorageHelper;
import stellics.settings.Settings;

public class StellicsModPlugin extends BaseModPlugin {

    private final String FACTION_BLACKLIST = "data/config/stellics/faction_blacklist.csv";
    private final String FACTION_WHITELIST = "data/config/stellics/faction_whitelist.csv";
    private final String SETTINGS_FILE = "settings.json";

    private Settings settings;

    @Override
    public void onApplicationLoad() throws Exception {
        JSONObject rawSettings;

        try {
            rawSettings = Global.getSettings().loadJSON(SETTINGS_FILE);
        } catch (Exception exception) {
            // use default settings
            rawSettings = new JSONObject();
        }

        settings = new Settings(rawSettings, loadFactionList(FACTION_BLACKLIST), loadFactionList(FACTION_WHITELIST));
    }

    @Override
    public void onNewGameAfterEconomyLoad() {
        seedPrism();
        seedFactions();
    }

    @Override
    public void onGameLoad(boolean newGame) {
        // add storage fee listener (monthly expense)
        StorageHelper.registerFeeListener();

        // add stellnet skill
        Global.getSector().getCharacterData().addAbility(Constants.ABILITY_STELLNET);
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

        if (!settings.canSeed(faction)) {
            return false;
        }

        if (settings.getFactionMinSize() > marketSize) {
            return false;
        }

        if (settings.getFactionMaxSubmarkets() < submarketCount) {
            return false;
        }

        return true;
    }

    private void seedFactions() {
        // spread branches and storages across the galaxy
        for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()) {
            if (canSeed(market)) {
                double seedProbability = settings.getFactionProbability();
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
        if (settings.hasPrism() && !StorageHelper.has(market)) {
            seedMarket(market, 1);
        }
    }
}
