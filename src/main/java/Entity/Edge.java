package Entity;

import java.util.Objects;

public class Edge {
    private String nodeId1;
    private String nodeId2;

    public Edge(String nodeId1, String nodeId2) {
        this.nodeId1 = nodeId1;
        this.nodeId2 = nodeId2;
    }

    @Override
    public int hashCode() {
        return nodeId1.hashCode() + nodeId2.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(this.hashCode(), edge.hashCode());
    }
}
