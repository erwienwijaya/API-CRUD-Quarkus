package org.crudapi;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/posts")
public class PostResource {

    @GET
    @Produces("application/json")
    public Response getAll() {
         List<Post> posts = Post.listAll();
         return Response.ok(posts).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") Long id) {
        return Post.findByIdOptional(id)
                .map(Post -> Response.ok(Post).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("title/{title}")
    @Produces("application/json")
    public Response getByTitle(@PathParam("title") String title) {
        return Post.find("title",title)
                .singleResultOptional()
                .map(post -> Response.ok(post).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("content/{content}")
    @Produces("application/json")
    public Response getByContent(@PathParam("content") String content) {
        List<Post> posts = Post.list("SELECT x FROM Post x WHERE x.content =?1 ORDER BY x.id DESC",content);
        return Response.ok(posts).build();
    }

    @POST
    @Transactional
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(Post post) {
        Post.persist(post);
        if(post.isPersistent()) {
            return Response.created(URI.create("/posts" + post.id)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    @Produces("application/json")
    public Response deleteById(@PathParam("id") Long id) {
         boolean deleted = Post.deleteById(id);
         if(deleted) {
            return Response.noContent().build();
         }
         return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Transactional
    @Path("{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateById(@PathParam("id") Long id, Post post) {
        Post entity = Post.findById(id);

        if(entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        entity.title = post.title;
        entity.content = post.content;

        return Response.ok(entity).build();
    }
}