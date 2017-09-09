import java.security.InvalidParameterException;
import java.util.*;

public class Graph {

    private volatile List<Vertex> vertices;

    public Graph(int verticesNumber, int x, int y) {

        if (verticesNumber < 2 || verticesNumber > (x + 1) * (y + 1)) {
            throw new InvalidParameterException();
        }

        vertices = new ArrayList<>();
        for (int i = 0; i < verticesNumber; i++) {
            vertices.add(new Vertex(-1, -1));
        }

        rearrangeVertices(x, y);
        arrangeArcs();
    }

    private void rearrangeVertices(int x, int y) {

        Random random = new Random();

        for (Vertex vertex : vertices) {
            int currentX = random.nextInt(x);
            int currentY = random.nextInt(y);


            while (vertices.contains(new Vertex(currentX, currentY))) {

                if (currentX == x && currentY == y) {
                    currentX = 0;
                    currentY = 0;
                } else {
                    if (currentX == x) {
                        currentX = 0;
                        currentY++;
                    } else {
                        currentX++;
                    }
                }
            }

            vertex.setX(currentX);
            vertex.setY(currentY);
        }
    }

    private void arrangeArcs() {

        Random random = new Random();
        for (int i = 0; i < vertices.size(); i++) {

            int rnd = random.nextInt(vertices.size());
            if (rnd == i) {
                rnd = i == 0
                        ? 1
                        : i == vertices.size() - 1
                            ? vertices.size() - 2
                            : random.nextBoolean()
                                    ? rnd - 1
                                    : rnd + 1;
            }

            Vertex current = vertices.get(i);
            Vertex toBeLinked = vertices.get(rnd);
            if (!current.isLinkedTo(toBeLinked) && !toBeLinked.isLinkedTo(current)) {
                current.addArcTo(vertices.get(rnd));
            }
        }

        Collections.shuffle(vertices);

        for (int i = 0; i < vertices.size() - 1; i++) {

            Vertex next = vertices.get(i + 1);
            Vertex current = vertices.get(i);

            if (!current.isLinkedTo(next) && !next.isLinkedTo(current)) {
                current.addArcTo(next);
            }
        }

        Vertex last = vertices.get(vertices.size() - 1);
        Vertex first = vertices.get(0);
        if (!first.isLinkedTo(last) && !last.isLinkedTo(first)) {
            last.addArcTo(vertices.get(0));
        }
    }

    public List<Vertex> getVertices() {
        return vertices;
    }
}
