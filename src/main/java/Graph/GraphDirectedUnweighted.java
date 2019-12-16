package Graph;

import Entity.Edge;
import Entity.EdgeAbstract;
import Entity.Node;
import Entity.OrEdge;
import Service.GraphService;

import java.util.*;

public class GraphDirectedUnweighted extends GraphAbstract {
    public GraphDirectedUnweighted() {
        super();
        directed = true;
        weighted = false;
    }

    private void addNode(String name, List<String> conLabels) throws Exception {
        Map<String, Long> connections = new HashMap<>();
        conLabels.forEach(s -> connections.put(s, 1L));
        addNode(name, connections);
    }

    @Override
    public void deleteNode(String name_model) throws Exception {
        super.deleteNode(name_model);
    }

    private void addCon(String first_label, String second_label) throws Exception {
        addOneCon(first_label, second_label, 1L);
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
                List<String> listLabels = new ArrayList<>();
                if (getNumberNodes() != 0) {
                    System.out.println("How many adjacent nodes? (max:" + getNumberNodes() + ")");
                    int k = scanner.nextInt();
                    for (int i = 0; i < k; i++) {
                        System.out.println("Enter label adjacent node:");
                        listLabels.add(scanner.next());
                    }
                }
                addNode(name, listLabels);
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
                addCon(first, second);
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
                        "3)1b.1 Inverse graph\n" +
                        "4)II.31 Find shortest path from node to all other nodes\n" +
                        "5)IV.a Find centre.");
                switch (scanner.nextInt()) {
                    case 1: {
                        System.out.println(getLoopLabels());
                        break;
                    }
                    case 2: {
                        System.out.println(getHangingNodes());
                        break;
                    }
                    case 3: {
                        System.out.println("Graph has been inversed.");
                        setInverseConForNodes();
                        break;
                    }
                    case 4: {
                        System.out.println("Enter name of node: ");
                        String id_node = scanner.next();
                        for (Map.Entry<String, List<String>> entry : bfs(id_node).entrySet()) {
                            System.out.print(entry.getKey() + " : { ");
                            for (String s : entry.getValue()) {
                                System.out.print(s + " ");
                            }
                            System.out.println("}");
                        }
                        break;
                    }
                    case 5: {
                        System.out.println(center());
                        break;
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
    Set<EdgeAbstract> getEdges() {
        Set<EdgeAbstract> edges = new HashSet<>();
        for (Node n : nodeList.values()) {
            for (Map.Entry<String, Long> entry : n.getNodes().entrySet()) {
                edges.add(new OrEdge(n.getName(), entry.getKey(), entry.getValue()));
            }
        }
        return edges;
    }

    @Override
    List<String> getLoopLabels() throws Exception {
        return super.getLoopLabels();
    }

    @Override
    public String toString() {
        return "Directed and Unweighted graph{\n" +
                "nodeList=\n" + toStringUnwNodeList() +
                "}";
    }

}
