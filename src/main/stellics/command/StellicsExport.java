package stellics.command;

import java.io.PrintWriter;
import java.util.List;

import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;

import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.Console;

import stellics.Constants;
import stellics.helper.StorageHelper;

public class StellicsExport implements BaseCommand {

    public CommandResult runCommand(String args, CommandContext context) {

        try {
            StorageHelper storageHelper = new StorageHelper();
            SubmarketAPI storage = storageHelper.get();
            PrintWriter stellicsCsv = new PrintWriter("stellics.csv");
            stellicsCsv.println("type,id,quantity");
            exportCsv(stellicsCsv, storage.getCargo().getStacksCopy());
            stellicsCsv.close();
            Console.showMessage("Exported Stellar Logistics Warehouse.");
        } catch (Exception exception) {
            Console.showMessage("Failed to export Stellar Logistics Warehouse!");

            return CommandResult.ERROR;
        }

        return CommandResult.SUCCESS;
    }

    private void exportCsv(PrintWriter stellicsCsv, List<CargoStackAPI> cargoStacks) {
        for (CargoStackAPI cargoStack : cargoStacks) {
            float size = cargoStack.getSize();

            if (cargoStack.isCommodityStack()) {
                export(stellicsCsv, Constants.COMMODITY, cargoStack.getCommodityId(), size);
            } else if (cargoStack.isWeaponStack()) {
                export(stellicsCsv, Constants.WEAPON, cargoStack.getWeaponSpecIfWeapon().getWeaponId(), size);
            } else if (cargoStack.isFighterWingStack()) {
                export(stellicsCsv, Constants.FIGHTER, cargoStack.getFighterWingSpecIfWing().getId(), size);
            } else if (cargoStack.isSpecialStack()) {
                SpecialItemData specialItem = cargoStack.getSpecialDataIfSpecial();
                export(stellicsCsv, specialItem.getId(), specialItem.getData(), size);
            } else {
                Console.showMessage("I: " + cargoStack.getDisplayName() + " (" + size + ")");
            }
        }
    }

    private void export(PrintWriter stellicsCsv, String type, String item, float quantity) {
        Console.showMessage(type.toUpperCase().charAt(0) + ": " + item + " (" + quantity + ")");
        stellicsCsv.println(type + "," + item + "," + quantity);
    }
}
