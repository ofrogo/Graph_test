package Graph;

import Entity.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class GraphAbstract {

    Boolean directed;
    Boolean weighted;
    Map<String, Node> nodeList;

    GraphAbstract() {
        nodeList = new HashMap<>();
    }

    GraphAbstract(Map<String, Node> nodeList) {
        this.nodeList = new HashMap<>();
        nodeList.forEach((s, node) -> this.nodeList.put(s, node));
    }

    public void addNode(String name, Map<String, Long> connections) throws Exception {
        if (nodeList.containsKey(name)) {
            throw new Exception("Node with this label already exist!");
        }
        nodeList.put(name, new Node(name, connections));
    }

    protected void addOneCon(String name_node1, String name_node2, Long weight) throws Exception {
        if (containNode(name_node1) && containNode(name_node2)) {
            nodeList.get(name_node1).addConnect(name_node2, weight);
        } else {
            throw new Exception("Node doesn't exist!");
        }
    }

    protected void addTwoCon(String name_node1, String name_node2, Long weight) throws Exception {
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

    protected void deleteOneCon(String name_node1, String name_node2) throws Exception {
        if (containNode(name_node1) && containNode(name_node2)) {
            nodeList.get(name_node1).deleteConnect(name_node2);
        } else {
            throw new Exception("Node doesn't exist!");
        }
    }

    protected void deleteTwoCon(String name_node1, String name_node2) throws Exception {
        if (containNode(name_node1) && containNode(name_node2)) {
            nodeList.get(name_node1).deleteConnect(name_node2);
            nodeList.get(name_node2).deleteConnect(name_node1);
        } else {
            throw new Exception("Node doesn't exist!");
        }
    }
    public void showInUI(Scanner scanner) throws Exception{}

    public int sizeOfGraph() {
        return nodeList.size();
    }

    @Override
    public String toString() {
        return "Graph{\n" +
                "nodeList=" + toStringNodeList() +
                "\n}";
    }


    private String toStringNodeList() {
        final String[] str = {""};
        nodeList.forEach((s, node) -> str[0] = str[0] + node.toString());
        return str[0];
    }

    protected boolean containNode(String label) {
        return nodeList.containsKey(label);
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
