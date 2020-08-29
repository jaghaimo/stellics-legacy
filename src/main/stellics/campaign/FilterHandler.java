package stellics.campaign;

import stellics.filter.FilterManager;

public class FilterHandler extends BasicHandler {

    FilterManager filterManager;

    public FilterHandler(StellnetDialogPlugin p) {
        super(p);
        filterManager = p.getFilterManager();
    }

    @Override
    public StellnetDialogOption handle(StellnetDialogOption o) {
        StellnetDialogOption option = plugin.getLastOption();

        if (option.equals(StellnetDialogOption.INIT)) {
            option = o;
        }

        switch (option) {
            case BRANCH:
                return branchHandler(o);

            case STAFF:
                return staffHandler(o);

            case CARGO:
                return cargoHandler(o);

            case SHIP:
                return shipHandler(o);

            default:
                plugin.askForMore();
        }

        return option;
    }

    protected StellnetDialogOption branchHandler(StellnetDialogOption option) {
        filterManager.setMarketFaction(option);

        plugin.addOptions(StellnetDialogOption.BRANCH, filterManager.getMarketFaction(), StellnetDialogOption.INIT);

        return StellnetDialogOption.BRANCH;
    }

    protected StellnetDialogOption staffHandler(StellnetDialogOption option) {
        filterManager.setMarketFaction(option);
        filterManager.setStaffType(option);
        filterManager.setStaffOfficer(option);

        StellnetDialogOption o = filterManager.getStaffType();

        if (o.equals(StellnetDialogOption.STAFF_ADMIN)) {
            plugin.addOptions(StellnetDialogOption.STAFF, filterManager.getMarketFaction(),
                    filterManager.getStaffType(), StellnetDialogOption.INIT);
        } else if (o.equals(StellnetDialogOption.STAFF_OFFICER)) {
            plugin.addOptions(StellnetDialogOption.STAFF, filterManager.getMarketFaction(),
                    filterManager.getStaffType(), filterManager.getStaffOfficer(), StellnetDialogOption.INIT);
        }

        plugin.setEscShortcut(StellnetDialogOption.INIT);

        return StellnetDialogOption.STAFF;
    }

    protected StellnetDialogOption cargoHandler(StellnetDialogOption option) {
        filterManager.setMarketFaction(option);
        filterManager.setCargoType(option);
        filterManager.setCargoWeaponSize(option);
        filterManager.setCargoWeaponType(option);
        filterManager.setCargoWingType(option);

        StellnetDialogOption o = filterManager.getCargoType();

        if (o.equals(StellnetDialogOption.CARGO_TYPE_WEAPON)) {
            plugin.addOptions(StellnetDialogOption.CARGO, filterManager.getMarketFaction(),
                    filterManager.getCargoType(), filterManager.getCargoWeaponSize(),
                    filterManager.getCargoWeaponType(), StellnetDialogOption.INIT);
        } else if (o.equals(StellnetDialogOption.CARGO_TYPE_FIGHTER)) {
            plugin.addOptions(StellnetDialogOption.CARGO, filterManager.getMarketFaction(),
                    filterManager.getCargoType(), filterManager.getCargoWingType(), StellnetDialogOption.INIT);
        } else if (o.equals(StellnetDialogOption.CARGO_TYPE_MODSPEC)) {
            plugin.addOptions(StellnetDialogOption.CARGO, filterManager.getMarketFaction(),
                    filterManager.getCargoType(), StellnetDialogOption.INIT);
        }

        plugin.setEscShortcut(StellnetDialogOption.INIT);

        return StellnetDialogOption.CARGO;
    }

    protected StellnetDialogOption shipHandler(StellnetDialogOption option) {
        filterManager.setMarketFaction(option);
        filterManager.setFleetShipSize(option);
        filterManager.setFleetShipDamaged(option);
        filterManager.setFleetShipCarrier(option);
        filterManager.setFleetShipCivilian(option);

        plugin.addOptions(StellnetDialogOption.SHIP, filterManager.getMarketFaction(), filterManager.getFleetShipSize(),
                filterManager.getFleetShipDamaged(), filterManager.getFleetShipCarrier(),
                filterManager.getFleetShipCivilian(), StellnetDialogOption.INIT);
        plugin.setEscShortcut(StellnetDialogOption.INIT);

        return StellnetDialogOption.SHIP;
    }
}
