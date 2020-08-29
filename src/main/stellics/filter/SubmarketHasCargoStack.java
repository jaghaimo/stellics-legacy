package stellics.filter;

import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;

public class SubmarketHasCargoStack implements SubmarketFilter {

    private CargoStackAPI cargoStack;

    public SubmarketHasCargoStack(CargoStackAPI cs) {
        cargoStack = cs;
    }

    public boolean match(SubmarketAPI submarket) {
        for (CargoStackAPI c : submarket.getCargo().getStacksCopy()) {
            if (cargoStack.getDisplayName().equals(c.getDisplayName())) {
                return true;
            }
        }

        return false;
    }
}
