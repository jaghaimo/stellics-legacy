package stellics.filter;

import com.fs.starfarer.api.campaign.CargoStackAPI;

import stellics.campaign.StellnetDialogOption;

public class CargoStackType implements CargoStackFilter {

    private StellnetDialogOption option;

    public CargoStackType(StellnetDialogOption o) {
        option = o;
    }

    @Override
    public boolean match(CargoStackAPI c) {
        switch (option) {
            case CARGO_TYPE_WEAPON:
                return c.isWeaponStack();

            case CARGO_TYPE_FIGHTER:
                return c.isFighterWingStack();

            case CARGO_TYPE_MODSPEC:
                return c.isSpecialStack() && c.getSpecialDataIfSpecial().getId().equals("modspec");

            default:
                return false;
        }
    }
}
