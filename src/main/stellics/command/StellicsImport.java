package stellics.command;

import com.fs.starfarer.api.campaign.CargoAPI.CargoItemType;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.Console;

import stellics.Constants;
import stellics.helper.StorageHelper;

public class StellicsImport implements BaseCommand {

    public CommandResult runCommand(String args, CommandContext context) {

        try {
            StorageHelper storageHelper = new StorageHelper();
            SubmarketAPI storage = storageHelper.get();
            JSONArray stellicsCsv = Global.getSettings().loadCSV("stellics.csv");
            importCsv(stellicsCsv, storage.getCargo());
            Console.showMessage("Imported Stellar Logistics Warehouse.");
        } catch (Exception exception) {
            Console.showMessage(exception.toString());
            Console.showMessage("Failed to import Stellar Logistics Warehouse!");

            return CommandResult.ERROR;
        }

        return CommandResult.SUCCESS;
    }

    private void importCsv(JSONArray stellicsCsv, CargoAPI cargo) throws JSONException {
        for (int i = 0; i < stellicsCsv.length(); i++) {
            JSONObject item = stellicsCsv.getJSONObject(i);

            try {
                float quantity = (float) item.getDouble("quantity");
                addToStorage(cargo, item.getString("type"), item.getString("id"), quantity);
            } catch (Exception exception) {
                Console.showMessage(exception.toString());
                Console.showMessage("I: " + item.toString());
            }
        }
    }

    private void addToStorage(CargoAPI cargo, String type, String id, float quantity) {
        switch (type) {
            case Constants.COMMODITY:
                cargo.addItems(CargoItemType.RESOURCES, id, quantity);
                break;

            case Constants.WEAPON:
                cargo.addWeapons(id, (int) quantity);
                break;

            case Constants.FIGHTER:
                cargo.addFighters(id, (int) quantity);
                break;

            default:
                cargo.addSpecial(new SpecialItemData(type, id), quantity);
                break;
        }

        Console.showMessage(type.toUpperCase().charAt(0) + ": " + id + " (" + quantity + ")");
    }
}
