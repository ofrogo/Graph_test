package Graph;

import Entity.Edge;
import Entity.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public abstract class GraphAbstract {

    Boolean directed;
    Boolean weighted;
    private Map<String, Node> nodeList;

    GraphAbstract() {
        nodeList = new HashMap<>();
    }

    GraphAbstract(Map<String, Node> nodeList) {
        this.nodeList = new HashMap<>();
        nodeList.forEach((s, node) -> this.nodeList.put(s, node));
    }

    public boolean isEmpty() {
        return nodeList.isEmpty();
    }

    void setInverseConForNodes() {
        for (Node n : nodeList.values()) {
            Set<String> labels = new HashSet<>(nodeList.keySet());
            Set<String> oldCon = n.getNodes().keySet();
            labels.removeAll(oldCon);
            n.setNodes(labels);
        }
    }

    public void addNode(String name, Map<String, Long> connections) throws Exception {
        if (nodeList.containsKey(name)) {
            throw new Exception("Node with this label already exist!");
        }
        nodeList.put(name, new Node(name, connections));
    }

    public void addNode(Node node) throws Exception {
        if (nodeList.containsKey(node.getName())) {
            throw new Exception("Node with this label already exist!");
        }
        nodeList.put(node.getName(), node);
    }

    void addOneCon(String name_node1, String name_node2, Long weight) throws Exception {
        if (containNode(name_node1) && containNode(name_node2)) {
            nodeList.get(name_node1).addConnect(name_node2, weight);
        } else {
            throw new Exception("Node doesn't exist!");
        }
    }

    void addTwoCon(String name_node1, String name_node2, Long weight) throws Exception {
        if (containNode(name_node1) && containNode(name_node2)) {
            nodeList.get(name_node1).addConnect(name_node2, weight);
            nodeList.get(name_node2).addConnect(name_node1, weight);
        } else {
            throw new Exception("Node doesn't exist!");
        }
    }

    protected void deleteNode(String name_model) throws Exception {
        if (nodeList.containsKey(name_model)) {
            nodeList.remove(name_model);
            for (Node node : nodeList.values()) {
                node.getNodes().remove(name_model);
            }
        } else {
            throw new Exception(String.format("Node with label: %s doesn't exist!", name_model));
        }
    }

    void deleteOneCon(String name_node1, String name_node2) throws Exception {
        if (containNode(name_node1) && containNode(name_node2)) {
            nodeList.get(name_node1).deleteConnect(name_node2);
        } else {
            throw new Exception("Node doesn't exist!");
        }
    }

    void deleteTwoCon(String name_node1, String name_node2) throws Exception {
        if (containNode(name_node1) && containNode(name_node2)) {
            nodeList.get(name_node1).deleteConnect(name_node2);
            nodeList.get(name_node2).deleteConnect(name_node1);
        } else {
            throw new Exception("Node doesn't exist!");
        }
    }

    public abstract void showInUI(Scanner scanner) throws Exception;

    Set<Edge> getEdges() {
        Set<Edge> edges = new HashSet<>();
        for (Node n : nodeList.values()) {
            for (String s2 : n.getNodes().keySet()) {
                edges.add(new Edge(n.getName(), s2));
            }
        }
        return edges;
    }

    int getNumberNodes() {
        return nodeList.size();
    }

    int getNumberEdges() {
        return getEdges().size();
    }

    String toStringNodeList() {
        final String[] str = {""};
        nodeList.forEach((s, node) -> str[0] = str[0] + node.toString());
        return str[0];
    }

    String toStringUnwNodeList() {
        final String[] str = {""};
        nodeList.forEach((s, n) -> str[0] = str[0] + s + "::" + n.getNodes().keySet() + "; ");
        return str[0];
    }

    boolean containNode(String label) {
        return nodeList.containsKey(label);
    }

    List<String> getLoopLabels() throws Exception {
        List<String> list = new ArrayList<>();
        nodeList.forEach((s, n) -> {
            if (n.containLoop())
                list.add(s);
        });
        return list;
    }

    List<Node> getHangingNodes() {
        List<Node> list = new ArrayList<>();
        for (Node n : nodeList.values()) {
            if (n.isHangingNode())
                list.add(n);
        }
        return list;
    }

    public Boolean getDirected() {
        return directed;
    }

    public Boolean getWeighted() {
        return weighted;
    }

    public Map<String, Node> getNodeList() {
        return nodeList;
    }
}
