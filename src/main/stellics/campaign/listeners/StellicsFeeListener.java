package stellics.campaign.listeners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MonthlyReport;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.listeners.EconomyTickListener;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;

import stellics.Constants;
import stellics.helper.CargoHelper;

// This listener should be attached to the founding market only (market that build the first branch)
public class StellicsFeeListener implements EconomyTickListener {

    private MarketAPI market;

    public StellicsFeeListener(MarketAPI marketApi) {
        market = marketApi;
    }

    public void reportEconomyTick(int iterIndex) {
        SubmarketAPI stellicsStorage = market.getSubmarket(Constants.STORAGE);

        if (stellicsStorage == null) {
            return;
        }

        CargoAPI cargo = stellicsStorage.getCargo();
        int economyIterPerMonth = (int) Global.getSettings().getFloat("economyIterPerMonth");
        int cargoUpkeep = calculateCargoUpkeep(cargo) / economyIterPerMonth;
        int fleetUpkeep = calculateFleetUpkeep(cargo) / economyIterPerMonth;
        updateCurrentReport(cargoUpkeep + fleetUpkeep);
    }

    public void reportEconomyMonthEnd() {
    }

    private int calculateCargoUpkeep(CargoAPI cargo) {
        int spaceUsed = CargoHelper.calculateSpaceUsed(cargo);
        int currentUpkeep = 0;

        // first 1000 costs 9 per unit, 1001-10000 costs 5 per unit, 10001+ costs 1 per unit
        currentUpkeep += 4 * Math.min(spaceUsed, 1000);
        currentUpkeep += 4 * Math.min(spaceUsed, 10000);
        currentUpkeep += 1 * spaceUsed;

        // divide by number of ticks per month
        return currentUpkeep;
    }

    private int calculateFleetUpkeep(CargoAPI cargo) {
       int currentUpkeep = 0;

       for (FleetMemberAPI ship : cargo.getMothballedShips().getMembersListCopy()) {
           int shipUpkeep = 0;

           // base upkeep depends on ship class
           if (ship.isFrigate()) shipUpkeep = 100;
           if (ship.isDestroyer()) shipUpkeep = 1000;
           if (ship.isCruiser()) shipUpkeep = 10000;
           if (ship.isCapital()) shipUpkeep = 100000;

           // reduction for civilians
           if (ship.isCivilian()) shipUpkeep /= 2;

           // increase for carriers
           if (ship.isCarrier()) shipUpkeep *= 2;

           currentUpkeep += shipUpkeep;
       }

       return currentUpkeep;
    }

    private void updateCurrentReport(int upkeep) {
        MonthlyReport report = SharedData.getData().getCurrentReport();
        MonthlyReport.FDNode coloniesNode = report.getNode(MonthlyReport.OUTPOSTS);
        MonthlyReport.FDNode stellicsNode = report.getNode(coloniesNode, "stellics_fee");

        stellicsNode.name = "Stellar Logistics Warehouse";
        stellicsNode.mapEntity = market.getPrimaryEntity();
        stellicsNode.icon = "graphics/icons/reports/generic_expense.png";
        stellicsNode.income = 0;
        stellicsNode.upkeep += upkeep;
    }
}
