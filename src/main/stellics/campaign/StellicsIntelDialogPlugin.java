package stellics.campaign;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.campaign.OptionPanelAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.campaign.VisualPanelAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.util.Misc;

import org.lwjgl.input.Keyboard;

import stellics.Constants;
import stellics.campaign.intel.StellicsLocationIntel;
import stellics.campaign.intel.StellicsOfficerIntel;
import stellics.helper.MarketHelper;

public class StellicsIntelDialogPlugin implements InteractionDialogPlugin {

    private enum OptionId {
        INIT(),
        BRANCH(),
        WEAPONS(),
        SHIPS(),
        OFFICERS(),
        TIMID(),
        CAUTIOUS(),
        STEADY(),
        AGGRESSIVE(),
        RECKLESS(),
        EXIT()
    }

    private InteractionDialogAPI dialog;
    private TextPanelAPI textPanel;
    private OptionPanelAPI options;
    private VisualPanelAPI visual;

    private MarketHelper marketHelper;

    public StellicsIntelDialogPlugin() {
        marketHelper = new MarketHelper();
    }

    @Override
    public void advance(float arg0) {
    }

    @Override
    public void backFromEngagement(EngagementResultAPI arg0) {
    }

    @Override
    public Object getContext() {
        return null;
    }

    @Override
    public Map<String, MemoryAPI> getMemoryMap() {

        return null;
    }

    @Override
    public void init(InteractionDialogAPI d) {
        dialog = d;
        textPanel = dialog.getTextPanel();
        options = dialog.getOptionPanel();
        visual = dialog.getVisualPanel();

        visual.showImagePortion("illustrations", "stellnet", 640, 400, 0, 0, 480, 300);
        dialog.setOptionOnEscape("Disconnect from StellNET", OptionId.EXIT);

        optionSelected(null, OptionId.INIT);
    }

    @Override
    public void optionMousedOver(String arg0, Object arg1) {
    }

    @Override
    public void optionSelected(String text, Object optionData) {
        if (optionData == null) {
            return;
        }

        OptionId option = (OptionId) optionData;

        switch (option) {
            case INIT:
                initHandler();
                return;

            case WEAPONS:
            case SHIPS:
                break;

            case OFFICERS:
                officersHandler();
                return;

            case TIMID:
            case CAUTIOUS:
            case STEADY:
            case AGGRESSIVE:
            case RECKLESS:
                officersHandler(option.name().toLowerCase());
                initHandler();
                return;

            case BRANCH:
                branchHandler();
                break;

            case EXIT:
                dialog.dismiss();
                return;
        }

        textPanel.addPara("Are there any other services which you would like to use?");
    }

    private void addTitle(String text) {
        Color colorHighlight = Misc.getHighlightColor();
        textPanel.addPara(text, colorHighlight);
    }

    private void initHandler() {
        addTitle("StellNET");
        textPanel.addPara("Welcome to Stellar Network!");
        textPanel.addPara("Which of our services would you like to use?");

        options.clearOptions();
        options.addOption("Locate nearest Stellar Logisticts Branch", OptionId.BRANCH);
        // options.addOption("Query markets for weapons", OptionId.WEAPONS);
        // options.addOption("Query markets for ships", OptionId.SHIPS);
        options.addOption("Find officers for hire", OptionId.OFFICERS);
        options.addOption("Disconnect from StellNET", OptionId.EXIT);
        options.setShortcut(OptionId.EXIT, Keyboard.KEY_ESCAPE, false, false, false, false);
    }

    private void branchHandler() {
        List<MarketAPI> markets = marketHelper.findMarketsWithSubmarket(Constants.STORAGE);
        MarketAPI market = marketHelper.getNearestMarket(markets);
        addTitle("Locate Nearest Branch");

        if (market == null) {
            textPanel.addPara("Could not find any operational Stellar Logistic Branches.");
            return;
        }

        StellicsLocationIntel intel = new StellicsLocationIntel(market);
        Global.getSector().getIntelManager().addIntel(intel);
        textPanel.addPara("Provided intel for the nearest operational Stellar Logistics Branch.");
    }

    private void officersHandler() {
        addTitle("Find Officers");
        textPanel.addPara("What personality should the officer have?");

        options.clearOptions();
        options.addOption("Timid", OptionId.TIMID);
        options.addOption("Cautious", OptionId.CAUTIOUS);
        options.addOption("Steady", OptionId.STEADY);
        options.addOption("Aggressive", OptionId.AGGRESSIVE);
        options.addOption("Reckless", OptionId.RECKLESS);
        options.addOption("Go back", OptionId.INIT);
    }

    private void officersHandler(String personality) {
        List<MarketAPI> markets = marketHelper.findMarketsWithOfficers(personality);
        MarketAPI market = marketHelper.getNearestMarket(markets);
        addTitle("Find Officers");

        if (market == null) {
            textPanel.addPara("Could not find any " + personality + " officers.");
            return;
        }

        StellicsOfficerIntel intel = new StellicsOfficerIntel(market, personality);
        Global.getSector().getIntelManager().addIntel(intel);
        textPanel.addPara("Provided intel for the nearest " + personality + " officer available for hire.");
    }
}
