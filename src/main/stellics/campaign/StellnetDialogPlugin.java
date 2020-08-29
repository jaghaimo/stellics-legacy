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

import stellics.campaign.intel.BaseStellnetIntel;
import stellics.filter.FilterManager;

public class StellnetDialogPlugin implements InteractionDialogPlugin {

    private FilterManager filterManager;
    private StellnetDialogOption lastOption;

    private CargoHandler cargoHandler;
    private FilterHandler filterHandler;
    private IndustryHandler industryHandler;
    private ShipHandler shipHandler;
    private StaffHandler staffHandler;

    private InteractionDialogAPI dialog;
    private OptionPanelAPI options;
    private TextPanelAPI textPanel;
    private VisualPanelAPI visual;

    public StellnetDialogPlugin() {
        filterManager = new FilterManager();
        cargoHandler = new CargoHandler(this);
        filterHandler = new FilterHandler(this);
        industryHandler = new IndustryHandler(this);
        shipHandler = new ShipHandler(this);
        staffHandler = new StaffHandler(this);
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
        options = dialog.getOptionPanel();
        textPanel = dialog.getTextPanel();
        visual = dialog.getVisualPanel();

        visual.showImagePortion("illustrations", "stellnet", 640, 400, 0, 0, 480, 300);

        Color colorHighlight = Misc.getHighlightColor();
        textPanel.addPara("StellNET", colorHighlight);
        textPanel.addPara("Welcome to Stellar Network!");
        textPanel.addPara("Which of our services would you like to use?");
        showMenu();
    }

    @Override
    public void optionMousedOver(String text, Object optionData) {
    }

    @Override
    public void optionSelected(String text, Object optionData) {
        if (optionData == null) {
            return;
        }

        StellnetDialogOption option = (StellnetDialogOption) optionData;

        switch (option) {
            case INIT:
                askForMore();
                break;

            case BRANCH:
                lastOption = industryHandler.handle(option);
                break;

            case STAFF:
                lastOption = staffHandler.handle(option);
                break;

            case CARGO:
                lastOption = cargoHandler.handle(option);
                break;

            case SHIP:
                lastOption = shipHandler.handle(option);
                break;

            case EXIT:
                dialog.dismiss();
                break;

            default:
                lastOption = filterHandler.handle(option);
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
    }

    public void addText(String update) {
        textPanel.addPara(update);
    }

    public void askForMore() {
        showMenu();
        textPanel.addPara("Are there any other services you would like to use?");
    }

    public InteractionDialogAPI getDialog() {
        return dialog;
    }

    public FilterHandler getFilterHandler() {
        return filterHandler;
    }

    public FilterManager getFilterManager() {
        return filterManager;
    }

    public StellnetDialogOption getLastOption() {
        return lastOption;
    }

    public void setEscShortcut(StellnetDialogOption option) {
        options.setShortcut(option, Keyboard.KEY_ESCAPE, false, false, false, false);
    }

    private void showMenu() {
        lastOption = StellnetDialogOption.INIT;
        addOptions(StellnetDialogOption.BRANCH, StellnetDialogOption.STAFF, StellnetDialogOption.CARGO,
                StellnetDialogOption.SHIP, StellnetDialogOption.EXIT);
        setEscShortcut(StellnetDialogOption.EXIT);
    }
}
