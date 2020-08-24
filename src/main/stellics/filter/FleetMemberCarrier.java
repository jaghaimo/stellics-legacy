package stellics.filter;

import com.fs.starfarer.api.fleet.FleetMemberAPI;

import stellics.campaign.StellnetDialogOption;

public class FleetMemberCarrier implements FleetMemberFilter {

    private StellnetDialogOption option;

    public FleetMemberCarrier(StellnetDialogOption o) {
        option = o;
    }

    @Override
    public boolean match(FleetMemberAPI f) {
        switch (option) {
            case SHIP_CARRIER_NO:
                return !f.isCarrier();

            case SHIP_CARRIER_ONLY:
                return f.isCarrier();

            default:
                return true;
        }
    }
}
