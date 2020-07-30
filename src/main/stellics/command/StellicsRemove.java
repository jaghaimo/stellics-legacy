package stellics.command;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.Console;

import stellics.Constants;

public class StellicsRemove implements BaseCommand {

    public CommandResult runCommand(String args, CommandContext context) {

        try {
            EconomyAPI economy = Global.getSector().getEconomy();

            for (MarketAPI marketCopy : economy.getMarketsCopy()) {
                MarketAPI market = economy.getMarket(marketCopy.getId());
                market.removeIndustry(Constants.BRANCH, null, false);
                market.removeSubmarket(Constants.STORAGE);
            }

            Console.showMessage("Removed Stellar Logistics content - save and disable the mod.");
        } catch (Exception exception) {
            Console.showMessage("Failed to remove Stellar Logistics content!");
            return CommandResult.ERROR;
        }

        return CommandResult.SUCCESS;
    }
}
