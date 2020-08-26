package stellics.campaign.intel.entity;

import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class Branch extends Industry {

    public enum Action {
        OPEN("Branch has been opened", "opened"), CLOSE("Branch has been closed", "closed"),
        DISRUPT("Branch has been temporarily closed", "temporarily closed"),
        RESUME("Branch has been reopened", "reopened");

        final public String message;
        final public String highlight;

        private Action(String m, String h) {
            message = m;
            highlight = h;
        }
    }

    private Action action;

    public Branch(MarketAPI m, Action a) {
        super(a.highlight, m);
        action = a;
    }

    @Override
    public String getIntelTitle() {
        return "Stellar Logisticts Branch";
    }

    @Override
    public String getIntelInfo() {
        return action.message;
    }
}
