package Graph;

import Entity.Edge;
import Entity.Node;
import Service.GraphService;

import java.util.*;

public class GraphUndirectedWeighted extends GraphAbstract {
    public GraphUndirectedWeighted() {
        super();
        directed = false;
        weighted = true;
    }

    GraphUndirectedWeighted(Node node) {
        super(node);
        directed = false;
        weighted = true;
    }

    GraphUndirectedWeighted(Set<Edge> edgeSet) {
        super();
        directed = false;
        weighted = true;
        for (Edge edge : edgeSet) {
            if (getNodeList().containsKey(edge.getNodeId1())) {
                getNodeList().get(edge.getNodeId1()).getNodes().put(edge.getNodeId2(), edge.getValue());
            } else {
                getNodeList().put(edge.getNodeId1(), new Node(edge.getNodeId1(), new HashMap<String, Long>() {{
                    put(edge.getNodeId2(), edge.getValue());
                }}));
            }
            if (getNodeList().containsKey(edge.getNodeId2())) {
                getNodeList().get(edge.getNodeId2()).getNodes().put(edge.getNodeId1(), edge.getValue());
            } else {
                getNodeList().put(edge.getNodeId2(), new Node(edge.getNodeId2(), new HashMap<String, Long>() {{
                    put(edge.getNodeId1(), edge.getValue());
                }}));
            }
        }
    }

    @Override
    public void addNode(String name, Map<String, Long> connections) throws Exception {
        super.addNode(name, connections);
        connections.forEach((s, l) -> {
            try {
                addOneCon(s, name, l);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        });
    }

    @Override
    public void deleteNode(String name_model) throws Exception {
        super.deleteNode(name_model);
    }

    private void addCon(String name_node1, String name_node2, Long weight) throws Exception {
        if (name_node1.equals(name_node2))
            throw new Exception("You cannot create loops in an undirected graph");
        addTwoCon(name_node1, name_node2, weight);
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
                        "1)1a.5 Show all hangings nodes.\n" +
                        "2)1b.1 Inverse graph.\n" +
                        "3)II.11 Find the cyclomatic number of a graph.\n" +
                        "4)II.31 Find shortest path from node to all other nodes\n" +
                        "5)III Boruvka\n" +
                        "6)IV.a Find centre.");
                switch (scanner.nextInt()) {
                    case 1: {
                        System.out.println(getHangingNodes());
                        break;
                    }
                    case 2: {
                        System.out.println("Weight information will be lost! Do you want to inverse? Y/n");
                        if (scanner.next().toUpperCase().equals("Y")) {
                            System.out.println("Graph has been inversed.");
                            setInverseConForNodes();
                        } else {
                            break;
                        }
                    }
                    case 3: {
                        System.out.println("Cyclomatic number: " + (getNumberEdges() - getNumberNodes() + getNumberOfComponents()));
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
                        System.out.println(boruvka().toString());
                        break;
                    }
                    case 6: {
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

    GraphUndirectedWeighted boruvka() throws Exception {
        if (getNumberOfComponents() != 1) {
            throw new Exception("Not connected graph");
        }

        LinkedList<Set<String>> components = new LinkedList<>();
        getNodeList().keySet().forEach(s -> components.add(new HashSet<String>() {{
            add(s);
        }}));
        Set<Edge> edges = new HashSet<>();

        while (components.size() > 1) {
            Set<String> comp = components.pop();
            for (String nodeId : comp) {
                Edge minEdge = getNodeList().get(nodeId).getMinEdge(edges);
                if (minEdge != null) {
                    edges.add(minEdge);
                    String conNodeId = minEdge.getNodeId1().equals(nodeId) ? minEdge.getNodeId2() : minEdge.getNodeId1();
                    if (!comp.contains(conNodeId)) {
                        comp.addAll(components.stream().filter(strings -> strings.contains(conNodeId)).findFirst().get());
                    } else {
                        components.addLast(comp);
                    }
                } else {
                    components.addLast(comp);
                }
            }

        }

        return new GraphUndirectedWeighted(edges);
    }



    @Override
    List<String> getLoopLabels() throws Exception {
        throw new Exception("Undirected graph doesn't assume Loops");
    }

    @Override
    public String toString() {
        return "Undirected and Weighted graph{\n" +
                "nodeList=\n" + toStringNodeList() +
                "}";
    }
}
