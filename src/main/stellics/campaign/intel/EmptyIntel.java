package stellics.campaign.intel;

import com.fs.starfarer.api.campaign.TextPanelAPI;

public class EmptyIntel implements BaseStellnetIntel {

    private String intelInfo;

    public EmptyIntel(String i) {
        intelInfo = i;
    }

    @Override
    public void updateTextPanel(TextPanelAPI textPanel) {
        textPanel.addPara(intelInfo);
    }

    @Override
    public void trigger() {
    }
}
