package stellics;

import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class StellicsSettings {

    private boolean seedPrism;
    private double seedProbability;
    private int seedMinimalSize;
    private int seedMaxSubmarkets;
    private boolean seedUseBlacklist;
    private boolean seedUseWhitelist;
    private Set<String> seedFactionBlacklist;
    private Set<String> seedFactionWhitelist;

    public StellicsSettings(JSONObject settings, Set<String> blacklist, Set<String> whitelist) {
        seedPrism = getSetting(settings, "seedPrism", true);
        seedMinimalSize = getSetting(settings, "seedFactionsMinimalSize", 4);
        seedProbability = getSetting(settings, "seedFactionsProbability", 1.0);
        seedMaxSubmarkets = getSetting(settings, "seedFactionsMaxSubmarkets", 4);
        seedUseBlacklist = getSetting(settings, "seedFactionsUseBlacklist", true);
        seedUseWhitelist = getSetting(settings, "seedFactionsUseWhitelist", true);
        seedFactionBlacklist = blacklist;
        seedFactionWhitelist = whitelist;
    }

    public boolean canSeed(String faction) {
        if (!seedFactionWhitelist.contains(faction) && seedUseWhitelist) {
            return false;
        }

        if (seedFactionBlacklist.contains(faction) && seedUseBlacklist) {
            return false;
        }

        return true;
    }

    public boolean isSeedPrism() {
        return seedPrism;
    }

    public int getSeedMinimalSize() {
        return seedMinimalSize;
    }

    public double getSeedProbability() {
        return seedProbability;
    }

    public int getSeedMaxSubmarkets() {
        return seedMaxSubmarkets;
    }

    private boolean getSetting(JSONObject settings, String key, boolean defaultValue) {
        try {
            return settings.getBoolean(key);
        } catch (JSONException exception) {
            return defaultValue;
        }
    }

    private double getSetting(JSONObject settings, String key, double defaultValue) {
        try {
            return settings.getDouble(key);
        } catch (JSONException exception) {
            return defaultValue;
        }
    }

    private int getSetting(JSONObject settings, String key, int defaultValue) {
        try {
            return settings.getInt(key);
        } catch (JSONException exception) {
            return defaultValue;
        }
    }
}
