package stellics.campaign.submarkets;

import com.fs.starfarer.api.campaign.CoreUIAPI;
import com.fs.starfarer.api.campaign.SubmarketPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.submarkets.StoragePlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class StellicsStorage extends StoragePlugin {

    @Override
    public OnClickAction getOnClickAction(CoreUIAPI ui)
    {
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
    public boolean showInFleetScreen() {
        return false;
    }

    @Override
    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
    }
}
