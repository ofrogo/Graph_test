import java.util.Map;

public class Node {
    private String name;
    private Map<Node, Integer> nodes;

    public Node(String name, Map<Node, Integer> nodes) {
        this.name = name;
        this.nodes = nodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Node, Integer> getNodes() {
        return nodes;
    }

    public void setNodes(Map<Node, Integer> nodes) {
        this.nodes = nodes;
    }
}
