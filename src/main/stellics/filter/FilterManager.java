package stellics.filter;

import java.util.ArrayList;
import java.util.List;

import stellics.campaign.StellnetDialogOption;

public class FilterManager {

    private StellnetDialogOption cargoType = StellnetDialogOption.CARGO_TYPE_WEAPON;
    private StellnetDialogOption cargoWeaponSize = StellnetDialogOption.WEAPON_SIZE_ANY;
    private StellnetDialogOption cargoWeaponType = StellnetDialogOption.WEAPON_TYPE_ANY;
    private StellnetDialogOption cargoWingType = StellnetDialogOption.WING_TYPE_ANY;

    private StellnetDialogOption courierDirection = StellnetDialogOption.COURIER_DIRECTION_FROM;
    private StellnetDialogOption courierTransfer = StellnetDialogOption.COURIER_TRANSFER_CARGO;

    private StellnetDialogOption fleetShipSize = StellnetDialogOption.SHIP_SIZE_FRIGATE;
    private StellnetDialogOption fleetShipDamaged = StellnetDialogOption.SHIP_DAMAGED_YES;
    private StellnetDialogOption fleetShipCarrier = StellnetDialogOption.SHIP_CARRIER_YES;
    private StellnetDialogOption fleetShipCivilian = StellnetDialogOption.SHIP_CIVILIAN_NO;

    private StellnetDialogOption marketFaction = StellnetDialogOption.MARKET_FACTION_NON_HOSTILE;

    private StellnetDialogOption staffType = StellnetDialogOption.STAFF_OFFICER;
    private StellnetDialogOption staffOfficer = StellnetDialogOption.OFFICER_STEADY;

    public FilterManager() {
    }

    public List<CargoStackFilter> listCargoFilters() {
        List<CargoStackFilter> filters = new ArrayList<CargoStackFilter>();
        filters.add(new CargoStackType(cargoType));

        if (cargoType == StellnetDialogOption.CARGO_TYPE_WEAPON) {
            filters.add(new CargoStackWeaponSize(cargoWeaponSize));
            filters.add(new CargoStackWeaponType(cargoWeaponType));
        } else if (cargoType == StellnetDialogOption.CARGO_TYPE_FIGHTER) {
            filters.add(new CargoStackWingType(cargoWingType));
        }

        return filters;
    }

    public List<FleetMemberFilter> listFleetFilters() {
        List<FleetMemberFilter> filters = new ArrayList<FleetMemberFilter>();
        filters.add(new FleetMemberSize(fleetShipSize));
        filters.add(new FleetMemberDamaged(fleetShipDamaged));
        filters.add(new FleetMemberCarrier(fleetShipCarrier));
        filters.add(new FleetMemberCivilian(fleetShipCivilian));

        return filters;
    }

    public List<MarketFilter> listMarketFilters() {
        List<MarketFilter> filters = new ArrayList<MarketFilter>();
        filters.add(new MarketNonHostile(marketFaction));

        return filters;
    }

    public List<SubmarketFilter> listSubmarketFilters() {
        List<SubmarketFilter> filters = new ArrayList<SubmarketFilter>();
        filters.add(new SubmarketNonCustom());

        return filters;
    }

    public StellnetDialogOption getCargoType() {
        return cargoType;
    }

    public void setCargoType(StellnetDialogOption option) {
        if (option == StellnetDialogOption.CARGO_TYPE_WEAPON) {
            cargoType = StellnetDialogOption.CARGO_TYPE_FIGHTER;
        } else if (option == StellnetDialogOption.CARGO_TYPE_FIGHTER) {
            cargoType = StellnetDialogOption.CARGO_TYPE_MODSPEC;
        } else if (option == StellnetDialogOption.CARGO_TYPE_MODSPEC) {
            cargoType = StellnetDialogOption.CARGO_TYPE_WEAPON;
        }
    }

    public StellnetDialogOption getCargoWeaponSize() {
        return cargoWeaponSize;
    }

    public void setCargoWeaponSize(StellnetDialogOption option) {
        if (option == StellnetDialogOption.WEAPON_SIZE_ANY) {
            cargoWeaponSize = StellnetDialogOption.WEAPON_SIZE_SMALL;
        } else if (option == StellnetDialogOption.WEAPON_SIZE_SMALL) {
            cargoWeaponSize = StellnetDialogOption.WEAPON_SIZE_MEDIUM;
        } else if (option == StellnetDialogOption.WEAPON_SIZE_MEDIUM) {
            cargoWeaponSize = StellnetDialogOption.WEAPON_SIZE_LARGE;
        } else if (option == StellnetDialogOption.WEAPON_SIZE_LARGE) {
            cargoWeaponSize = StellnetDialogOption.WEAPON_SIZE_ANY;
        }
    }

    public StellnetDialogOption getCargoWeaponType() {
        return cargoWeaponType;
    }

    public void setCargoWeaponType(StellnetDialogOption option) {
        if (option == StellnetDialogOption.WEAPON_TYPE_ANY) {
            cargoWeaponType = StellnetDialogOption.WEAPON_TYPE_BALLISTIC;
        } else if (option == StellnetDialogOption.WEAPON_TYPE_BALLISTIC) {
            cargoWeaponType = StellnetDialogOption.WEAPON_TYPE_ENERGY;
        } else if (option == StellnetDialogOption.WEAPON_TYPE_ENERGY) {
            cargoWeaponType = StellnetDialogOption.WEAPON_TYPE_MISSILE;
        } else if (option == StellnetDialogOption.WEAPON_TYPE_MISSILE) {
            cargoWeaponType = StellnetDialogOption.WEAPON_TYPE_ANY;
        }
    }

    public StellnetDialogOption getCargoWingType() {
        return cargoWingType;
    }

    public void setCargoWingType(StellnetDialogOption option) {
        if (option == StellnetDialogOption.WING_TYPE_ANY) {
            cargoWingType = StellnetDialogOption.WING_TYPE_INTERCEPTOR;
        } else if (option == StellnetDialogOption.WING_TYPE_INTERCEPTOR) {
            cargoWingType = StellnetDialogOption.WING_TYPE_FIGHTER;
        } else if (option == StellnetDialogOption.WING_TYPE_FIGHTER) {
            cargoWingType = StellnetDialogOption.WING_TYPE_BOMBER;
        } else if (option == StellnetDialogOption.WING_TYPE_BOMBER) {
            cargoWingType = StellnetDialogOption.WING_TYPE_ANY;
        }
    }

    public StellnetDialogOption getCourierDirection() {
        return courierDirection;
    }

    public void setCourierDirection(StellnetDialogOption option) {
        if (option == StellnetDialogOption.COURIER_DIRECTION_TO) {
            courierDirection = StellnetDialogOption.COURIER_DIRECTION_FROM;
        } else if (option == StellnetDialogOption.COURIER_DIRECTION_FROM) {
            courierDirection = StellnetDialogOption.COURIER_DIRECTION_TO;
        }
    }

    public StellnetDialogOption getCourierTransfer() {
        return courierTransfer;
    }

    public void setCourierTransfer(StellnetDialogOption option) {
        if (option == StellnetDialogOption.COURIER_TRANSFER_SHIP) {
            courierTransfer = StellnetDialogOption.COURIER_TRANSFER_CARGO;
        } else if (option == StellnetDialogOption.COURIER_TRANSFER_CARGO) {
            courierTransfer = StellnetDialogOption.COURIER_TRANSFER_SHIP;
        }
    }

    public StellnetDialogOption getFleetShipSize() {
        return fleetShipSize;
    }

    public void setFleetShipSize(StellnetDialogOption option) {
        if (option == StellnetDialogOption.SHIP_SIZE_CAPITAL) {
            fleetShipSize = StellnetDialogOption.SHIP_SIZE_FRIGATE;
        } else if (option == StellnetDialogOption.SHIP_SIZE_FRIGATE) {
            fleetShipSize = StellnetDialogOption.SHIP_SIZE_DESTROYER;
        } else if (option == StellnetDialogOption.SHIP_SIZE_DESTROYER) {
            fleetShipSize = StellnetDialogOption.SHIP_SIZE_CRUISER;
        } else if (option == StellnetDialogOption.SHIP_SIZE_CRUISER) {
            fleetShipSize = StellnetDialogOption.SHIP_SIZE_CAPITAL;
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

    public StellnetDialogOption getMarketFaction() {
        return marketFaction;
    }

    public void setMarketFaction(StellnetDialogOption option) {
        if (option == StellnetDialogOption.MARKET_FACTION_ANY) {
            marketFaction = StellnetDialogOption.MARKET_FACTION_NON_HOSTILE;
        } else if (option == StellnetDialogOption.MARKET_FACTION_NON_HOSTILE) {
            marketFaction = StellnetDialogOption.MARKET_FACTION_ANY;
        }
    }

    public StellnetDialogOption getStaffType() {
        return staffType;
    }

    public void setStaffType(StellnetDialogOption option) {
        if (option == StellnetDialogOption.STAFF_OFFICER) {
            staffType = StellnetDialogOption.STAFF_ADMIN;
        } else if (option == StellnetDialogOption.STAFF_ADMIN) {
            staffType = StellnetDialogOption.STAFF_OFFICER;
        }
    }

    public StellnetDialogOption getStaffOfficer() {
        return staffOfficer;
    }

    public void setStaffOfficer(StellnetDialogOption option) {
        if (option == StellnetDialogOption.OFFICER_TIMID) {
            staffOfficer = StellnetDialogOption.OFFICER_CAUTIOUS;
        } else if (option == StellnetDialogOption.OFFICER_CAUTIOUS) {
            staffOfficer = StellnetDialogOption.OFFICER_STEADY;
        } else if (option == StellnetDialogOption.OFFICER_STEADY) {
            staffOfficer = StellnetDialogOption.OFFICER_AGGRESSIVE;
        } else if (option == StellnetDialogOption.OFFICER_AGGRESSIVE) {
            staffOfficer = StellnetDialogOption.OFFICER_RECKLESS;
        } else if (option == StellnetDialogOption.OFFICER_RECKLESS) {
            staffOfficer = StellnetDialogOption.OFFICER_TIMID;
        }
    }
}
