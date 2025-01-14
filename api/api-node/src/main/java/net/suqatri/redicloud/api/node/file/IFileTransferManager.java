package net.suqatri.redicloud.api.node.file;

import net.suqatri.redicloud.api.node.ICloudNode;
import net.suqatri.redicloud.api.node.file.process.IFileTransferReceiveProcess;
import net.suqatri.redicloud.api.redis.bucket.IRBucketHolder;
import net.suqatri.redicloud.commons.function.future.FutureAction;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface IFileTransferManager {

    ConcurrentHashMap<UUID, IFileTransferReceiveProcess> getWaitingReceiveProcesses();

    FutureAction<Boolean> getPullingRequest();

    void addProcessQueue(UUID transferId);

    FutureAction<File> transferFolderToNode(File folder, File targetFile, String targetFilePathToDelete, IRBucketHolder<ICloudNode> holder);

    FutureAction<File> transferFolderToNode(File folder, File targetFile, String targetFilePathToDelete, UUID nodeId);

    void cancelTransfer(UUID transferId, boolean interrupt);

    FutureAction<Boolean> pullFile(String originalFilePath, File destinationFile, File targetFileToDelete, IRBucketHolder<ICloudNode> holder);


}
