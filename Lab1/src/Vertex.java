import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Vertex implements IExplorable {

    private int x;

    private int y;

    private int radius;

    private volatile List<Arc> arcs = new ArrayList<>();

    private IExplorer explorer;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;

        this.radius = new Random().nextInt(20) + 7;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addArcTo(Vertex vertex) {
        arcs.add(new Arc(this, vertex));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Vertex)) {
            return false;
        }

        Vertex other = (Vertex) obj;
        return other.getY() == y && other.getX() == x;
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    public int getRadius() {
        return radius;
    }

    public boolean isLinkedTo(Vertex other) {
        return arcs.contains(new Arc(this, other));
    }

    public IExplorer getExplorer() {
        return explorer;
    }

    @Override
    public synchronized void accept(IExplorer explorer) {
            this.explorer = explorer;
    }

    @Override
    public IExplorable next() {
        return arcs.get(new Random().nextInt(arcs.size()));
    }

    @Override
    public synchronized boolean hasNext(IExplorer explorer) {
        return arcs.stream().anyMatch(arc -> arc.getExplorer() == null || arc.getExplorer() == explorer);
    }
}
