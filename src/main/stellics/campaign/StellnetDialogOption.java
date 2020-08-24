package stellics.campaign;

public enum StellnetDialogOption {

    // main page
    INIT("Go back"), BRANCH("Locate nearest branch"), OFFICER("Hire officers"), MARKET("Query markets"),
    SHIP("Search for ships"), EXIT("Disconnect from StellNET"),

    // officers page
    OFFICER_TIMID("Timid"), OFFICER_CAUTIOUS("Cautious"), OFFICER_STEADY("Steady"), OFFICER_AGGRESSIVE("Aggresive"),
    OFFICER_RECKLESS("Reckless"),

    // market page
    MARKET_FILTERS("Filters"), MARKET_WEAPON("Weapons"), MARKET_FIGHTER("Wings"), MARKET_MODSPEC("Modspecs"),
    MARKET_BLUEPRINT("Blueprints"), MARKET_BACK("Go back"),

    // market weapon size filter
    MARKET_WEAPON_SIZE_ANY("Weapon size: Any"), MARKET_WEAPON_SIZE_SMALL("Weapon size: Small"),
    MARKET_WEAPON_SIZE_MEDIUM("Weapon size: Medium"), MARKET_WEAPON_SIZE_LARGE("Weapon size: Large"),

    // market weapon type filter
    MARKET_WEAPON_TYPE_ANY("Weapon type: Any"), MARKET_WEAPON_TYPE_BALLISTIC("Weapon type: Ballistic"),
    MARKET_WEAPON_TYPE_ENERGY("Weapon type: Energy"), MARKET_WEAPON_TYPE_MISSILE("Weapon type: Missile"),

    // market wing type filter
    MARKET_WING_TYPE_ANY("Wing type: Any"), MARKET_WING_TYPE_INTERCEPTOR("Wing type: Interceptors"),
    MARKET_WING_TYPE_FIGHTER("Wing type: Fighters"), MARKET_WING_TYPE_BOMBER("Wing type: Bombers"),

    // ship page
    SHIP_FILTERS("Filters"), SHIP_FRIGATE("Frigates"), SHIP_DESTROYER("Destroyers"), SHIP_CRUISER("Cruisers"),
    SHIP_CAPITAL("Capitals"), SHIP_BACK("Go back"),

    // ship max d-mod count filter
    SHIP_DAMAGED_YES("Include damaged ships"), SHIP_DAMAGED_NO("Exclude damaged ships"),
    SHIP_DAMAGED_ONLY("Only damaged ships"),

    // ship carrier ship filter
    SHIP_CARRIER_YES("Include carrier ships"), SHIP_CARRIER_NO("Exclude carrier ships"),
    SHIP_CARRIER_ONLY("Only carrier ships"),

    // ship civilian ship filter
    SHIP_CIVILIAN_YES("Include civilian ships"), SHIP_CIVILIAN_NO("Exclude civilian ships"),
    SHIP_CIVILIAN_ONLY("Only civilian ships");

    final private String name;

    private StellnetDialogOption(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }
}
