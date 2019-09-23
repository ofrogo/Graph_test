import Graph.GraphAbstract;
import Graph.GraphDirectedUnweighted;
import Graph.GraphDirectedWeighted;
import Graph.GraphUndirectedUnweighted;
import Graph.GraphUndirectedWeighted;
import Service.GraphService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Start GRAPH application...");
        GraphAbstract graph = null;
        while (true) {
            try {
                if (graph == null) {
                    System.out.println("Graph.Graph absent.");
                    System.out.println("Choose option:\n" +
                            "1)Create empty graph.\n" +
                            "2)Load graph from file.");
                    switch (scanner.nextInt()) {
                        case 1: {
                            System.out.println("Choose type of graph:\n" +
                                    "1)Directed and Unweighted\n" +
                                    "2)Directed and Weighted\n" +
                                    "3)Undirected and Unweighted\n" +
                                    "4)Undirected and Weighted\n");
                            switch (scanner.nextInt()) {
                                case 1:
                                    graph = new GraphDirectedUnweighted();
                                    break;
                                case 2:
                                    graph = new GraphDirectedWeighted();
                                    break;
                                case 3:
                                    graph = new GraphUndirectedUnweighted();
                                    break;
                                case 4:
                                    graph = new GraphUndirectedWeighted();
                                    break;
                            }
                            break;
                        }
                        case 2: {
                            System.out.println("Enter file name:");
                            graph = GraphService.getGraphFromFile(scanner.next());
                            break;
                        }
                        default: {
                            System.out.println("error");
                            return;
                        }
                    }
                } else {
                    System.out.println(graph.toString());
                    System.out.println("Choose option:\n" +
                            "1)Add node.\n" +
                            "2)Delete node.\n" +
                            "3)Add edge.\n" +
                            "4)Delete edge.\n" +
                            "5)Save to file.\n" +
                            "0)Exit.");
                    graph.showInUI(scanner);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        }

    }

