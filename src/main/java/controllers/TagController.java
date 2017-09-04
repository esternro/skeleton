package controllers;

import api.ReceiptResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


package controllers;

import dao.ReceiptDao;
import dao.TagsDao;
import generated.tables.records.ReceiptsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;

import static java.util.stream.Collectors.toList;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagController {
    final ReceiptDao receipts;
    final TagsDao tags;

    @PUT
    @Path("/tags/{tag}")
    public void toggleTag(@PathParam("tag") String tag, Integer receipt_id) {
        if (receipts.idPresent(receipt_id)== false) {
            throw new WebApplicationException("receipt id does not exist", Response.Status.NOT_FOUND);
        }
        // find or create tag by name
        Integer tagId = tags.getTagFromName(tag);
        if (tagId == null) {
            tagId = tags.insert(tag);
        }
        receipts.toggleTagReceipt(receipt_id, tagId);
        return Response.ok().build();
    }

//    @GET
//    @Path("/tags/{tag}")
//    public List<ReceiptResponse> getReceipts(@PathParam("tag") String tag) {
//
//    }
}
