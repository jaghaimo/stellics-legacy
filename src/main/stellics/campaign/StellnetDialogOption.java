package stellics.campaign;

public enum StellnetDialogOption {

    INIT("Go back"), EXIT("Disconnect from StellNET"), BRANCH("Locate Stellar Logistics branches"),
    COURIER("Request courier services"), STAFF("Find officers or administrators"), CARGO("Search for matching items"),
    SHIP("Search for specific ships"),

    // courier filter
    COURIER_DIRECTION_FROM("Direction: From the warehouse"), COURIER_DIRECTION_TO("Direction: To the warehouse"),
    COURIER_TRANSFER_CARGO("Transfer: Cargo"), COURIER_TRANSFER_SHIP("Transfer: Ships"),

    // market faction filter
    MARKET_FACTION_ANY("Factions: Any"), MARKET_FACTION_NON_HOSTILE("Factions: Non-hostile"),

    // staff type filter
    STAFF_OFFICER("Staff: Officers"), STAFF_ADMIN("Staff: Administrators"),

    // cargo type filter
    CARGO_TYPE_WEAPON("Cargo: Weapons"), CARGO_TYPE_FIGHTER("Cargo: Fighter wings"),
    CARGO_TYPE_MODSPEC("Cargo: Modspecs"),

    // officers personality filter
    OFFICER_TIMID("Offier: Timid"), OFFICER_CAUTIOUS("Officer: Cautious"), OFFICER_STEADY("Officer: Steady"),
    OFFICER_AGGRESSIVE("Officer: Aggresive"), OFFICER_RECKLESS("Officer: Reckless"),

    // market weapon size filter
    WEAPON_SIZE_ANY("Size: Any"), WEAPON_SIZE_SMALL("Size: Small"), WEAPON_SIZE_MEDIUM("Size: Medium"),
    WEAPON_SIZE_LARGE("Size: Large"),

    // market weapon type filter
    WEAPON_TYPE_ANY("Type: Any"), WEAPON_TYPE_BALLISTIC("Type: Ballistic"), WEAPON_TYPE_ENERGY("Type: Energy"),
    WEAPON_TYPE_MISSILE("Type: Missile"),

    // market wing type filter
    WING_TYPE_ANY("Type: Any"), WING_TYPE_INTERCEPTOR("Type: Interceptors"), WING_TYPE_FIGHTER("Type: Fighters"),
    WING_TYPE_BOMBER("Type: Bombers"),

    // ship page
    SHIP_SIZE_FRIGATE("Size: Frigates"), SHIP_SIZE_DESTROYER("Size: Destroyers"), SHIP_SIZE_CRUISER("Size: Cruisers"),
    SHIP_SIZE_CAPITAL("Size: Capitals"),

    // ship max d-mod count filter
    SHIP_DAMAGED_YES("Damaged: Include"), SHIP_DAMAGED_NO("Damaged: Exclude"), SHIP_DAMAGED_ONLY("Damaged: Only"),

    // ship carrier ship filter
    SHIP_CARRIER_YES("Carriers: Include"), SHIP_CARRIER_NO("Carriers: Exclude"), SHIP_CARRIER_ONLY("Carriers: Only"),

    // ship civilian ship filter
    SHIP_CIVILIAN_YES("Civilian: Include"), SHIP_CIVILIAN_NO("Civilian: Exclude"), SHIP_CIVILIAN_ONLY("Civilian: Only");

    final private String name;

    private StellnetDialogOption(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }
}
