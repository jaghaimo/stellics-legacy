package stellics.filter;

import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponSize;

import stellics.campaign.StellnetDialogOption;

public class CargoStackWeaponSize implements CargoStackFilter {

    private StellnetDialogOption option;

    public CargoStackWeaponSize(StellnetDialogOption o) {
        option = o;
    }

    @Override
    public boolean match(CargoStackAPI c) {
        if (!c.isWeaponStack()) {
            return false;
        }

        WeaponSize weaponSize = c.getWeaponSpecIfWeapon().getSize();

        switch (option) {
            case WEAPON_SIZE_SMALL:
                return weaponSize.equals(WeaponSize.SMALL);

            case WEAPON_SIZE_MEDIUM:
                return weaponSize.equals(WeaponSize.MEDIUM);

            case WEAPON_SIZE_LARGE:
                return weaponSize.equals(WeaponSize.LARGE);

            default:
                return true;
        }
    }
}
