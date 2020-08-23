package stellics.campaign;

import java.util.List;

import com.fs.starfarer.api.campaign.FleetMemberPickerListener;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

import stellics.filter.FilterManager;
import stellics.helper.EconomyHelper;
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
        plugin.askForMore();
    }

    @Override
    public void pickedFleetMembers(List<FleetMemberAPI> fleet) {
        plugin.addIntel(IntelHelper.getFleetIntel(filterManager, fleet));
    }

    public void handle(StellnetDialogOption option) {
        String size = option.name().substring(5).toLowerCase();
        List<MarketAPI> markets = EconomyHelper.getMarkets(filterManager.getMarketFiltersCopy());
        List<FleetMemberAPI> fleet = MarketHelper.findShips(markets, filterManager.getSubmarketFiltersCopy(), size);

        if (fleet.isEmpty()) {
            plugin.askForMore("No markets selling " + size + " ships found.");
            return;
        }

        interaction.showFleetMemberPickerDialog("Pick a ship to search for", "Query", "Cancel", 8, 12, 64f, true, true,
                fleet, this);
    }
}
