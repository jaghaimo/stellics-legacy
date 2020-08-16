package stellics.helper;

import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;

public class CargoHelper {

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
