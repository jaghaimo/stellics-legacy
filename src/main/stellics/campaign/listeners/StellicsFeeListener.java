package stellics.campaign.listeners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MonthlyReport;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.listeners.EconomyTickListener;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;

import org.lazywizard.lazylib.campaign.CargoUtils;

import stellics.Constants;

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

        int spaceUsed = (int) CargoUtils.getSpaceTakenByCargo(stellicsStorage.getCargo());
        int upkeep = calculateUpkeep(spaceUsed);
        updateCurrentReport(spaceUsed, upkeep);
    }

    public void reportEconomyMonthEnd() {
    }

    private int calculateUpkeep(int spaceUsed) {
        int currentUpkeep = 0;
        int economyIterPerMonth = (int) Global.getSettings().getFloat("economyIterPerMonth");

        // first 1000 costs 9 per unit, 1001-10000 costs 5 per unit, 10001+ costs 1 per unit
        currentUpkeep += 4 * Math.min(spaceUsed, 1000);
        currentUpkeep += 4 * Math.min(spaceUsed, 10000);
        currentUpkeep += 1 * spaceUsed;

        // divide by number of ticks per month
        return currentUpkeep / economyIterPerMonth;
    }

    private void updateCurrentReport(int spaceUsed, int upkeep) {
        MonthlyReport report = SharedData.getData().getCurrentReport();
        MonthlyReport.FDNode coloniesNode = report.getNode(MonthlyReport.OUTPOSTS);
        MonthlyReport.FDNode stellicsNode = report.getNode(coloniesNode, "stellics_fee");

        stellicsNode.name = "Stellar Logistics Warehouse (" + spaceUsed + " units)";
        stellicsNode.mapEntity = market.getPrimaryEntity();
        stellicsNode.icon = "graphics/icons/reports/generic_expense.png";
        stellicsNode.income = 0;
        stellicsNode.upkeep += upkeep;
    }
}
