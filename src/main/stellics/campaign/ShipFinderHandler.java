package stellics.campaign;

import java.util.List;

import com.fs.starfarer.api.campaign.FleetMemberPickerListener;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

import stellics.filter.FilterManager;
import stellics.helper.IntelHelper;
import stellics.helper.MarketHelper;

public class ShipFinderHandler implements FleetMemberPickerListener {

    private FilterManager filterManager;
    private InteractionDialogAPI interaction;
    private StellnetDialogPlugin plugin;

    public ShipFinderHandler(FilterManager f, InteractionDialogAPI i, StellnetDialogPlugin p) {
        filterManager = f;
        interaction = i;
        plugin = p;
    }

    @Override
    public void cancelledFleetMemberPicking() {
    }

    @Override
    public void pickedFleetMembers(List<FleetMemberAPI> fleet) {
        plugin.addIntel(IntelHelper.getFleetIntel(filterManager, fleet));
    }

    public void handle(StellnetDialogOption option) {
        String size = option.name().substring(5).toLowerCase();
        List<FleetMemberAPI> fleet = MarketHelper.findShips(filterManager, option);

        if (fleet.isEmpty()) {
            plugin.addText("No markets selling desired " + size + "s found.");
            return;
        }

        interaction.showFleetMemberPickerDialog("Pick a ship to search for", "Query", "Cancel", 8, 12, 64f, true, true,
                fleet, this);
    }
}
