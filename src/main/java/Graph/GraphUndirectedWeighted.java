package Graph;

import Service.GraphService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GraphUndirectedWeighted extends GraphAbstract {
    public GraphUndirectedWeighted() {
        super();
        directed = false;
        weighted = true;
    }

    @Override
    public void addNode(String name, Map<String, Long> connections) throws Exception {
        super.addNode(name, connections);
        connections.forEach((s, l) -> {
            try {
                addOneCon(s, name, l);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void deleteNode(String name_model) throws Exception {
        super.deleteNode(name_model);
    }

    public void addCon(String name_node1, String name_node2, Long weight) throws Exception {
        addOneCon(name_node1, name_node2, weight);
    }

    public void deleteCon(String name_model1, String name_model2) throws Exception {
        deleteOneCon(name_model1, name_model2);
    }

    @Override
    public void showInUI(Scanner scanner) throws Exception {
        switch (scanner.nextInt()) {
            case 1: {
                System.out.println("Enter name of node:");
                String name = scanner.next();
                Map<String, Long> nodesMap = new HashMap<>();
                if (sizeOfGraph() != 0) {
                    System.out.println("How many adjacent nodes? (max:" + sizeOfGraph() + ")");
                    int k = scanner.nextInt();
                    for (int i = 0; i < k; i++) {
                        System.out.println("Enter label and distance adjacent node:");
                        String label = scanner.next();
                        Long weight = scanner.nextLong();
                        nodesMap.put(label, weight);
                    }
                }
                addNode(name, nodesMap);
                break;
            }
            case 2: {
                System.out.println("Enter name of node:");
                String name = scanner.next();
                deleteNode(name);
                break;
            }
            case 3: {
                System.out.println("Enter name of first node:");
                String first = scanner.next();
                while (!containNode(first)) {
                    System.out.println("Don't know this label. Enter name of first nose:");
                    first = scanner.next();
                    if (first.equals("quit")) {
                        return;
                    }
                }
                System.out.println("Enter name of second node:");
                String second = scanner.next();
                while (!containNode(second)) {
                    System.out.println("Don't know this label. Enter name of second nose:");
                    second = scanner.next();
                    if (second.equals("quit")) {
                        return;
                    }
                }
                System.out.println("Enter weight:");
                Long weight = scanner.nextLong();
                addCon(first, second, weight);
                break;
            }
            case 4: {
                System.out.println("Enter name of first node:");
                String first = scanner.next();
                while (!containNode(first)) {
                    System.out.println("Don't know this label. Enter name of first nose:");
                    first = scanner.next();
                    if (first.equals("quit")) {
                        return;
                    }
                }
                System.out.println("Enter name of second node:");
                String second = scanner.next();
                while (!containNode(second)) {
                    System.out.println("Don't know this label. Enter name of second nose:");
                    second = scanner.next();
                    if (second.equals("quit")) {
                        return;
                    }
                }
                deleteCon(first, second);
                break;
            }
            case 5: {
                System.out.println("Enter file name:");
                GraphService.saveGraphToFile(scanner.next(), this);
                break;
            }
            default: {
                System.out.println("Do you want to exit? Y/n");
                if (scanner.next().toUpperCase().equals("Y")) {
                    System.exit(0);
                } else {
                    break;
                }
            }
        }
    }
}
