public class Arc implements IExplorable {

    private Vertex first;

    private Vertex second;

    private IExplorer explorer;

    public Arc(Vertex first, Vertex second) {
        this.first = first;
        this.second = second;
    }

    public Vertex getFirst() {
        return first;
    }

    public Vertex getSecond() {
        return second;
    }

    public IExplorer getExplorer() {
        return explorer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Arc)) {
            return false;
        }

        Arc other = (Arc) obj;
        return first.equals(other.first) && second.equals(other.second);
    }

    @Override
    public synchronized void accept(IExplorer explorer) {
            this.explorer = explorer;
    }

    @Override
    public IExplorable next() {
        return second;
    }

    @Override
    public synchronized boolean hasNext(IExplorer explorer) {
        return second.getExplorer() == null || second.getExplorer() == explorer;
    }
}
