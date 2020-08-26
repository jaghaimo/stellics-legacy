package stellics.campaign.econ;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CoreUIAPI;
import com.fs.starfarer.api.campaign.SubmarketPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.submarkets.StoragePlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import stellics.helper.CargoHelper;

public class StorageSubmarket extends StoragePlugin {

    @Override
    public OnClickAction getOnClickAction(CoreUIAPI ui) {
        boolean isPlayerOwned = market.isPlayerOwned();

        if (isPlayerOwned) {
            return OnClickAction.OPEN_SUBMARKET;
        }

        return super.getOnClickAction(ui);
    }

    @Override
    public boolean isFreeTransfer() {
        return true;
    }

    @Override
    public boolean isIllegalOnSubmarket(String commodityId, SubmarketPlugin.TransferAction action) {
        if (commodityId.equals(Commodities.CREW)) {
            return true;
        }

        if (commodityId.equals(Commodities.MARINES)) {
            return true;
        }

        return false;
    }

    @Override
    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
        CargoAPI cargo = submarket.getCargo();
        int cargoCost = CargoHelper.calculateCargoUpkeep(cargo);
        int shipCost = CargoHelper.calculateShipUpkeep(cargo);

        if (cargoCost + shipCost > 0) {
            tooltip.addPara("Monthly fees and expenses:", 10f);
            tooltip.beginGridFlipped(300, 1, 80, 10);
            tooltip.addToGrid(0, 0, "Ships in storage", Misc.getDGSCredits(shipCost));
            tooltip.addToGrid(0, 1, "Cargo in storage", Misc.getDGSCredits(cargoCost));
            tooltip.addGrid(3f);

        }
    }
}