package stellics.settings;

import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class Settings {

    public final static boolean SEED_PRISM = true;
    public final static int SEED_FACTIONS_MINIMAL_SIZE = 4;
    public final static float SEED_FACTIONS_PROBABILITY = 1.0f;
    public final static int SEED_FACTIONS_MAX_SUBMARKETS = 4;
    public final static boolean SEED_FACTIONS_USE_BLACKLIST = true;
    public final static boolean SEED_FACTIONS_USE_WHITELIST = true;

    private JSONObject rawSettings;

    private boolean hasPrism;
    private double factionProbability;
    private int factionMinSize;
    private int factionMaxSubmarkets;
    private boolean factionUseBlacklist;
    private boolean factionUseWhitelist;
    private Set<String> factionBlacklist;
    private Set<String> factionWhitelist;

    public Settings(JSONObject settings, Set<String> blacklist, Set<String> whitelist) {
        rawSettings = settings;
        hasPrism = get("seedPrism", SEED_PRISM);
        factionMinSize = get("seedFactionsMinimalSize", SEED_FACTIONS_MINIMAL_SIZE);
        factionProbability = get("seedFactionsProbability", SEED_FACTIONS_PROBABILITY);
        factionMaxSubmarkets = get("seedFactionsMaxSubmarkets", SEED_FACTIONS_MAX_SUBMARKETS);
        factionUseBlacklist = get("seedFactionsUseBlacklist", SEED_FACTIONS_USE_BLACKLIST);
        factionUseWhitelist = get("seedFactionsUseWhitelist", SEED_FACTIONS_USE_WHITELIST);
        factionBlacklist = blacklist;
        factionWhitelist = whitelist;
    }

    public boolean canSeed(String faction) {
        if (!factionWhitelist.contains(faction) && factionUseWhitelist) {
            return false;
        }

        if (factionBlacklist.contains(faction) && factionUseBlacklist) {
            return false;
        }

        return true;
    }

    public boolean hasPrism() {
        return hasPrism;
    }

    public int getFactionMinSize() {
        return factionMinSize;
    }

    public double getFactionProbability() {
        return factionProbability;
    }

    public int getFactionMaxSubmarkets() {
        return factionMaxSubmarkets;
    }

    public Settings(JSONObject s) {
        rawSettings = s;
    }

    private boolean get(String key, boolean defaultValue) {
        try {
            return rawSettings.getBoolean(key);
        } catch (JSONException exception) {
            return defaultValue;
        }
    }

    private double get(String key, double defaultValue) {
        try {
            return rawSettings.getDouble(key);
        } catch (JSONException exception) {
            return defaultValue;
        }
    }

    private int get(String key, int defaultValue) {
        try {
            return rawSettings.getInt(key);
        } catch (JSONException exception) {
            return defaultValue;
        }
    }
}
