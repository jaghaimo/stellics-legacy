package stellics.helper;

import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

public class CargoHelper {

    private static enum CostType {
        CARGO, FLEET
    }

    public static int calculateCargoUpkeep(CargoAPI cargo) {
        int spaceUsed = CargoHelper.calculateCargoSpace(cargo);

        return getCost(spaceUsed, CostType.CARGO);
    }

    public static int calculateShipUpkeep(CargoAPI cargo) {
        int spaceUsed = CargoHelper.calculateShipSpace(cargo);

        return getCost(spaceUsed, CostType.FLEET);
    }

    // Space is a sum of ordenance points
    public static int calculateShipSpace(CargoAPI cargo) {
        int fleetCost = 0;

        for (FleetMemberAPI ship : cargo.getMothballedShips().getMembersListCopy()) {
            fleetCost += ship.getHullSpec().getOrdnancePoints(null);
        }

        return fleetCost;
    }

    public static int calculateCargoSpace(CargoAPI cargo) {
        int cargoSpace = 0;

        for (CargoStackAPI stack : cargo.getStacksCopy()) {
            cargoSpace += stack.getCargoSpace();
        }

        return cargoSpace;
    }

    public static CargoAPI getCargo(List<CargoStackAPI> cargoStacks) {
        CargoAPI cargo = Global.getFactory().createCargo(true);

        for (CargoStackAPI cargoStack : cargoStacks) {
            cargo.addFromStack(cargoStack);
        }

        cargo.sort();

        return cargo;
    }

    private static int getCost(int spaceUsed, CostType type) {
        int currentUpkeep = 0;

        String prefix = type.name().toLowerCase();

        int tier1cost = Global.getSettings().getInt(prefix + "Tier1Cost");
        int tier2cost = Global.getSettings().getInt(prefix + "Tier2Cost");
        int tier3cost = Global.getSettings().getInt(prefix + "Tier3Cost");

        int tier1quantity = Global.getSettings().getInt(prefix + "Tier1Quantity");
        int tier2quantity = Global.getSettings().getInt(prefix + "Tier2Quantity");

        currentUpkeep += (tier1cost - tier2cost) * Math.min(spaceUsed, tier1quantity);
        currentUpkeep += (tier2cost - tier3cost) * Math.min(spaceUsed, tier2quantity);
        currentUpkeep += tier3cost * spaceUsed;

        return currentUpkeep;
    }
}
