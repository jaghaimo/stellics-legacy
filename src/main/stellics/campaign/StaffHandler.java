package stellics.campaign;

import java.util.List;

import stellics.filter.FilterManager;
import stellics.filter.MarketFilter;
import stellics.filter.MarketHasAdministrator;
import stellics.filter.MarketHasOfficer;
import stellics.helper.IntelHelper;

public class StaffHandler extends BasicHandler {

    public StaffHandler(StellnetDialogPlugin p) {
        super(p);
    }

    @Override
    protected StellnetDialogOption run(StellnetDialogOption option) {
        FilterManager filterManager = plugin.getFilterManager();
        List<MarketFilter> filters = filterManager.listMarketFilters();

        if (filterManager.getStaffType().equals(StellnetDialogOption.STAFF_ADMIN)) {
            filters.add(new MarketHasAdministrator());
            plugin.addIntel(IntelHelper.getAdministratorIntel(filters));
        } else {
            String personality = filterManager.getStaffOfficer().name().substring(8).toLowerCase();
            filters.add(new MarketHasOfficer(personality));
            plugin.addIntel(IntelHelper.getOfficerIntel(filters, personality));
        }

        return option;
    }
}
