package stellics.campaign.intel;

import com.fs.starfarer.api.campaign.TextPanelAPI;

public interface BaseStellnetIntel {

    public void trigger();

    public void updateTextPanel(TextPanelAPI textPanel);
}
