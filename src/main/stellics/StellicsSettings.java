package stellics;

import org.json.JSONException;
import org.json.JSONObject;

public class StellicsSettings {

    boolean seedPrism;

    int seedFactions;

    int seedMinimalSize;

    double seedProbability;

    int seedMaxSubmarkets;

    public StellicsSettings(JSONObject settings) {
        seedPrism = getSetting(settings, "seedPrism", true);
        seedFactions = getSetting(settings, "seedFactions", 1);
        seedMinimalSize = getSetting(settings, "seedFactionsMinimalSize", 4);
        seedProbability = getSetting(settings, "seedFactionsProbability", 1.0);
        seedMaxSubmarkets = getSetting(settings, "seedFactionsMaxSubmarkets", 4);
    }

    public boolean isSeedPrism() {
        return seedPrism;
    }

    public int getSeedFactions() {
        return seedFactions;
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
