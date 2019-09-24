package Graph;

import Service.GraphService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GraphDirectedWeighted extends GraphAbstract {
    public GraphDirectedWeighted() {
        super();
        directed = true;
        weighted = true;
    }

    public void addNode(String name, Map<String, Long> connections) throws Exception {
        super.addNode(name, connections);
    }

    protected void deleteNode(String name_model) throws Exception {
        super.deleteNode(name_model);
    }

    private void addCon(String name_node1, String name_node2, Long weight) throws Exception {
        addOneCon(name_node1, name_node2, weight);
    }

    private void deleteCon(String name_model1, String name_model2) throws Exception {
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
            case 6: {
                System.out.println("Tasks:\n" +
                        "1)1a.11 Show labels of loops\n" +
                        "2)1a.5 Show all hangings nodes.\n" +
                        "3)1b.1 Inverse graph.");
                switch (scanner.nextInt()) {
                    case 1: {
                        System.out.println(getLoopLabels());
                        break;
                    }
                    case 2: {
                        System.out.println(getHangingNodes());
                    }
                    case 3: {
                        System.out.println("Weight information will be lost! Do you want to inverse? Y/n");
                        if (scanner.next().toUpperCase().equals("Y")) {
                            System.out.println("Graph has been inversed.");
                            setInverseConForNodes();
                        } else {
                            break;
                        }
                    }
                    default:
                        break;
                }
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

    @Override
    List<String> getLoopLabels() throws Exception {
        return super.getLoopLabels();
    }

    @Override
    public String toString() {
        return "Directed and Weighted graph{\n" +
                "nodeList=\n" + toStringNodeList() +
                "}";
    }
}
