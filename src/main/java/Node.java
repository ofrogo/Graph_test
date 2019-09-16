import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

public class Node {
    private String name;
    private Map<String, Long> nodes;

    Node(String name, Map<String, Long> nodes) {
        this.name = name;
        this.nodes = nodes;
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
}
