package stellics.campaign.econ;

import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;

public class HasCargoFilter implements MarketFilter {

    private CargoStackAPI cargoStack;

    public HasCargoFilter(CargoStackAPI cs) {
        cargoStack = cs;
    }

    public boolean match(MarketAPI market) {
        for (SubmarketAPI s : market.getSubmarketsCopy()) {
            for (CargoStackAPI c : s.getCargo().getStacksCopy()) {
                // ideally this should be done based on some unique identifier
                if (cargoStack.getDisplayName().equals(c.getDisplayName())) {
                    return true;
                }
            }
        }

        return false;
    }
}
