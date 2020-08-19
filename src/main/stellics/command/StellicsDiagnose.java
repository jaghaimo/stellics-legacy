package stellics.command;

import java.util.Arrays;
import java.util.List;

import com.fs.starfarer.api.campaign.econ.MarketAPI;

import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.Console;

import stellics.Constants;
import stellics.campaign.econ.HasSubmarketFilter;
import stellics.campaign.econ.MarketFilter;
import stellics.campaign.econ.NoIndustryFilter;
import stellics.helper.MarketHelper;
import stellics.helper.StorageHelper;

public class StellicsDiagnose implements BaseCommand {

    public CommandResult runCommand(String args, CommandContext context) {

        try {
            boolean doFix = args.equals("fix");

            listMarkets("The following markets have a Stellar Logistics Branch:", false,
                    new HasSubmarketFilter(Constants.STORAGE));

            listMarkets("The following markets should not have access to the Stellar Logistics Warehouse:", doFix,
                    new NoIndustryFilter(Constants.BRANCH), new HasSubmarketFilter(Constants.STORAGE));

        } catch (Exception exception) {
            Console.showMessage("Stellar Logistics diagnose run failed.");

            return CommandResult.ERROR;
        }

        return CommandResult.SUCCESS;
    }

    private void listMarkets(String message, boolean doFix, MarketFilter... filters) {
        List<MarketAPI> markets = MarketHelper.findMarkets(Arrays.asList(filters));

        if (markets.isEmpty()) {
            return;
        }

        Console.showMessage(message);

        for (MarketAPI m : markets) {
            String line = " - " + m.getName();

            if (m.getStarSystem() != null) {
                line += " in " + m.getStarSystem().getName();
            }

            if (doFix && StorageHelper.remove(m)) {
                line += " (removed)";
            }

            Console.showMessage(line);
        }
    }
}
