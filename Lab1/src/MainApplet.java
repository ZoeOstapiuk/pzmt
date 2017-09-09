import java.applet.Applet;
import java.awt.*;
import java.util.Random;

public class MainApplet extends Applet {

    private final int padding = 50;

    private final int width = 50;

    private final int height = 40;

    private Graph graph;

    @Override
    public void init() {

        int random = new Random().nextInt(15) + 15;
        graph = new Graph(50, width, height);

        Thread redExplorer = new GraphExplorer(this, graph, Color.red, "Red");
        Thread blueExplorer = new GraphExplorer(this, graph, Color.blue, "Blue");
        Thread greenExplorer = new GraphExplorer(this, graph, Color.green, "Green");

        redExplorer.start();
        blueExplorer.start();
        greenExplorer.start();
    }

    @Override
    public void paint(Graphics g) {

        setBackground(new Color(9, 8, 57));

        int xSize = (getWidth() - padding * 2) / width;
        int ySize = (getHeight() - padding * 2) / height;

        for (Vertex v : graph.getVertices()) {
            int xCoordinate = getCoordinate(xSize, v.getX());
            int yCoordinate = getCoordinate(ySize, v.getY());

            v.getArcs().stream().forEach(arc -> {

                Vertex vertex = arc.getSecond();
                Color color = arc.getExplorer() == null ? Color.white : arc.getExplorer().getColor();

                g.setColor(color);

                g.drawLine(xCoordinate, yCoordinate, getCoordinate(xSize, vertex.getX()),
                        getCoordinate(ySize, vertex.getY()));
            });
        }

        for (Vertex v : graph.getVertices()) {
            int xCoordinate = getCoordinate(xSize, v.getX());
            int yCoordinate = getCoordinate(ySize, v.getY());

            Color color = v.getExplorer() == null ? Color.yellow : v.getExplorer().getColor();

            g.setColor(color);
            g.fillArc(xCoordinate - v.getRadius(), yCoordinate - v.getRadius(),
                    v.getRadius() * 2, v.getRadius() * 2, 0, 360);

            g.drawArc(xCoordinate - v.getRadius(), yCoordinate - v.getRadius(),
                    v.getRadius() * 2, v.getRadius() * 2, 0, 360);
        }
    }

    private int getCoordinate(int size, int coordinate) {
        return padding + size * coordinate;
    }
}
