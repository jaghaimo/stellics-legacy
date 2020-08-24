package stellics.filter;

import com.fs.starfarer.api.fleet.FleetMemberAPI;

import stellics.campaign.StellnetDialogOption;

public class FleetMemberDamaged implements FleetMemberFilter {

    private StellnetDialogOption option;

    public FleetMemberDamaged(StellnetDialogOption o) {
        option = o;
    }

    @Override
    public boolean match(FleetMemberAPI f) {
        switch (option) {
            case SHIP_DAMAGED_NO:
                return !f.getHullSpec().isDHull();

            case SHIP_DAMAGED_ONLY:
                return f.getHullSpec().isDHull();

            default:
                return true;
        }
    }
}
