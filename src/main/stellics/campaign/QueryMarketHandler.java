package stellics.campaign;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoPickerListener;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import stellics.filter.FilterManager;
import stellics.helper.CargoHelper;
import stellics.helper.IntelHelper;
import stellics.helper.MarketHelper;

public class QueryMarketHandler implements CargoPickerListener {

    private FilterManager filterManager;
    private InteractionDialogAPI interaction;
    private StellnetDialogPlugin plugin;

    public QueryMarketHandler(FilterManager f, InteractionDialogAPI i, StellnetDialogPlugin p) {
        filterManager = f;
        interaction = i;
        plugin = p;
    }

    @Override
    public void cancelledCargoSelection() {
    }

    @Override
    public void pickedCargo(CargoAPI cargo) {
        plugin.addIntel(IntelHelper.getCargoIntel(filterManager, cargo));
    }

    @Override
    public void recreateTextPanel(TooltipMakerAPI panel, CargoAPI cargo, CargoStackAPI pickedUp,
            boolean pickedUpFromSource, CargoAPI combined) {
    }

    public void handle(StellnetDialogOption option) {
        String category = option.name().substring(7).toLowerCase();
        CargoAPI cargo = CargoHelper.getCargo(MarketHelper.findItems(filterManager, option));

        if (cargo.isEmpty()) {
            plugin.addText("No markets selling " + category + "s found.");
            return;
        }

        interaction.showCargoPickerDialog("Pick an item to search for", "Query", "Cancel", false, 0f, cargo, this);
    }
}
