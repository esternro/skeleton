package dao;

import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;


public class TagsDao {
    DSLContext dsl;

    public TagsDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }


    public int insert(String tag, int receipt_id) {

        TagsRecord tagsRecord = dsl
                .insertInto(TAGS, TAGS.RECEIPT_ID, TAGS.TAG)
                .values(receipt_id, tag)
                .returning(TAGS.TID, TAGS.RECEIPT_ID, TAGS.TAG)
                .fetchOne();

        checkState(tagsRecord != null && tagsRecord.getTid() != null, "Insert tag failed");

        return tagsRecord.getTid();
    }

    public Integer getTagFromName(String tag){
        return dsl.selectFrom(TAGS)
                .where(TAGS.TAG.eq(tag))
                .fetchOne(TAGS.RECEIPT_ID);
    }

    public boolean taggedReceipts(String tag, int receipt_id){
        TagsRecord tagged = dsl
                .selectFrom(TAGS)
                .where(TAGS.TAG.eq(tag).and(TAGS.RECEIPT_ID.eq(receipt_id)))
                .fetchOne();

        if (tagged!=null){
            return true;
        }else {
            return false;
        }
    }

    public void delete(String tag, int receipt_id){
        dsl.delete(TAGS)
                .where(TAGS.TAG.eq(tag).and(TAGS.RECEIPT_ID.eq(receipt_id)))
                .execute();
    }

    public List<TagsRecord> getAllTags(){
        return dsl.selectFrom(TAGS).fetch();
    }

    public List<Integer> getTagsWithReceipts(String tag){
        return dsl.selectFrom(TAGS)
                .where(TAGS.TAG.eq(tag))
                .fetch(TAGS.RECEIPT_ID);
     //return dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.in(RECEIPTS).fetch());
    }

//    public List<ReceiptsRecord> getTags(String tag){
//        List <Integer> receipt_id = dsl.selectFrom(TAGS).where(TAGS.TAG.eq(tag)).fetch()
//                .stream().map(x->x.getReceiptId()).collect(Collectors.toList());
//
//
//        System.out.println(receipt_id);
//        return dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.in(receipt_id)).fetch();
//    }


}