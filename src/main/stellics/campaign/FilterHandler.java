package stellics.campaign;

import stellics.filter.FilterManager;

public class FilterHandler {

    private FilterManager filterManager;
    private StellnetDialogPlugin plugin;

    public FilterHandler(FilterManager f, StellnetDialogPlugin p) {
        filterManager = f;
        plugin = p;
    }

    public void handle(StellnetDialogOption option) {
        if (isFilter(option, "market")) {
            filterManager.setCargoWeaponSize(option);
            filterManager.setCargoWeaponType(option);
            filterManager.setCargoWingType(option);
            showMarketFilters();
            return;
        }

        if (isFilter(option, "ship")) {
            filterManager.setFleetShipDamaged(option);
            filterManager.setFleetShipCarrier(option);
            filterManager.setFleetShipCivilian(option);
            showShipFilters();
            return;
        }

        plugin.askForMore();
    }

    private boolean isFilter(StellnetDialogOption option, String filter) {
        return option.name().startsWith(filter.toUpperCase() + "_");
    }

    private void showMarketFilters() {
        plugin.addOptions(filterManager.getCargoWeaponSize(), filterManager.getCargoWeaponType(),
                filterManager.getCargoWingType(), StellnetDialogOption.MARKET_BACK);
    }

    private void showShipFilters() {
        plugin.addOptions(filterManager.getFleetShipDamaged(), filterManager.getFleetShipCarrier(),
                filterManager.getFleetShipCivilian(), StellnetDialogOption.SHIP_BACK);
    }
}
