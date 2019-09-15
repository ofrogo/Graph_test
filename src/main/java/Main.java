import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();
        graph.getGraphFromFile("/graph.json");
    }
}
