package Entity;

public abstract class EdgeAbstract {
    protected String nodeId1;
    protected String nodeId2;
    protected Long value = 1L;

    public EdgeAbstract(String nodeId1, String nodeId2) {
        this.nodeId1 = nodeId1;
        this.nodeId2 = nodeId2;
    }

    public EdgeAbstract(String nodeId1, String nodeId2, Long value) {
        this.nodeId1 = nodeId1;
        this.nodeId2 = nodeId2;
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
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

    public void reverse() {
        String buf = nodeId1;
        nodeId2 = nodeId1;
        nodeId1 = buf;
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
