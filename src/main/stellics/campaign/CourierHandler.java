package stellics.campaign;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoPickerListener;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.FleetDataAPI;
import com.fs.starfarer.api.campaign.FleetMemberPickerListener;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.MutableValue;

import stellics.filter.FilterManager;
import stellics.helper.CargoHelper;
import stellics.helper.StorageHelper;

public class CourierHandler extends BasicHandler implements CargoPickerListener, FleetMemberPickerListener {

    private String dialogTitle;

    private CargoAPI sourceCargo;
    private CargoAPI targetCargo;

    private FleetDataAPI sourceFleet;
    private FleetDataAPI targetFleet;

    private FilterManager filterManager;

    public CourierHandler(StellnetDialogPlugin p) {
        super(p);
        filterManager = p.getFilterManager();
    }

    @Override
    public void cancelledFleetMemberPicking() {
        plugin.addText("Ship transfer cancelled...");
    }

    @Override
    public void pickedFleetMembers(List<FleetMemberAPI> fleet) {
        List<FleetMemberAPI> fleetCopy = new ArrayList<FleetMemberAPI>(fleet);
        for (FleetMemberAPI f : fleet) {
            // never transfer the player ship
            if (f.getCaptain().isPlayer()) {
                fleetCopy.remove(f);
            }
        }

        if (fleetCopy.isEmpty()) {
            cancelledFleetMemberPicking();
            return;
        }

        if (!handleTransferCost(CargoHelper.calculateShipUpkeep(fleetCopy))) {
            return;
        }

        for (FleetMemberAPI f : fleetCopy) {
            sourceFleet.removeFleetMember(f);
            targetFleet.addFleetMember(f);
            plugin.appendText("\n- " + f.getShipName());
        }
    }

    @Override
    public void cancelledCargoSelection() {
        plugin.addText("Cargo transfer cancelled...");
    }

    @Override
    public void pickedCargo(CargoAPI cargo) {
        if (cargo.isEmpty()) {
            cancelledFleetMemberPicking();
            return;
        }

        if (!handleTransferCost(CargoHelper.calculateCargoUpkeep(cargo))) {
            return;
        }

        targetCargo.addAll(cargo);
        for (CargoStackAPI c : cargo.getStacksCopy()) {
            plugin.appendText("\n- " + c.getSize() + " x " + c.getDisplayName());
        }
    }

    @Override
    public void recreateTextPanel(TooltipMakerAPI panel, CargoAPI cargo, CargoStackAPI pickedUp,
            boolean pickedUpFromSource, CargoAPI combined) {
    }

    @Override
    protected StellnetDialogOption run(StellnetDialogOption option) {
        boolean isFrom = filterManager.getCourierDirection().equals(StellnetDialogOption.COURIER_DIRECTION_FROM);
        boolean isCargo = filterManager.getCourierTransfer().equals(StellnetDialogOption.COURIER_TRANSFER_CARGO);

        if (isFrom) {
            sourceCargo = StorageHelper.get().getCargo();
            targetCargo = Global.getSector().getPlayerFleet().getCargo();
            sourceFleet = sourceCargo.getMothballedShips();
            targetFleet = Global.getSector().getPlayerFleet().getFleetData();
            dialogTitle = "from Stellar Logistics Warehouse";
        } else {
            sourceCargo = Global.getSector().getPlayerFleet().getCargo();
            targetCargo = StorageHelper.get().getCargo();
            sourceFleet = Global.getSector().getPlayerFleet().getFleetData();
            targetFleet = targetCargo.getMothballedShips();
            dialogTitle = "to Stellar Logistics Warehouse";
        }

        if ((isCargo && sourceCargo.isEmpty()) || (!isCargo && sourceFleet.getMembersInPriorityOrder().isEmpty())) {
            String what = isCargo ? "is no available cargo" : "are no available ships";
            plugin.addText("There " + what + " to transfer " + dialogTitle + ".");
            return option;
        }

        if (isCargo) {
            dialogTitle = "Transfer cargo " + dialogTitle;
            plugin.getDialog().showCargoPickerDialog(dialogTitle, "Transfer", "Cancel", false, 0f, sourceCargo, this);
        } else {
            dialogTitle = "Transfer ships " + dialogTitle;
            plugin.getDialog().showFleetMemberPickerDialog(dialogTitle, "Transfer", "Cancel", 8, 12, 64f, true, true,
                    sourceFleet.getMembersInPriorityOrder(), this);
        }

        return option;
    }

    private boolean handleTransferCost(int cost) {
        String fee = Misc.getDGSCredits(cost);
        MutableValue credits = Global.getSector().getPlayerFleet().getCargo().getCredits();

        if (credits.get() < cost) {
            plugin.addText("You do not have enough credits to pay the courier fee of " + fee + ".");
            return false;
        }

        credits.subtract(cost);
        plugin.addText("After paying the fee of " + fee + " you "
                + dialogTitle.replaceAll("Transfer", "transfer the following") + ":");
        return true;
    }
}
