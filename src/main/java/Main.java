import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Start graph application...");
        String temp;
        Graph graph = null;
        while (true) {
            if (graph == null) {
                System.out.println("Graph absent.");
                System.out.println("Choose option:\n" +
                        "1)Create empty graph.\n" +
                        "2)Load graph from file.");
                switch (scanner.nextInt()) {
                    case 1: {
                        graph = new Graph();
                        break;
                    }
                    case 2: {
                        graph = new Graph();
                        System.out.println("Enter file name:");
                        graph.getGraphFromFile(scanner.next());
                        break;
                    }
                    default: {
                        System.out.println("error");
                    }
                }
            } else {
                System.out.println(graph.toString());
                System.out.println("Choose option:\n" +
                        "1)Add node.\n" +
                        "2)Delete node.\n" +
                        "3)Add edge.\n" +
                        "4)Delete edge.");
                switch (scanner.nextInt()) {
                    case 1: {
                        System.out.println("Enter name of node:");
                        String name = scanner.next();
                        System.out.println("How many adjacent nodes?");
                        Map<String, Long> nodesMap = new HashMap<>();
                        for (int i = 0; i < scanner.nextInt(); i++) {
                            System.out.println("Enter label and distance adjacent node:");
                            nodesMap.put(scanner.next(), scanner.nextLong());
                        }
                        graph.addNode(name, nodesMap);
                        break;
                    }
                    case 2: {
                        System.out.println("Enter name of node:");
                        String name = scanner.next();
                        graph.deleteNode(name);
                        break;
                    }
                    case 3: {
                        System.out.println("Enter name of first node:");
                        String first = scanner.next();
                        System.out.println("Enter name of second node:");
                        String second = scanner.next();
                        System.out.println("Enter weight:");
                        Long weight = scanner.nextLong();
                        System.out.println("Double connect: Y/n");
                        if (scanner.next().toUpperCase().equals("Y")) {
                            graph.addTwoCon(first, second, weight);
                        } else {
                            graph.addOneCon(first, second, weight);
                        }
                        break;
                    }
                    case 4: {
                        System.out.println("Enter name of first node:");
                        String first = scanner.next();
                        System.out.println("Enter name of second node:");
                        String second = scanner.next();
                        System.out.println("Double connect: Y/n");
                        if (scanner.next().toUpperCase().equals("Y")) {
                            graph.deleteTwoCon(first, second);
                        } else {
                            graph.deleteOneCon(first, second);
                        }
                        break;
                    }
                    default: {
                        System.out.println("Do you want to exit? Y/n");
                        if (scanner.next().toUpperCase().equals("Y")) {
                            System.exit(0);
                        } else {
                            System.exit(0);
                        }
                    }
                }
            }
        }

    }
}
