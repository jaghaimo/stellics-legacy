package stellics.helper;

import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

public class CargoHelper {

    public static int calculateCargoUpkeep(CargoAPI cargo) {
        int spaceUsed = CargoHelper.calculateSpaceUsed(cargo);
        int currentUpkeep = 0;

        int tier1cost = Global.getSettings().getInt("cargoTier1Cost");
        int tier2cost = Global.getSettings().getInt("cargoTier2Cost");
        int tier3cost = Global.getSettings().getInt("cargoTier3Cost");

        int tier1quantity = Global.getSettings().getInt("cargoTier1Quantity");
        int tier2quantity = Global.getSettings().getInt("cargoTier2Quantity");

        currentUpkeep += (tier1cost - tier2cost - tier3cost) * Math.min(spaceUsed, tier1quantity);
        currentUpkeep += (tier2cost - tier3cost) * Math.min(spaceUsed, tier2quantity);
        currentUpkeep += tier3cost * spaceUsed;

        return currentUpkeep;
    }

    public static int calculateFleetUpkeep(CargoAPI cargo) {
        int currentUpkeep = 0;

        int tier1cost = Global.getSettings().getInt("fleetFrigateCost");
        int tier2cost = Global.getSettings().getInt("fleetDestroyerCost");
        int tier3cost = Global.getSettings().getInt("fleetCruiserCost");
        int tier4cost = Global.getSettings().getInt("fleetCapitalCost");

        float civilianMulti = Global.getSettings().getFloat("fleetCivilianMulti");
        float carrierMulti = Global.getSettings().getFloat("fleetCarrierMulti");

        for (FleetMemberAPI ship : cargo.getMothballedShips().getMembersListCopy()) {
            int shipUpkeep = 0;

            // base upkeep depends on ship class
            if (ship.isFrigate()) shipUpkeep = tier1cost;
            if (ship.isDestroyer()) shipUpkeep = tier2cost;
            if (ship.isCruiser()) shipUpkeep = tier3cost;
            if (ship.isCapital()) shipUpkeep = tier4cost;

            // reduction for civilians
            if (ship.isCivilian()) shipUpkeep *= civilianMulti;

            // increase for carriers
            if (ship.isCarrier()) shipUpkeep *= carrierMulti;

            currentUpkeep += shipUpkeep;
        }

        return currentUpkeep;
    }

    public static int calculateSpaceUsed(CargoAPI cargo) {
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

}
