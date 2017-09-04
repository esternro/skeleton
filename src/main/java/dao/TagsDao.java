package dao;

import generated.tables.records.TagsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.TAGS;


public class TagsDao {
    DSLContext dsl;

    public TagsDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public int insert(String tag) {
        TagsRecord tagsRecord = dsl
                .insertInto(TAGS, TAGS.TAG)
                .values(tag)
                .returning(TAGS.TID)
                .fetchOne();

        checkState(tagsRecord != null && tagsRecord.getTid() != null, "Insert tag failed");

        return tagsRecord.getTid();
    }

    public Integer getTagFromName(String tag){
        return dsl.selectFrom(TAGS)
                .where(TAGS.TAG.eq(tag))
                .fetchOne(TAGS.TID);
    }

    public List<TagsRecord> getAllTags(){
        return dsl.selectFrom(TAGS).fetch();
    }

}