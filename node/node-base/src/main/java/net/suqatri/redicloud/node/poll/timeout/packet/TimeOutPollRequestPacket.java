package net.suqatri.redicloud.node.poll.timeout.packet;

import lombok.Data;
import net.suqatri.redicloud.api.impl.packet.CloudPacket;
import net.suqatri.redicloud.api.impl.poll.timeout.TimeOutResult;
import net.suqatri.redicloud.node.NodeLauncher;

import java.util.UUID;

@Data
public class TimeOutPollRequestPacket extends CloudPacket {

    private UUID pollId;

    @Override
    public void receive() {
        NodeLauncher.getInstance().getTimeOutPollManager().getPoll(this.pollId)
                .onFailure(e -> sendResponse(TimeOutResult.ERROR))
                .onSuccess(pool -> {
                    if (pool.get().isOpenerId()) return;
                    pool.get().decide()
                            .onFailure(e -> sendResponse(TimeOutResult.ERROR))
                            .onSuccess(this::sendResponse);
                });
    }

    private void sendResponse(TimeOutResult result) {
        TimeOutPollResultPacket packet = new TimeOutPollResultPacket();
        packet.setPollID(this.pollId);
        packet.setResult(result);
        packet.getPacketData().addReceiver(packet.getPacketData().getSender());
        packet.publishAsync();
    }
}
