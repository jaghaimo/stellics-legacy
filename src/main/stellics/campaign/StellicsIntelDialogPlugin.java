package stellics.campaign;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoPickerListener;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.FleetMemberPickerListener;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.campaign.OptionPanelAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.campaign.VisualPanelAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import org.lwjgl.input.Keyboard;

import stellics.Constants;
import stellics.campaign.intel.BaseStellnetIntel;
import stellics.filter.FilterManager;
import stellics.filter.MarketNonHostile;
import stellics.filter.SubmarketNonCustom;
import stellics.helper.CargoHelper;
import stellics.helper.EconomyHelper;
import stellics.helper.IntelHelper;
import stellics.helper.MarketHelper;

public class StellicsIntelDialogPlugin
        implements InteractionDialogPlugin, CargoPickerListener, FleetMemberPickerListener {

    private FilterManager filterManager;

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
        filterManager = new FilterManager();
        filterManager.add(new MarketNonHostile());
        filterManager.add(new SubmarketNonCustom());

        dialog = d;
        textPanel = dialog.getTextPanel();
        options = dialog.getOptionPanel();
        visual = dialog.getVisualPanel();

        visual.showImagePortion("illustrations", "stellnet", 640, 400, 0, 0, 480, 300);
        dialog.setOptionOnEscape("Disconnect from StellNET", IntelOption.EXIT);

        addTitle("StellNET");
        textPanel.addPara("Welcome to Stellar Network!");
        textPanel.addPara("Which of our services would you like to use?");
        optionSelected(null, IntelOption.INIT);
    }

    @Override
    public void optionMousedOver(String arg0, Object arg1) {
    }

    @Override
    public void optionSelected(String text, Object optionData) {
        if (optionData == null) {
            return;
        }

        IntelOption option = (IntelOption) optionData;

        switch (option) {
            case INIT:
                initHandler();
                break;

            case BRANCH:
                branchHandler();
                break;

            case OFFICER:
                officersHandler();
                break;

            case QUERY:
                queryHandler();
                break;

            case SHIP:
                shipHandler();
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

            // find ships handling
            case FRIGATE:
            case DESTROYER:
            case CRUISER:
            case CAPITAL:
                shipHandler(option);
                break;
        }
    }

    @Override
    public void cancelledCargoSelection() {
        askForMore();
    }

    @Override
    public void pickedCargo(CargoAPI cargo) {
        addIntel(IntelHelper.getCargoIntel(filterManager, cargo));
    }

    @Override
    public void cancelledFleetMemberPicking() {
        askForMore();
    }

    @Override
    public void pickedFleetMembers(List<FleetMemberAPI> fleet) {
        addIntel(IntelHelper.getFleetIntel(filterManager, fleet));
    }

    @Override
    public void recreateTextPanel(TooltipMakerAPI panel, CargoAPI cargo, CargoStackAPI pickedUp,
            boolean pickedUpFromSource, CargoAPI combined) {
    }

    protected void initHandler() {
        addOptions(IntelOption.BRANCH, IntelOption.OFFICER, IntelOption.QUERY, IntelOption.SHIP, IntelOption.EXIT);
        options.setShortcut(IntelOption.EXIT, Keyboard.KEY_ESCAPE, false, false, false, false);
    }

    protected void branchHandler() {
        addTitle("Locate Nearest Branch");
        addIntel(IntelHelper.getIndustryIntel(filterManager.getMarketFiltersCopy(), Constants.BRANCH, true));
    }

    protected void officersHandler() {
        addTitle("Find Officers");
        textPanel.addPara("What personality should the officer have?");

        addOptions(IntelOption.TIMID, IntelOption.CAUTIOUS, IntelOption.STEADY, IntelOption.AGGRESSIVE,
                IntelOption.RECKLESS, IntelOption.INIT);
        options.setShortcut(IntelOption.INIT, Keyboard.KEY_ESCAPE, false, false, false, false);
    }

    protected void officersHandler(IntelOption option) {
        String personality = option.name().toLowerCase();
        addTitle("Find Officers");
        addIntel(IntelHelper.getOfficerIntel(filterManager.getMarketFiltersCopy(), personality));
    }

    protected void queryHandler() {
        addTitle("Query Markets");
        textPanel.addPara("What category of items are you interested in?");

        addOptions(IntelOption.WEAPON, IntelOption.FIGHTER, IntelOption.MODSPEC, IntelOption.BLUEPRINT,
                IntelOption.INIT);
        options.setShortcut(IntelOption.INIT, Keyboard.KEY_ESCAPE, false, false, false, false);
    }

    protected void queryHandler(IntelOption option) {
        String category = option.name().toLowerCase();
        List<MarketAPI> markets = EconomyHelper.getMarkets(filterManager.getMarketFiltersCopy());
        CargoAPI cargo = CargoHelper
                .getCargo(MarketHelper.findItems(markets, filterManager.getSubmarketFiltersCopy(), category));

        if (cargo.isEmpty()) {
            askForMore("No markets selling " + category + "s found.");
            return;
        }

        dialog.showCargoPickerDialog("Pick an item to search for", "Query", "Cancel", false, 0f, cargo, this);
    }

    protected void shipHandler() {
        addTitle("Ship Finder");
        textPanel.addPara("What type of ships are you interested in?");

        addOptions(IntelOption.FRIGATE, IntelOption.DESTROYER, IntelOption.CRUISER, IntelOption.CAPITAL,
                IntelOption.INIT);
        options.setShortcut(IntelOption.INIT, Keyboard.KEY_ESCAPE, false, false, false, false);
    }

    protected void shipHandler(IntelOption option) {
        String size = option.name().toLowerCase();
        List<MarketAPI> markets = EconomyHelper.getMarkets(filterManager.getMarketFiltersCopy());
        List<FleetMemberAPI> fleet = MarketHelper.findShips(markets, filterManager.getSubmarketFiltersCopy(), size);

        if (fleet.isEmpty()) {
            askForMore("No markets selling " + size + " ships found.");
            return;
        }

        dialog.showFleetMemberPickerDialog("Pick a ship to search for", "Query", "Cancel", 8, 12, 64f, true, true,
                fleet, this);
    }

    private void addOptions(IntelOption... intelOptions) {
        options.clearOptions();

        for (IntelOption option : intelOptions) {
            options.addOption(option.getName(), option);
        }
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
}
