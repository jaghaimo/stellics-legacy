package stellics.campaign;

import java.awt.Color;
import java.util.Map;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.campaign.OptionPanelAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.campaign.VisualPanelAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.util.Misc;

import org.lwjgl.input.Keyboard;

import stellics.Constants;
import stellics.campaign.intel.BaseStellnetIntel;
import stellics.filter.FilterManager;
import stellics.filter.MarketNonHostile;
import stellics.filter.SubmarketNonCustom;
import stellics.helper.IntelHelper;

public class StellnetDialogPlugin implements InteractionDialogPlugin {

    private FilterManager filterManager;
    private QueryMarketHandler queryMarketHandler;
    private ShipFinderHandler shipFinderHandler;

    private InteractionDialogAPI dialog;
    private TextPanelAPI textPanel;
    private OptionPanelAPI options;
    private VisualPanelAPI visual;

    public StellnetDialogPlugin() {
        filterManager = new FilterManager();
        filterManager.add(new MarketNonHostile());
        filterManager.add(new SubmarketNonCustom());
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

        queryMarketHandler = new QueryMarketHandler(filterManager, d, this);
        shipFinderHandler = new ShipFinderHandler(filterManager, d, this);

        visual.showImagePortion("illustrations", "stellnet", 640, 400, 0, 0, 480, 300);
        dialog.setOptionOnEscape("Disconnect from StellNET", StellnetDialogOption.EXIT);

        addTitle("StellNET");
        textPanel.addPara("Welcome to Stellar Network!");
        textPanel.addPara("Which of our services would you like to use?");
        optionSelected(null, StellnetDialogOption.INIT);
    }

    @Override
    public void optionMousedOver(String arg0, Object arg1) {
    }

    @Override
    public void optionSelected(String text, Object optionData) {
        if (optionData == null) {
            return;
        }

        StellnetDialogOption option = (StellnetDialogOption) optionData;

        switch (option) {
            case INIT:
                showMenu();
                break;

            case BRANCH:
                locateBranch();
                break;

            case OFFICER:
                hireOfficers();
                break;

            case MARKET:
                queryMarkets();
                break;

            case SHIP:
                shipFinder();
                break;

            case EXIT:
                dialog.dismiss();
                break;

            case OFFICER_TIMID:
            case OFFICER_CAUTIOUS:
            case OFFICER_STEADY:
            case OFFICER_AGGRESSIVE:
            case OFFICER_RECKLESS:
                hireOfficers(option);
                break;

            case MARKET_WEAPON:
            case MARKET_FIGHTER:
            case MARKET_MODSPEC:
            case MARKET_BLUEPRINT:
                queryMarketHandler.handle(option);
                break;

            case SHIP_FRIGATE:
            case SHIP_DESTROYER:
            case SHIP_CRUISER:
            case SHIP_CAPITAL:
                shipFinderHandler.handle(option);
                break;

            // else it is a filter
            default:
                break;
        }
    }

    public void addOptions(StellnetDialogOption... intelOptions) {
        options.clearOptions();

        for (StellnetDialogOption option : intelOptions) {
            options.addOption(option.getName(), option);
        }
    }

    public void addIntel(BaseStellnetIntel intel) {
        intel.trigger();
        intel.updateTextPanel(textPanel);
        askForMore();
    }

    public void askForMore() {
        showMenu();
        addTitle("StellNET");
        textPanel.addPara("Are there any other services you would like to use?");
    }

    public void askForMore(String update) {
        textPanel.addPara(update);
        askForMore();
    }

    public void addTitle(String text) {
        Color colorHighlight = Misc.getHighlightColor();
        textPanel.addPara(text, colorHighlight);
    }

    private void showMenu() {
        addOptions(StellnetDialogOption.BRANCH, StellnetDialogOption.OFFICER, StellnetDialogOption.MARKET,
                StellnetDialogOption.SHIP, StellnetDialogOption.EXIT);
        options.setShortcut(StellnetDialogOption.EXIT, Keyboard.KEY_ESCAPE, false, false, false, false);
    }

    private void locateBranch() {
        addTitle("Locate Nearest Branch");
        addIntel(IntelHelper.getIndustryIntel(filterManager.getMarketFiltersCopy(), Constants.BRANCH, true));
    }

    private void hireOfficers() {
        addTitle("Hire Officers");
        textPanel.addPara("What personality should the officer have?");

        addOptions(StellnetDialogOption.OFFICER_TIMID, StellnetDialogOption.OFFICER_CAUTIOUS,
                StellnetDialogOption.OFFICER_STEADY, StellnetDialogOption.OFFICER_AGGRESSIVE,
                StellnetDialogOption.OFFICER_RECKLESS, StellnetDialogOption.INIT);
        options.setShortcut(StellnetDialogOption.INIT, Keyboard.KEY_ESCAPE, false, false, false, false);
    }

    private void hireOfficers(StellnetDialogOption option) {
        String personality = option.name().substring(8).toLowerCase();
        addIntel(IntelHelper.getOfficerIntel(filterManager.getMarketFiltersCopy(), personality));
    }

    private void queryMarkets() {
        addTitle("Query Markets");
        textPanel.addPara("What category of items are you interested in?");

        addOptions(StellnetDialogOption.MARKET_WEAPON, StellnetDialogOption.MARKET_FIGHTER,
                StellnetDialogOption.MARKET_MODSPEC, StellnetDialogOption.MARKET_BLUEPRINT, StellnetDialogOption.INIT);
        options.setShortcut(StellnetDialogOption.INIT, Keyboard.KEY_ESCAPE, false, false, false, false);
    }

    private void shipFinder() {
        addTitle("Search For Ships");
        textPanel.addPara("What type of ships are you interested in?");

        addOptions(StellnetDialogOption.SHIP_FRIGATE, StellnetDialogOption.SHIP_DESTROYER,
                StellnetDialogOption.SHIP_CRUISER, StellnetDialogOption.SHIP_CAPITAL, StellnetDialogOption.INIT);
        options.setShortcut(StellnetDialogOption.INIT, Keyboard.KEY_ESCAPE, false, false, false, false);
    }
}
