package stellics.campaign.intel;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.campaign.TextPanelAPI;

public class BatchStellnetIntel implements BaseStellnetIntel {

    private List<BaseStellnetIntel> intels;

    public BatchStellnetIntel() {
        intels = new ArrayList<BaseStellnetIntel>();
    }

    public void add(BaseStellnetIntel intel) {
        intels.add(intel);
    }

    @Override
    public void trigger() {
        for (BaseStellnetIntel i : intels) {
            i.trigger();
        }
    }

    @Override
    public void updateTextPanel(TextPanelAPI textPanel) {
        for (BaseStellnetIntel i : intels) {
            i.updateTextPanel(textPanel);
        }
    }
}
