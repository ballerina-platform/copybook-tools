package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.RecordRestDescriptorNode;

public class RecordMetadata {
    private final boolean isOpenRecord;
    private final RecordRestDescriptorNode restDescriptorNode;

    RecordMetadata(Builder builder) {
        this.isOpenRecord = builder.isOpenRecord;
        this.restDescriptorNode = builder.restDescriptorNode;
    }

    public boolean isOpenRecord() {
        return isOpenRecord;
    }

    public RecordRestDescriptorNode getRestDescriptorNode() {
        return restDescriptorNode;
    }

    /**
     * Record meta data builder class for {@code RecordMetadata}.
     *
     * @since 1.4.0
     */
    public static class Builder {
        private boolean isOpenRecord = false;
        private RecordRestDescriptorNode restDescriptorNode = null;
        public Builder withIsOpenRecord(boolean isOpenRecord) {
            this.isOpenRecord = isOpenRecord;
            return this;
        }
        public Builder withRestDescriptorNode(RecordRestDescriptorNode restDescriptorNode) {
            this.restDescriptorNode = restDescriptorNode;
            return this;
        }

        public RecordMetadata build() {
            return new RecordMetadata(this);
        }
    }
}
