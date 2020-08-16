package stellics.campaign;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoPickerListener;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.campaign.OptionPanelAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.campaign.VisualPanelAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import org.lwjgl.input.Keyboard;

import stellics.Constants;
import stellics.campaign.econ.MarketFilter;
import stellics.campaign.econ.NonHostileFilter;
import stellics.campaign.intel.*;
import stellics.helper.CargoHelper;
import stellics.helper.IntelHelper;
import stellics.helper.MarketHelper;

public class StellicsIntelDialogPlugin implements InteractionDialogPlugin, CargoPickerListener {

    private enum OptionId {
        // main page
        INIT, BRANCH, OFFICERS, QUERY, EXIT,
        // officers page
        TIMID, CAUTIOUS, STEADY, AGGRESSIVE, RECKLESS,
        // query page
        WEAPON, FIGHTER, MODSPEC, BLUEPRINT
    }

    private InteractionDialogAPI dialog;
    private TextPanelAPI textPanel;
    private OptionPanelAPI options;
    private VisualPanelAPI visual;

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

        addTitle("StellNET");
        textPanel.addPara("Welcome to Stellar Network!");
        textPanel.addPara("Which of our services would you like to use?");
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
                break;

            case BRANCH:
                branchHandler();
                break;

            case OFFICERS:
                officersHandler();
                break;

            case QUERY:
                queryHandler();
                break;

            case EXIT:
                dialog.dismiss();
                break;

            // find officers handling
            case TIMID:
            case CAUTIOUS:
            case STEADY:
            case AGGRESSIVE:
            case RECKLESS:
                officersHandler(option);
                break;

            // query markets handling
            case WEAPON:
            case FIGHTER:
            case MODSPEC:
            case BLUEPRINT:
                queryHandler(option);
                break;
        }
    }

    @Override
    public void cancelledCargoSelection() {
        askForMore();
    }

    @Override
    public void pickedCargo(CargoAPI cargo) {
        addIntel(IntelHelper.getCargoIntel(getFilters(), cargo));
    }

    @Override
    public void recreateTextPanel(TooltipMakerAPI panel, CargoAPI cargo, CargoStackAPI pickedUp,
            boolean pickedUpFromSource, CargoAPI combined) {
    }

    protected void initHandler() {
        options.clearOptions();
        options.addOption("Locate nearest branch", OptionId.BRANCH);
        options.addOption("Find officers", OptionId.OFFICERS);
        options.addOption("Query markets", OptionId.QUERY);
        options.addOption("Disconnect from StellNET", OptionId.EXIT);
        options.setShortcut(OptionId.EXIT, Keyboard.KEY_ESCAPE, false, false, false, false);
    }

    protected void branchHandler() {
        addTitle("Locate Nearest Branch");
        addIntel(IntelHelper.getIndustryIntel(getFilters(), Constants.BRANCH, true));
    }

    protected void officersHandler() {
        addTitle("Find Officers");
        textPanel.addPara("What personality should the officer have?");

        options.clearOptions();
        options.addOption("Timid", OptionId.TIMID);
        options.addOption("Cautious", OptionId.CAUTIOUS);
        options.addOption("Steady", OptionId.STEADY);
        options.addOption("Aggressive", OptionId.AGGRESSIVE);
        options.addOption("Reckless", OptionId.RECKLESS);
        options.addOption("Go back", OptionId.INIT);
        options.setShortcut(OptionId.INIT, Keyboard.KEY_ESCAPE, false, false, false, false);
    }

    protected void officersHandler(OptionId option) {
        String personality = option.name().toLowerCase();
        addTitle("Find Officers");
        addIntel(IntelHelper.getOfficerIntel(getFilters(), personality));
    }

    protected void queryHandler() {
        addTitle("Query Markets");
        textPanel.addPara("What category of items are you interested in?");

        options.clearOptions();
        options.addOption("Weapons", OptionId.WEAPON);
        options.addOption("Fighters", OptionId.FIGHTER);
        options.addOption("Modspecs", OptionId.MODSPEC);
        options.addOption("Blueprints", OptionId.BLUEPRINT);
        options.addOption("Go back", OptionId.INIT);
        options.setShortcut(OptionId.INIT, Keyboard.KEY_ESCAPE, false, false, false, false);
    }

    protected void queryHandler(OptionId option) {
        String category = option.name().toLowerCase();
        List<MarketAPI> markets = MarketHelper.findMarkets(getFilters());
        CargoAPI cargo = CargoHelper.getCargo(MarketHelper.findItems(markets, category));

        if (cargo.isEmpty()) {
            askForMore("No markets selling " + category + "s found.");
            return;
        }

        dialog.showCargoPickerDialog("Pick item to search for", "Query", "Cancel", false, 0f, cargo, this);
    }

    private void addIntel(BaseStellnetIntel intel) {
        intel.trigger();
        intel.updateTextPanel(textPanel);
        askForMore();
    }

    private void askForMore() {
        initHandler();
        addTitle("StellNET");
        textPanel.addPara("Are there any other services you would like to use?");
    }

    private void askForMore(String update) {
        textPanel.addPara(update);
        askForMore();
    }

    private void addTitle(String text) {
        Color colorHighlight = Misc.getHighlightColor();
        textPanel.addPara(text, colorHighlight);
    }

    private List<MarketFilter> getFilters() {
        List<MarketFilter> filters = new ArrayList<MarketFilter>();
        filters.add(new NonHostileFilter());

        return filters;
    }
}
