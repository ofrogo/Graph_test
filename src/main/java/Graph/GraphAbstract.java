package Graph;

import Entity.Edge;
import Entity.Node;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public abstract class GraphAbstract {

    Boolean directed;
    Boolean weighted;
    private Map<String, Node> nodeList;


    private Map<String, Boolean> used;

    GraphAbstract() {
        nodeList = new HashMap<>();
    }

    GraphAbstract(Node node) {
        nodeList = new HashMap<>();
        nodeList.put(node.getName(), node);
    }

    GraphAbstract(Map<String, Node> nodeList) {
        this.nodeList = new HashMap<>();
        nodeList.forEach((s, node) -> this.nodeList.put(s, node));
    }


    void addGraph(GraphAbstract graphAbstract) throws Exception {
        if (graphAbstract.directed != directed || graphAbstract.weighted != weighted) {
            throw new Exception("Error trying to connect graphs of different types");
        }
        nodeList.putAll(graphAbstract.nodeList);
    }

    Map<String, List<String>> bfs(String id_cur) {
        Map<String, Long> d = new HashMap<>();
        for (String s : nodeList.keySet()) {
            d.put(s, Long.MAX_VALUE);
        }
        d.put(id_cur, 0L);

        Queue<String> queue = new ArrayDeque<>();
        queue.add(id_cur);

        Map<String, List<String>> paths = new HashMap<>();
        paths.put(id_cur, new ArrayList<>());

        while (!queue.isEmpty()) {
            String v = queue.poll();
            for (Map.Entry<String, Long> u : nodeList.get(v).getNodes().entrySet()) {
                if (d.get(u.getKey()) > d.get(v) + u.getValue()) {
                    d.put(u.getKey(), d.get(v) + u.getValue());
                    List<String> buf = new ArrayList<>(paths.get(v));
                    buf.add(u.getKey());
                    paths.put(u.getKey(), buf);
                    queue.add(u.getKey());
                }
            }
        }


        return paths;
    }

    private void dfs(String id_cur) {
        if (used == null) {
            used = new HashMap<>();
            for (String s : nodeList.keySet()) {
                used.put(s, false);
            }
        }
        used.put(id_cur, true);
        for (String id_adj : nodeList.get(id_cur).getNodes().keySet()) {
            if (!used.get(id_adj)) {
                dfs(id_adj);
            }
        }
    }

    int getNumberOfComponents() {
        used = new HashMap<>();
        for (String s : nodeList.keySet()) {
            used.put(s, false);
        }
        int cnt = 0;
        for (String s : nodeList.keySet()) {
            if (!used.get(s)) {
                dfs(s);
                cnt++;
            }
        }
        return cnt;
    }

    List<String> getNodesIdFromVariousComponents() {
        used = new HashMap<>();
        for (String s : nodeList.keySet()) {
            used.put(s, false);
        }
        List<String> nodes = new ArrayList<>();
        for (String s : nodeList.keySet()) {
            if (!used.get(s)) {
                dfs(s);
                nodes.add(s);
            }
        }
        return nodes;
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
            for (Map.Entry<String, Long> entry : n.getNodes().entrySet()) {
                edges.add(new Edge(n.getName(), entry.getKey(), entry.getValue()));
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
