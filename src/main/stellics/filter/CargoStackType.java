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
            case MARKET_WEAPON:
                return c.isWeaponStack();

            case MARKET_FIGHTER:
                return c.isFighterWingStack();

            case MARKET_MODSPEC:
                return c.isSpecialStack() && c.getSpecialDataIfSpecial().getId().equals("modspec");

            case MARKET_BLUEPRINT:
                return c.isSpecialStack() && c.getSpecialDataIfSpecial().getId().endsWith("_bp");

            default:
                return false;
        }
    }
}
