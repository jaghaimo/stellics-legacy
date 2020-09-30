package stellics.campaign.intel;

import com.fs.starfarer.api.ui.TooltipMakerAPI;

public interface IntelEntity {

    public String getEntity();

    public String getIcon();

    public String getIntelTitle();

    public String getIntelInfo();

    public String getStarSystemName(String prefix);

    public void createSmallDescription(StellnetIntel plugin, TooltipMakerAPI info);
}
