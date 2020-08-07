package stellics.campaign.abilities;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.impl.campaign.abilities.BaseDurationAbility;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import stellics.campaign.StellicsIntelDialogPlugin;

public class StellicsLocation extends BaseDurationAbility {

    @Override
    protected void activateImpl() {
        InteractionDialogPlugin dialog = new StellicsIntelDialogPlugin();
        Global.getSector().getCampaignUI().showInteractionDialog(dialog, null);
    }

    @Override
    protected void applyEffect(float arg0, float arg1) {
    }

    @Override
    protected void cleanupImpl() {
    }

    @Override
    protected void deactivateImpl() {
    }

    @Override
    public void createTooltip(TooltipMakerAPI tooltip, boolean expanded) {
        tooltip.addTitle("Connect to StellNET");
        tooltip.addPara("Stellar Logistics provides free access to its vast inter-sector network.", 10f);

        if (expanded) {
            Color color = Misc.getBasePlayerColor();
            tooltip.addPara("Use StellNET to find out the following:", 10f);
            tooltip.addPara("   Locate nearest Stellar Logistics Branch", color, 3f);
        }
    }

    @Override
    public boolean hasTooltip() {
        return true;
    }
}
