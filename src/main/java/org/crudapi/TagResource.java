package org.crudapi;



import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {

    @Inject
    TagRepository tagRepository;

    @GET
    public Response getAll() {
        List<Tag> tags = tagRepository.listAll();
        return Response.ok(tags).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id) {
        return tagRepository.findByIdOptional(id)
                .map(tag -> Response.ok(tag).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("label/{label}")
    public Response getByLabel(@PathParam("label") String label) {
//        return tagRepository.find("label",label)
//                .singleResultOptional()
//                .map(tag -> Response.ok(tag).build())
//                .orElse(Response.status(Response.Status.NOT_FOUND).build());
         List<Tag> tags = tagRepository.findByLabel(label);
         return Response.ok(tags).build();
    }

    @POST
    @Transactional
    public Response create(Tag tag) {
        tagRepository.persist(tag);
        if(tagRepository.isPersistent(tag)) {
            return Response.created(URI.create("/tags" + tag.getId())).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Transactional
    @Path("{id}")
    public Response updateById(@PathParam("id") Long id, Tag tagUpdate) {
       Tag entity = tagRepository.findById(id);
       if(entity==null) {
           return Response.status(Response.Status.NOT_FOUND).build();
       }
       entity.setLabel(tagUpdate.label);

       return Response.ok(entity).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public Response deleteById(@PathParam("id") Long id) {
        boolean deleted = tagRepository.deleteById(id);
        if(deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
