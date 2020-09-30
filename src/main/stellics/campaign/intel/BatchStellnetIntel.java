package stellics.campaign.intel;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.campaign.TextPanelAPI;

public class BatchStellnetIntel implements TextPanelAwareIntel {

    private List<TextPanelAwareIntel> intels;

    public BatchStellnetIntel() {
        intels = new ArrayList<TextPanelAwareIntel>();
    }

    public void add(TextPanelAwareIntel intel) {
        intels.add(intel);
    }

    @Override
    public void updateTextPanel(TextPanelAPI textPanel) {
        for (TextPanelAwareIntel i : intels) {
            i.updateTextPanel(textPanel);
        }
    }
}
