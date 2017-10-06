package api;

import com.fasterxml.jackson.annotation.JsonProperty;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;

import java.util.List;

import java.math.BigDecimal;
import java.sql.Time;

public class TagsResponse {
    @JsonProperty
    int id;

    @JsonProperty
    String tag;

    @JsonProperty
    int receipt_id;

    public TagsResponse(TagsRecord dbRecord) {
        this.id = dbRecord.getTid();
        this.tag = dbRecord.getTag();
        this.receipt_id = dbRecord.getReceiptId();
    }
}
