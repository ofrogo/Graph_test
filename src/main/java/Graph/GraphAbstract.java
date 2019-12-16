package Graph;

import Entity.Edge;
import Entity.EdgeAbstract;
import Entity.Node;
import Entity.OrEdge;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public abstract class GraphAbstract {

    Boolean directed;
    Boolean weighted;
    protected Map<String, Node> nodeList;


    Map<String, Boolean> used;

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

    Set<Edge> dijkstra(String s, String e) throws Exception {
        if (getNumberOfComponents() > 1) {
            throw new Exception("Unrelated graph!");
        }
        if (s.equals(e)) {
            return new HashSet<Edge>() {
                {
                    add(new Edge(s, e, 0L));
                }
            };
        }
        if (used == null) used = new HashMap<>();
        else used.clear();
        for (String key : getNodeList().keySet()) {
            used.put(key, false);
        }
        used.put(s, true);
        Map<String, Long> dist = new HashMap<String, Long>() {{
            for (String key : getNodeList().keySet()) {
                put(key, Long.MAX_VALUE);
            }
        }};
        Map<String, Edge> path = new HashMap<>();

        Node node = getNodeList().get(s);
        dist.put(node.getName(), 0L);
        Node finalNode = node;
        node.getNodes().forEach((s1, aLong) -> {
            dist.put(s1, aLong);
            path.put(s1, new Edge(s1, finalNode.getName(), aLong));
        });
        while (true) {
            String curNodeId = node.getName();
            String minNodeId = dist.entrySet().stream().filter(stringLongEntry -> !used.get(stringLongEntry.getKey()) && !stringLongEntry.getKey().equals(curNodeId)).min(Comparator.comparingLong(Map.Entry::getValue)).get().getKey();
            if (minNodeId.isEmpty() || minNodeId.equals(e)) {
                return new HashSet<Edge>() {{
                    String tmp = e;
                    while (!tmp.equals(s)) {
                        add(path.get(tmp));
                        tmp = path.get(tmp).getNodeId2();
                    }
                }};
            }

            node = getNodeList().get(minNodeId);
            used.put(node.getName(), true);
            node.getNodes().forEach((s1, aLong) -> {
                if (!used.get(s1) && dist.get(minNodeId) + aLong < dist.get(s1)) {
                    dist.put(s1, dist.get(minNodeId) + aLong);
                    path.put(s1, new Edge(s1, minNodeId, aLong)); // предком вершины s1 является minNodeId
                }
            });

        }
    }

//    Map<String, OrEdge> floyd(String s) {
//        Set<OrEdge> edgeSet = new HashSet<OrEdge>() {
//            {
//                for (String s1 : nodeList.keySet()) {
//                    Set<String> nodeList1 = new HashSet<>(nodeList.keySet());
//                    nodeList1.remove(s1);
//                    for (String s2 : nodeList1) {
//                        add(new OrEdge(s1, s2));
//                    }
//                }
//            }
//        };
//        Map<OrEdge, Long> dict = new HashMap<OrEdge, Long>() {{
//            for (String u : nodeList.keySet()) {
//                Set<String> nodeList1 = new HashSet<>(nodeList.keySet());
//                nodeList1.remove(u);
//                for (String v : nodeList1) {
//                    Long l = nodeList.get(u).isAdjacent(v) ? nodeList.get(u).getNodes().get(v) : Long.MAX_VALUE;
//                    put(edgeSet.stream().filter(orEdge -> orEdge.isConnectNode(u) && orEdge.isConnectNode(v)).findFirst().get(), l);
//                }
//            }
//        }};
//        Map<String, OrEdge> path = new HashMap<>();
//        for (String i : nodeList.keySet()) {
//            for (String u : new HashSet<String>(nodeList.keySet()) {{
//                remove(i);
//            }}) {
//                for (String v : new HashSet<String>(nodeList.keySet()) {{
//                    remove(i);
//                    remove(u);
//                }}) {
//                    if (dict.get(new OrEdge(u, i)) + dict.get(new OrEdge(i, v)) < dict.get(new OrEdge(u, v))) {
//                        dict.put(new OrEdge(u, v), dict.get(new OrEdge(u, i)) + dict.get(new OrEdge(i, v)));
//                        path.put(u, new OrEdge(u, i, dict.get(new OrEdge(u, i))));
//                    }
//                }
//            }
//        }
//        return path;
//    }

    Map<String, List<String>> floyd(String s) {
        Map<String, Integer> mapDictionary = new HashMap<String, Integer>() {{
            int i = 0;
            for (String n : nodeList.keySet()) {
                put(n, i);
                i++;
            }
        }};
        String[] dictionary = new String[nodeList.size()];
        {
            int i = 0;
            for (String n : nodeList.keySet()) {
                dictionary[i] = n;
                i++;
            }
        }
        Long[][] dist = new Long[nodeList.size()][nodeList.size()];
        Integer[][] next = new Integer[nodeList.size()][nodeList.size()];
        for (int i = 0; i < nodeList.size(); i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE / 2);
            Arrays.fill(next[i], 0);
        }
        for (Node node : nodeList.values()) {
            for (Map.Entry<String, Long> stringLongEntry : node.getNodes().entrySet()) {
                dist[mapDictionary.get(node.getName())][mapDictionary.get(stringLongEntry.getKey())] = stringLongEntry.getValue();
                next[mapDictionary.get(node.getName())][mapDictionary.get(stringLongEntry.getKey())] = mapDictionary.get(stringLongEntry.getKey());
            }

        }

        for (Integer i : mapDictionary.values()) {
            for (Integer u : new ArrayList<Integer>(mapDictionary.values()) {{
                remove(i);
            }}) {
                for (Integer v : new ArrayList<Integer>(mapDictionary.values()) {{
                    remove(i);
                    remove(u);
                }}) {
                    if (dist[u][i] + dist[i][v] < dist[u][v]) {
                        dist[u][v] = dist[u][i] + dist[i][v];
                        next[u][v] = next[u][i];
                    }
                }
            }
        }

        Map<String, List<String>> result = new HashMap<>();
        for (String v : new HashSet<String>(nodeList.keySet()) {{
            remove(s);
        }}) {
            if (dist[mapDictionary.get(s)][mapDictionary.get(v)] == Long.MAX_VALUE / 2) {
                result.put(v, null);
                continue;
            }
            List<String> list = new ArrayList<>();
            String c = s;
            while (!c.equals(v)) {
                list.add(c);
                c = dictionary[next[mapDictionary.get(c)][mapDictionary.get(v)]];
            }
            result.put(v, list);
        }
        return result;
    }

    List<String> ford() {
        Map<String, Integer> mapDictionary = new HashMap<String, Integer>() {{
            int i = 0;
            for (String n : nodeList.keySet()) {
                put(n, i);
                i++;
            }
        }};
        String[] dictionary = new String[nodeList.size()];
        {
            int i = 0;
            for (String n : nodeList.keySet()) {
                dictionary[i] = n;
                i++;
            }
        }
        Long[] d = new Long[nodeList.size()];
        Arrays.fill(d, Long.MAX_VALUE / 2);
        d[0] = 0L;
        int[] p = new int[nodeList.size()];
        Arrays.fill(p, -1);
        int x = 0;
        for (int i = 0; i < nodeList.size(); i++) {
            x = -1;
            for (EdgeAbstract e : getEdges()) {
                if (d[mapDictionary.get(e.getNodeId1())] > d[mapDictionary.get(e.getNodeId2())] + e.getValue()) {
                    d[mapDictionary.get(e.getNodeId1())] = Math.max(d[mapDictionary.get(e.getNodeId2())] + e.getValue(), -Long.MAX_VALUE / 2);
                    p[mapDictionary.get(e.getNodeId1())] = mapDictionary.get(e.getNodeId2());
                    x = mapDictionary.get(e.getNodeId1());
                }
            }
        }
        List<String> path = new ArrayList<>();
        if (x == -1) {
            return null;
        } else {
            int y = x;
            for (int i = 0; i < nodeList.size(); i++) {
                y = p[y];
            }

            for (int i = y; ; i = p[i]) {
                if (i == y && path.size() > 1) {
                    break;
                }
                path.add(dictionary[i]);
            }
            Collections.reverse(path);
        }

        return path;
    }


    Long eccentricity(String s) {
        long maxValue = Long.MIN_VALUE;
        for (String n : nodeList.keySet()) {
            Set<Edge> path = null;
            try {
                path = dijkstra(s, n);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            AtomicReference<Long> l = new AtomicReference<>(0L);
            path.forEach(edge -> l.updateAndGet(v -> v + edge.getValue()));
            maxValue = Long.max(maxValue, l.get());
        }
        return maxValue;
    }

    Long radius() {
        Set<Long> eccentricities = new HashSet<Long>() {{
            for (String n : nodeList.keySet()) {
                add(eccentricity(n));
            }
        }};
        return eccentricities.stream().min(Long::compareTo).get();
    }

    Set<String> center() {
        try {
            Long radius = radius();
            return new HashSet<String>() {{
                nodeList.keySet().forEach(s -> {
                    if (Objects.equals(eccentricity(s), radius)) add(s);
                });
            }};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    Set<EdgeAbstract> getEdges() {
        Set<EdgeAbstract> edges = new HashSet<>();
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
