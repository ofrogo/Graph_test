package Entity;

import java.util.Objects;

public class Edge extends EdgeAbstract {

    public Edge(String nodeId1, String nodeId2) {
        super(nodeId1, nodeId2);
    }

    public Edge(String nodeId1, String nodeId2, Long value) {
        super(nodeId1, nodeId2, value);
    }

    @Override
    public int hashCode() {
        return nodeId1.hashCode() + nodeId2.hashCode() + value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(this.hashCode(), edge.hashCode());
    }
}
