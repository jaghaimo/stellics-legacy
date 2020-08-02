package stellics.campaign.listeners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MonthlyReport;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.listeners.EconomyTickListener;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;

import stellics.Constants;

// This listener should be attached to the founding market only (market that build the first branch)
public class StellicsFeeListener implements EconomyTickListener {

    private MarketAPI market;

    public StellicsFeeListener(MarketAPI marketApi) {
        market = marketApi;
    }

    public void reportEconomyTick(int iterIndex) {
        int economyIterPerMonth = (int) Global.getSettings().getFloat("economyIterPerMonth");

        SubmarketAPI stellicsStorage = market.getSubmarket(Constants.STORAGE);

        if (stellicsStorage == null) {
            return;
        }

        int tickFee = calculateSpaceUsed(stellicsStorage.getCargo()) / economyIterPerMonth;
        updateCurrentReport(tickFee);
    }

    public void reportEconomyMonthEnd() {
    }

    private int calculateSpaceUsed(CargoAPI cargo) {
        int cargoSize = 0;

        for (CargoStackAPI stack : cargo.getStacksCopy()) {
            cargoSize += stack.getCargoSpace();
        }

        return cargoSize;
    }

    private void updateCurrentReport(int tickFee) {
        MonthlyReport report = SharedData.getData().getCurrentReport();
        MonthlyReport.FDNode coloniesNode = report.getNode(MonthlyReport.OUTPOSTS);
        MonthlyReport.FDNode stellicsNode = report.getNode(coloniesNode, "stellics_fee");

        stellicsNode.name = "Stellar Logistics Warehouse (cargo)";
        stellicsNode.mapEntity = market.getPrimaryEntity();
        stellicsNode.icon = "graphics/icons/reports/generic_expense.png";
        stellicsNode.income = 0;
        stellicsNode.upkeep += tickFee;
    }
}
