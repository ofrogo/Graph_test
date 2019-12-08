package Entity;

import java.util.Objects;

public class Edge {
    private String nodeId1;
    private String nodeId2;
    private Long value = 1L;

    public Edge(String nodeId1, String nodeId2) {
        this.nodeId1 = nodeId1;
        this.nodeId2 = nodeId2;
    }

    public Edge(String nodeId1, String nodeId2, Long value) {
        this.nodeId1 = nodeId1;
        this.nodeId2 = nodeId2;
        this.value = value;
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

    public Long getValue() {
        return value;
    }

    public String getNodeId1() {
        return nodeId1;
    }

    public String getNodeId2() {
        return nodeId2;
    }

    public boolean isConnectNode(String nodeId) {
        return nodeId.equals(nodeId1) || nodeId.equals(nodeId2);
    }


    @Override
    public String toString() {
        return "Edge{" +
                "nodeId1='" + nodeId1 + '\'' +
                ", nodeId2='" + nodeId2 + '\'' +
                ", value=" + value +
                '}';
    }
}
