package com.irchanbani.beam.avro;

import com.google.common.base.MoreObjects;
import java.util.Map;
import java.util.Objects;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

/**
 * The {@link AvroPubsubMessageRecord} class is an Avro wrapper class for {@link
 * org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage} which captures the message's fields along with
 * the event timestamp for archival purposes.
 */
@DefaultCoder(AvroCoder.class)
public class AvroPubsubMessageRecord {

    private byte[] message;
    private Map<String, String> attributes;
    private long timestamp;

    // Private empty constructor used for reflection required by AvroIO.
    @SuppressWarnings("unused")
    private AvroPubsubMessageRecord() {}

    public AvroPubsubMessageRecord(byte[] message, Map<String, String> attributes, long timestamp) {
        this.message = message;
        this.attributes = attributes;
        this.timestamp = timestamp;
    }

    public byte[] getMessage() {
        return this.message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final AvroPubsubMessageRecord other = (AvroPubsubMessageRecord) obj;

        return Objects.deepEquals(this.getMessage(), other.getMessage())
                && Objects.equals(this.getAttributes(), other.getAttributes())
                && Objects.equals(this.getTimestamp(), other.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, attributes, timestamp);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("message", message)
                .add("attributes", attributes)
                .add("timestamp", timestamp)
                .toString();
    }
}
