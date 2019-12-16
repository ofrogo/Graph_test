package Graph;

import Entity.Edge;
import Entity.EdgeAbstract;
import Entity.Node;
import Entity.OrEdge;
import Service.GraphService;

import java.util.*;

public class GraphDirectedWeighted extends GraphAbstract {
    Map<String, OrEdge> capacity;

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


    Set<OrEdge> getMyEdges() {
        Set<OrEdge> edges = new HashSet<>();
        for (Node n : nodeList.values()) {
            for (Map.Entry<String, Long> entry : n.getNodes().entrySet()) {
                OrEdge e = new OrEdge(n.getName(), entry.getKey(), entry.getValue());
                if (!edges.add(e)) {
                    System.out.println("Already have " + e);
                }
            }
        }
        return edges;
    }


    public int maxFlow(String source, String target) {
        int maxFlow = 0;
        int iterationResult = 0;
        if (used == null) used = new HashMap<>();
        do {
            used.clear();
            for (String key : getNodeList().keySet()) {
                used.put(key, false);
            }
            iterationResult = flow(source, target, Integer.MAX_VALUE / 2);
            maxFlow += iterationResult;
        } while (iterationResult > 0);
        return maxFlow;
    }

    private int flow(String u, String t, int cMin) {
        if (u.equals(t)) return cMin;
        used.put(u, true);
        for (OrEdge edge : nodeList.get(u).getOrEdges()) {
            String v = edge.getNodeId1().equals(u) ? edge.getNodeId2() : edge.getNodeId1();
            if (!used.get(v) && edge.getFlow() < edge.getValue()) {
                int minResult = flow(v, t, (int) Math.min(cMin, edge.getValue() - edge.getFlow()));
                if (minResult > 0) {
                    edge.setFlow(edge.getFlow() + minResult);
                    edge.getBack().setFlow(edge.getBack().getFlow() - minResult);
                    return minResult;
                }
            }
        }
        return 0;
    }


    @Override
    public void showInUI(Scanner scanner) throws Exception {
        switch (scanner.nextInt()) {
            case 1: {
                System.out.println("Enter name of node:");
                String name = scanner.next();
                Map<String, Long> nodesMap = new HashMap<>();
                if (getNumberNodes() != 0) {
                    System.out.println("How many adjacent nodes? (max:" + getNumberNodes() + ")");
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
                        "3)1b.1 Inverse graph.\n" +
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
                        System.out.println("Weight information will be lost! Do you want to inverse? Y/n");
                        if (scanner.next().toUpperCase().equals("Y")) {
                            System.out.println("Graph has been inversed.");
                            setInverseConForNodes();
                        } else {
                            break;
                        }
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
