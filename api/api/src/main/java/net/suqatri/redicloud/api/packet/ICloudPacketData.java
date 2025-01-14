package net.suqatri.redicloud.api.packet;

import net.suqatri.redicloud.api.network.INetworkComponentInfo;
import net.suqatri.redicloud.commons.function.future.FutureAction;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

public interface ICloudPacketData extends Serializable {

    Collection<INetworkComponentInfo> getReceivers();

    ICloudPacketData addReceiver(INetworkComponentInfo receiver);

    ICloudPacketData addReceivers(INetworkComponentInfo... receivers);

    ICloudPacketData removeReceiver(INetworkComponentInfo receiver);

    ICloudPacketData removeReceivers(INetworkComponentInfo... receivers);

    ICloudPacketData clearReceivers();

    FutureAction<ICloudPacketResponse> waitForResponse();

    UUID getPacketId();

    ICloudPacketData getResponseTargetData();

    void setResponseTargetData(ICloudPacketData packetData);

    FutureAction<ICloudPacketResponse> getResponseAction();

    ICloudPacketData allowSenderAsReceiver();

    boolean isSenderAsReceiverAllowed();

    boolean hasReceiver(INetworkComponentInfo receiver);

    INetworkComponentInfo getSender();

}
