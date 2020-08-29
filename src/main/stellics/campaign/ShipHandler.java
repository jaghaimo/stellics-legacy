package stellics.campaign;

import java.util.List;

import com.fs.starfarer.api.campaign.FleetMemberPickerListener;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

import stellics.filter.FilterManager;
import stellics.helper.IntelHelper;
import stellics.helper.MarketHelper;

public class ShipHandler extends BasicHandler implements FleetMemberPickerListener {

    private FilterManager filterManager;

    public ShipHandler(StellnetDialogPlugin p) {
        super(p);
        filterManager = p.getFilterManager();
    }

    @Override
    public void cancelledFleetMemberPicking() {
    }

    @Override
    public void pickedFleetMembers(List<FleetMemberAPI> fleet) {
        plugin.addIntel(IntelHelper.getFleetIntel(filterManager, fleet));
    }

    @Override
    protected StellnetDialogOption run(StellnetDialogOption option) {
        List<FleetMemberAPI> fleet = MarketHelper.findShips(filterManager);

        if (fleet.isEmpty()) {
            String size = filterManager.getFleetShipSize().name().substring(10).toLowerCase();
            plugin.addText("No markets selling desired " + size + " ships found.");

            return option;
        }

        plugin.getDialog().showFleetMemberPickerDialog("Pick a ship to search for...", "Search", "Cancel", 8, 12, 64f,
                true, true, fleet, this);

        return option;
    }
}
