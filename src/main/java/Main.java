import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph();
        graph.getGraphFromFile("graph.json");
        graph.saveGraphToFile("test.json");
        System.out.println(graph.toString());
    }
}
