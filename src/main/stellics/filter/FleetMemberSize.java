package stellics.filter;

import com.fs.starfarer.api.fleet.FleetMemberAPI;

import stellics.campaign.StellnetDialogOption;

public class FleetMemberSize implements FleetMemberFilter {

    private StellnetDialogOption option;

    public FleetMemberSize(StellnetDialogOption o) {
        option = o;
    }

    @Override
    public boolean match(FleetMemberAPI f) {
        switch (option) {
            case SHIP_FRIGATE:
                return f.isFrigate();

            case SHIP_DESTROYER:
                return f.isDestroyer();

            case SHIP_CRUISER:
                return f.isCruiser();

            case SHIP_CAPITAL:
                return f.isCapital();

            default:
                return false;
        }
    }
}
