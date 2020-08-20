package stellics.filter;

import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;

public class MarketHasCargoStack implements MarketFilter {

    private CargoStackAPI cargoStack;

    public MarketHasCargoStack(CargoStackAPI cs) {
        cargoStack = cs;
    }

    public boolean match(MarketAPI market) {
        for (SubmarketAPI s : market.getSubmarketsCopy()) {
            for (CargoStackAPI c : s.getCargo().getStacksCopy()) {
                if (cargoStack.getDisplayName().equals(c.getDisplayName())) {
                    return true;
                }
            }
        }

        return false;
    }
}
