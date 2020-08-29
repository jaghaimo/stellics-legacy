package stellics.campaign;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoPickerListener;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import stellics.filter.FilterManager;
import stellics.helper.CargoHelper;
import stellics.helper.IntelHelper;
import stellics.helper.MarketHelper;

public class CargoHandler extends BasicHandler implements CargoPickerListener {

    private FilterManager filterManager;

    public CargoHandler(StellnetDialogPlugin p) {
        super(p);
        filterManager = p.getFilterManager();
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

    @Override
    protected StellnetDialogOption run(StellnetDialogOption option) {
        CargoAPI cargo = CargoHelper.getCargo(MarketHelper.findItems(filterManager));

        if (cargo.isEmpty()) {
            String category = filterManager.getCargoType().getName().substring(7).toLowerCase();
            plugin.addText("No markets selling desired " + category + "s found.");

            return option;
        }

        plugin.getDialog().showCargoPickerDialog("Pick an item to search for...", "Search", "Cancel", false, 0f, cargo,
                this);

        return option;
    }
}
