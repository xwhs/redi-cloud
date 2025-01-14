package net.suqatri.redicloud.node.node;

import lombok.Getter;
import lombok.Setter;
import net.suqatri.redicloud.api.impl.node.CloudNode;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ClusterConnectionInformation implements Serializable {

    private UUID uniqueId;

    public void applyToNode(CloudNode cloudNode) {
        cloudNode.setUniqueId(this.uniqueId);
    }

}
