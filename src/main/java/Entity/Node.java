package Entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.Set;

public class Node {
    private String name;
    private Map<String, Long> nodes;

    public Node(String name, Map<String, Long> nodes) {
        this.name = name;
        this.nodes = nodes;
    }

    public int getNumberEdges() {
        return nodes.size();
    }

    public boolean isAdjacent(String id) {
        return nodes.containsKey(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Long> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Long> nodes) {
        this.nodes = nodes;
    }

    public void setNodes(Set<String> labels) {
        nodes.clear();
        labels.forEach(l -> nodes.put(l, 0L));
    }

    public void addConnect(String name_node, Long weight) {
        nodes.put(name_node, weight);
    }

    public void deleteConnect(String name_node) throws Exception {
        if (nodes.containsKey(name_node)) {
            nodes.remove(name_node);
        } else {
            throw new Exception(String.format("Connection %s with %s doesn't exist!", name, name_node));
        }
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        JSONArray jsonNodes = new JSONArray();
        for (String key : nodes.keySet()) {
            JSONObject jsonCon = new JSONObject();
            jsonCon.put("name", key);
            jsonCon.put("weight", nodes.get(key));
            jsonNodes.add(jsonCon);
        }
        json.put("nodes", jsonNodes);
        return json;
    }

    public boolean containLoop() {
        for (String s : nodes.keySet()) {
            if (name.equals(s))
                return true;
        }
        return false;
    }

    public boolean isHangingNode() {
        return nodes.size() == 1;
    }

    @Override
    public String toString() {
        return " Node{" + name +
                " : " + nodes +
                "}\n";
    }
}
