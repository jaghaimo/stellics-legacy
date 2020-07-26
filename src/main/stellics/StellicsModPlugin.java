package stellics;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;

import org.apache.log4j.Level;
import org.json.JSONObject;

public class StellicsModPlugin extends BaseModPlugin {
    private static final String SETTINGS_FILE = "stellics_settings.json";

    @Override
    public void onApplicationLoad() throws Exception {
        JSONObject settings = Global.getSettings().loadJSON(SETTINGS_FILE);
        setLogLevel(Level.toLevel(settings.optString("logLevel", "ERROR"), Level.ERROR));
    }

    @Override
    public void onNewGame() {
        initStellics();
    }

    private static void initStellics() {
    }

    private static void setLogLevel(Level level) {
    }
}
