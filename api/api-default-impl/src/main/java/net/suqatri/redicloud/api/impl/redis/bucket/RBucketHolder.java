package net.suqatri.redicloud.api.impl.redis.bucket;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.suqatri.redicloud.api.CloudAPI;
import net.suqatri.redicloud.api.impl.redis.bucket.packet.BucketUpdatePacket;
import net.suqatri.redicloud.api.redis.bucket.IRBucketHolder;
import net.suqatri.redicloud.api.redis.bucket.IRedissonBucketManager;
import net.suqatri.redicloud.commons.function.future.FutureAction;
import org.redisson.api.RBucket;

public class RBucketHolder<T extends RBucketObject> implements IRBucketHolder<T> {

    private final String identifier;
    private final RBucket<T> bucket;
    private IRedissonBucketManager<T> bucketManager;
    private T publishedObject;

    public RBucketHolder(String identifier, IRedissonBucketManager<T> bucketManager, RBucket<T> bucket, T object) {
        this.bucket = bucket;
        this.identifier = identifier;
        this.bucketManager = bucketManager;
        if (object == null) throw new IllegalArgumentException("Object that the holder holds cannot be null");
        this.publishedObject = object;
        this.publishedObject.setHolder(this);
    }

    public void setPublishedObject(T object) {
        if (object == null) throw new IllegalArgumentException("Object that the holder holds cannot be null");
        CloudAPI.getInstance().getConsole().trace("Setting published object for bucket " + identifier + " to " + object);
        this.publishedObject = object;
        this.publishedObject.setHolder(this);
    }

    @Override
    public T get(boolean force) {
        if (this.publishedObject != null && !force) {
            return this.publishedObject;
        }
        setPublishedObject(this.bucket.get());
        return this.publishedObject;
    }

    @Override
    public IRBucketHolder<T> update(T object) {
        if (object == null) throw new IllegalArgumentException("Object that the holder holds cannot be null");
        CloudAPI.getInstance().getConsole().trace("Updating bucket " + getRedisKey() + "!");
        this.bucket.set(object);
        try {
            BucketUpdatePacket packet = new BucketUpdatePacket();
            packet.setIdentifier(this.identifier);
            packet.setRedisPrefix(this.getRedisPrefix());
            packet.setJson(this.bucketManager.getObjectCodec().getObjectMapper().writeValueAsString(object));
            packet.publishAll();
        } catch (JsonProcessingException e) {
            CloudAPI.getInstance().getConsole().error("Error while publishing bucket update packet for " + getRedisKey(), e);
        }
        return this;
    }

    @Override
    public FutureAction<IRBucketHolder<T>> updateAsync(T object) {
        if (object == null) throw new IllegalArgumentException("Object that the holder holds cannot be null");
        CloudAPI.getInstance().getConsole().trace("Updating bucket " + getRedisKey() + "!");
        FutureAction<IRBucketHolder<T>> futureAction = new FutureAction<>();

        new FutureAction<>(this.bucket.setAsync(object))
                .onFailure(futureAction)
                .onSuccess(a -> {
                    try {
                        BucketUpdatePacket packet = new BucketUpdatePacket();
                        packet.setIdentifier(this.identifier);
                        packet.setRedisPrefix(this.getRedisPrefix());
                        packet.setJson(this.bucketManager.getObjectCodec().getObjectMapper().writeValueAsString(object));
                        packet.publishAllAsync();
                    } catch (JsonProcessingException e) {
                        futureAction.completeExceptionally(e);
                        return;
                    }
                    futureAction.complete(this);
                });

        return futureAction;
    }

    @Override
    public void unlink() {
        this.bucketManager.unlink(this);
    }

    @Override
    public void mergeChanges(String json) {
        if (json == null) throw new IllegalArgumentException("Object that the holder holds cannot be null");
        try {
            CloudAPI.getInstance().getConsole().trace("Merging changes for bucket " + getRedisKey() + " | publishedObject: " + (this.publishedObject != null));
            if (this.publishedObject != null) {
                this.bucketManager.getObjectCodec().getObjectMapper().readerForUpdating(this.publishedObject).readValue(json);
                this.publishedObject.setHolder(this);
                this.publishedObject.merged();
            } else {
                this.setPublishedObject(this.bucketManager.getObjectCodec().getObjectMapper().readValue(json, this.bucketManager.getImplClass()));
                this.publishedObject.merged();
            }
        } catch (JsonProcessingException e) {
            CloudAPI.getInstance().getConsole().error("Failed to merge changes of bucket " + getRedisKey(), e);
        }
    }

    @Override
    public String getRedisKey() {
        return this.bucketManager.getRedisKey(this.identifier);
    }

    @Override
    public String getRedisPrefix() {
        return this.bucketManager.getRedisPrefix();
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof IRBucketHolder)) return false;
        if (((IRBucketHolder<?>) obj).getRedisKey().equals(this.getRedisKey())) return true;
        return true;
    }
}
