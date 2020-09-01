package stellics.campaign;

abstract public class BasicHandler {

    protected StellnetDialogPlugin plugin;

    public BasicHandler(StellnetDialogPlugin p) {
        plugin = p;
    }

    public StellnetDialogOption handle(StellnetDialogOption option) {
        if (plugin.getLastOption().equals(StellnetDialogOption.INIT)) {
            plugin.addTitle(option.getName());

            return init(option);
        }

        return run(option);
    }

    protected StellnetDialogOption init(StellnetDialogOption option) {
        return plugin.getFilterHandler().handle(option);
    }

    protected StellnetDialogOption run(StellnetDialogOption option) {
        return option;
    }
}
