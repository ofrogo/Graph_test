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
}
