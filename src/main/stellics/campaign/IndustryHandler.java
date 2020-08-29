package stellics.campaign;

import java.util.List;

import stellics.Constants;
import stellics.filter.MarketFilter;
import stellics.filter.MarketHasIndustry;
import stellics.helper.IntelHelper;

public class IndustryHandler extends BasicHandler {

    public IndustryHandler(StellnetDialogPlugin p) {
        super(p);
    }

    @Override
    protected StellnetDialogOption run(StellnetDialogOption option) {
        List<MarketFilter> filters = plugin.getFilterManager().listMarketFilters();
        filters.add(new MarketHasIndustry(Constants.BRANCH, true));
        plugin.addIntel(IntelHelper.getIndustryIntel(filters, Constants.BRANCH));

        return option;
    }
}
