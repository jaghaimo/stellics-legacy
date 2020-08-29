package stellics.filter;

import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponType;

import stellics.campaign.StellnetDialogOption;

public class CargoStackWeaponType implements CargoStackFilter {

    private StellnetDialogOption option;

    public CargoStackWeaponType(StellnetDialogOption o) {
        option = o;
    }

    @Override
    public boolean match(CargoStackAPI c) {
        if (!c.isWeaponStack()) {
            return false;
        }

        WeaponType weaponType = c.getWeaponSpecIfWeapon().getType();

        switch (option) {
            case WEAPON_TYPE_BALLISTIC:
                return weaponType.equals(WeaponType.BALLISTIC);

            case WEAPON_TYPE_ENERGY:
                return weaponType.equals(WeaponType.ENERGY);

            case WEAPON_TYPE_MISSILE:
                return weaponType.equals(WeaponType.MISSILE);

            default:
                return true;
        }
    }
}
