package stellics.filter;

import java.util.ArrayList;
import java.util.List;

import stellics.campaign.StellnetDialogOption;

public class FilterManager {

    private StellnetDialogOption cargoWeaponSize;
    private StellnetDialogOption cargoWeaponType;
    private StellnetDialogOption cargoWingType;

    private StellnetDialogOption fleetShipDamaged;
    private StellnetDialogOption fleetShipCarrier;
    private StellnetDialogOption fleetShipCivilian;

    public FilterManager() {
        // default cargo filters
        cargoWeaponSize = StellnetDialogOption.MARKET_WEAPON_SIZE_ANY;
        cargoWeaponType = StellnetDialogOption.MARKET_WEAPON_TYPE_ANY;
        cargoWingType = StellnetDialogOption.MARKET_WING_TYPE_ANY;
        // default ship filters
        fleetShipDamaged = StellnetDialogOption.SHIP_DAMAGED_YES;
        fleetShipCarrier = StellnetDialogOption.SHIP_CARRIER_YES;
        fleetShipCivilian = StellnetDialogOption.SHIP_CIVILIAN_NO;
    }

    public List<CargoStackFilter> listCargoFilters(StellnetDialogOption option) {
        List<CargoStackFilter> filters = new ArrayList<CargoStackFilter>();
        filters.add(new CargoStackType(option));

        if (option.equals(StellnetDialogOption.MARKET_WEAPON)) {
            filters.add(new CargoStackWeaponSize(cargoWeaponSize));
            filters.add(new CargoStackWeaponType(cargoWeaponType));
        }

        if (option.equals(StellnetDialogOption.MARKET_FIGHTER)) {
            filters.add(new CargoStackWingType(cargoWingType));
        }

        return filters;
    }

    public List<FleetMemberFilter> listFleetFilters(StellnetDialogOption option) {
        List<FleetMemberFilter> filters = new ArrayList<FleetMemberFilter>();
        filters.add(new FleetMemberSize(option));
        filters.add(new FleetMemberDamaged(fleetShipDamaged));
        filters.add(new FleetMemberCarrier(fleetShipCarrier));
        filters.add(new FleetMemberCivilian(fleetShipCivilian));

        return filters;
    }

    public List<MarketFilter> listMarketFilters() {
        List<MarketFilter> filters = new ArrayList<MarketFilter>();
        filters.add(new MarketNonHostile());

        return filters;
    }

    public List<SubmarketFilter> listSubmarketFilters() {
        List<SubmarketFilter> filters = new ArrayList<SubmarketFilter>();
        filters.add(new SubmarketNonCustom());

        return filters;
    }

    public StellnetDialogOption getCargoWeaponSize() {
        return cargoWeaponSize;
    }

    public void setCargoWeaponSize(StellnetDialogOption option) {
        if (option == StellnetDialogOption.MARKET_WEAPON_SIZE_ANY) {
            cargoWeaponSize = StellnetDialogOption.MARKET_WEAPON_SIZE_SMALL;
        } else if (option == StellnetDialogOption.MARKET_WEAPON_SIZE_SMALL) {
            cargoWeaponSize = StellnetDialogOption.MARKET_WEAPON_SIZE_MEDIUM;
        } else if (option == StellnetDialogOption.MARKET_WEAPON_SIZE_MEDIUM) {
            cargoWeaponSize = StellnetDialogOption.MARKET_WEAPON_SIZE_LARGE;
        } else if (option == StellnetDialogOption.MARKET_WEAPON_SIZE_LARGE) {
            cargoWeaponSize = StellnetDialogOption.MARKET_WEAPON_SIZE_ANY;
        }
    }

    public StellnetDialogOption getCargoWeaponType() {
        return cargoWeaponType;
    }

    public void setCargoWeaponType(StellnetDialogOption option) {
        if (option == StellnetDialogOption.MARKET_WEAPON_TYPE_ANY) {
            cargoWeaponType = StellnetDialogOption.MARKET_WEAPON_TYPE_BALLISTIC;
        } else if (option == StellnetDialogOption.MARKET_WEAPON_TYPE_BALLISTIC) {
            cargoWeaponType = StellnetDialogOption.MARKET_WEAPON_TYPE_ENERGY;
        } else if (option == StellnetDialogOption.MARKET_WEAPON_TYPE_ENERGY) {
            cargoWeaponType = StellnetDialogOption.MARKET_WEAPON_TYPE_MISSILE;
        } else if (option == StellnetDialogOption.MARKET_WEAPON_TYPE_MISSILE) {
            cargoWeaponType = StellnetDialogOption.MARKET_WEAPON_TYPE_ANY;
        }
    }

    public StellnetDialogOption getCargoWingType() {
        return cargoWingType;
    }

    public void setCargoWingType(StellnetDialogOption option) {
        if (option == StellnetDialogOption.MARKET_WING_TYPE_ANY) {
            cargoWingType = StellnetDialogOption.MARKET_WING_TYPE_INTERCEPTOR;
        } else if (option == StellnetDialogOption.MARKET_WING_TYPE_INTERCEPTOR) {
            cargoWingType = StellnetDialogOption.MARKET_WING_TYPE_FIGHTER;
        } else if (option == StellnetDialogOption.MARKET_WING_TYPE_FIGHTER) {
            cargoWingType = StellnetDialogOption.MARKET_WING_TYPE_BOMBER;
        } else if (option == StellnetDialogOption.MARKET_WING_TYPE_BOMBER) {
            cargoWingType = StellnetDialogOption.MARKET_WING_TYPE_ANY;
        }
    }

    public StellnetDialogOption getFleetShipDamaged() {
        return fleetShipDamaged;
    }

    public void setFleetShipDamaged(StellnetDialogOption option) {
        if (option == StellnetDialogOption.SHIP_DAMAGED_NO) {
            fleetShipDamaged = StellnetDialogOption.SHIP_DAMAGED_YES;
        } else if (option == StellnetDialogOption.SHIP_DAMAGED_YES) {
            fleetShipDamaged = StellnetDialogOption.SHIP_DAMAGED_ONLY;
        } else if (option == StellnetDialogOption.SHIP_DAMAGED_ONLY) {
            fleetShipDamaged = StellnetDialogOption.SHIP_DAMAGED_NO;
        }
    }

    public StellnetDialogOption getFleetShipCarrier() {
        return fleetShipCarrier;
    }

    public void setFleetShipCarrier(StellnetDialogOption option) {
        if (option == StellnetDialogOption.SHIP_CARRIER_NO) {
            fleetShipCarrier = StellnetDialogOption.SHIP_CARRIER_YES;
        } else if (option == StellnetDialogOption.SHIP_CARRIER_YES) {
            fleetShipCarrier = StellnetDialogOption.SHIP_CARRIER_ONLY;
        } else if (option == StellnetDialogOption.SHIP_CARRIER_ONLY) {
            fleetShipCarrier = StellnetDialogOption.SHIP_CARRIER_NO;
        }
    }

    public StellnetDialogOption getFleetShipCivilian() {
        return fleetShipCivilian;
    }

    public void setFleetShipCivilian(StellnetDialogOption option) {
        if (option == StellnetDialogOption.SHIP_CIVILIAN_NO) {
            fleetShipCivilian = StellnetDialogOption.SHIP_CIVILIAN_YES;
        } else if (option == StellnetDialogOption.SHIP_CIVILIAN_YES) {
            fleetShipCivilian = StellnetDialogOption.SHIP_CIVILIAN_ONLY;
        } else if (option == StellnetDialogOption.SHIP_CIVILIAN_ONLY) {
            fleetShipCivilian = StellnetDialogOption.SHIP_CIVILIAN_NO;
        }
    }
}
