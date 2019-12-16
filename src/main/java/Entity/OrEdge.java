package Entity;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;

public class OrEdge extends EdgeAbstract {
    private Long flow = 0L;

    private OrEdge backEdge;

    public OrEdge(String nodeId1, String nodeId2) {
        super(nodeId1, nodeId2);
    }

    public OrEdge(String nodeId1, String nodeId2, Long value) {
        super(nodeId1, nodeId2, value);
    }

    public Long getFlow() {
        return flow;
    }

    public void setFlow(Long flow) {
        this.flow = flow;
    }

    public OrEdge getBack() {
        if (backEdge == null) backEdge = new OrEdge(nodeId2, nodeId1, value);
        return backEdge;
    }

    @Override
    public int hashCode() {
        return 73 * (73 + nodeId1.hashCode()) + nodeId2.hashCode() + value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrEdge edge = (OrEdge) o;
        return Objects.equals(this.hashCode(), edge.hashCode());
    }

    @Override
    public String toString() {
        return super.toString() + "flow: " + flow;
    }
}
