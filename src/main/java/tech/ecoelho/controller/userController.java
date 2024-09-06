package tech.ecoelho.controller;

import io.vertx.ext.auth.User;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.query.Page;
import tech.ecoelho.entity.UserEntity;
import tech.ecoelho.service.UserService;

import java.awt.print.Pageable;
import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class userController {

    private final UserService userService;

    public userController(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Transactional // necessario sempre que for alterar/add algo no banco
    public Response createUser(UserEntity userEntity){
        return Response.ok(userService.createUser(userEntity)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateUserById(@PathParam("id") UUID userId, UserEntity user){
        return Response.ok(userService.updateUserById(userId, user)).build();
    }

    @GET
    public Response listAll(@QueryParam("page") @DefaultValue(value = "0") Integer page,
                            @QueryParam("pageSize") @DefaultValue(value = "10") Integer pageSize){
        var users = userService.findAll(page, pageSize);
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response listById(@PathParam("id") UUID userId){
        return Response.ok(userService.findById(userId)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteById(@PathParam("id") UUID userId){
        userService.deleteById(userId);
        return Response.noContent().build();
    }

}
