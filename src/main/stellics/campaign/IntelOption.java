package stellics.campaign;

public enum IntelOption {

    // main page
    INIT("Go back"), BRANCH("Locate nearest branch"), OFFICER("Find officers"), QUERY("Query markets"),
    SHIP("Locate ships"), EXIT("Disconnect from StellNET"),

    // officers page
    TIMID("Timid"), CAUTIOUS("Cautious"), STEADY("Steady"), AGGRESSIVE("Aggresive"), RECKLESS("Reckless"),

    // query page
    WEAPON("Weapons"), FIGHTER("Fighters"), MODSPEC("Modspecs"), BLUEPRINT("Blueprints"),

    // ship page
    FRIGATE("Frigates"), DESTROYER("Destroyers"), CRUISER("Cruisers"), CAPITAL("Capitals");

    final private String name;

    private IntelOption(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }
}
