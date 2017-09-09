public interface IExplorable {

    void accept(IExplorer explorer);

    IExplorable next();

    IExplorer getExplorer();

    boolean hasNext(IExplorer explorer);
}
