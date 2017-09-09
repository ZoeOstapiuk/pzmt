import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphExplorer extends Thread implements IExplorer {

    private Applet applet;

    private Graph graph;

    private Color color;

    public GraphExplorer(Applet applet, Graph graph, Color color, String name) {
        this.applet = applet;
        this.graph = graph;
        this.color = color;
        this.setName(name);
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void run() {

        List<Vertex> clone = new ArrayList<>(graph.getVertices());
        Collections.shuffle(clone);
        IExplorable current = clone.get(0);

        explore(current);

        int itemsExplored = 0;

        while (true) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                ex.printStackTrace();

                throw new InvalidParameterException();
            }

            if (!current.hasNext(this)) {
                JOptionPane.showMessageDialog(applet,
                        getName() + " explorer has finished its exploration and explored "
                                + itemsExplored + " items!",
                        "Exploration finished",
                        JOptionPane.INFORMATION_MESSAGE);

                break;
            }

            itemsExplored++;
            IExplorable next = moveRandomly(current);

            if (next != null) {
                current = next;
                explore(current);
            }

            applet.repaint();
        }
    }

    private IExplorable moveRandomly(IExplorable current) {

        IExplorer other = current.getExplorer();
        if (other != this && other != null) {
            return null;
        }

        return current.next();
    }

    @Override
    public void explore(IExplorable explorable) {

        explorable.accept(this);
    }
}
