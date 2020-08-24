package stellics.filter;

import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;

import stellics.campaign.StellnetDialogOption;

public class FleetMemberCivilian implements FleetMemberFilter {

    private StellnetDialogOption option;

    public FleetMemberCivilian(StellnetDialogOption o) {
        option = o;
    }

    @Override
    public boolean match(FleetMemberAPI f) {
        switch (option) {
            case SHIP_CIVILIAN_NO:
                return !f.getVariant().hasHullMod(HullMods.CIVGRADE);

            case SHIP_CIVILIAN_ONLY:
                return f.getVariant().hasHullMod(HullMods.CIVGRADE);

            default:
                return true;
        }
    }
}
