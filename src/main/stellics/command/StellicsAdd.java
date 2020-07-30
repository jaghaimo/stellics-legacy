package stellics.command;

import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.Console;

import stellics.StellicsModPlugin;

public class StellicsAdd implements BaseCommand {

    public CommandResult runCommand(String args, CommandContext context) {

        try {
            StellicsModPlugin stellicsMod = new StellicsModPlugin();
            stellicsMod.onApplicationLoad();
            stellicsMod.onNewGameAfterEconomyLoad();
            Console.showMessage("Added Stellar Logistics content to existing markets.");
        } catch (Exception exception) {
            Console.showMessage("Failed to add Stellar Logistics content to existing markets!");
            return CommandResult.ERROR;
        }

        return CommandResult.SUCCESS;
    }
}
