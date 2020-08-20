package stellics.filter;

public interface Filter<T> {

    public boolean match(T market);
}
