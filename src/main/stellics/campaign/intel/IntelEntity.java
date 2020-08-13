package stellics.campaign.intel;

import com.fs.starfarer.api.ui.TooltipMakerAPI;

public interface IntelEntity {

    public String getEntity();

    public String getIntelTitle();

    public String getIntelInfo();

    public void createSmallDescription(TooltipMakerAPI info);
}
