package stellics.campaign.submarkets;

import com.fs.starfarer.api.impl.campaign.submarkets.StoragePlugin;

public class StellicsStorage extends StoragePlugin {

    @Override
    public boolean isFreeTransfer() {
        return true;
    }
}
