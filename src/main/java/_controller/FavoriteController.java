package _controller;

import _service.FavoriteService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
        import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.KillerBuild;
import model.SurvivorBuild;
import response.ResponseStatus;
import responses.ResponseEntity;

import java.util.List;

@Path("/favorites")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FavoriteController {
    @EJB
    private FavoriteService favoriteService;

    // TODO: заглушка
    long userId = 1;

    @GET
    @Path("/build/survivor")
    public Response getFavoriteSurvivorBuilds() {
        try {
            List<SurvivorBuild> builds = favoriteService.getFavoriteSurvivorBuilds(userId);
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", builds)).build();
        } catch (IllegalStateException e) {
            // пока не работает корректно
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }

    @POST
    @Path("/build/survivor/{buildId}")
    public Response addSurvivorBuildToFavorites(
            @PathParam("buildId") Long buildId) {
        try {
            favoriteService.addSurvivorBuildToFavorites(userId, buildId);
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
        } catch (IllegalArgumentException e) {
            // пока не работает корректно
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }

    @DELETE
    @Path("/build/survivor/{buildId}")
    public Response removeSurvivorBuildFromFavorites(
            @PathParam("buildId") Long buildId) {
        try {
            favoriteService.removeSurvivorBuildFromFavorites(userId, buildId);
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }

    @GET
    @Path("/build/killer")
    public Response getFavoriteKillerBuilds() {
        try {
            List<KillerBuild> builds = favoriteService.getFavoriteKillerBuilds(userId);
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", builds)).build();
        } catch (IllegalStateException e) {
            // пока не работает корректно
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }

    @POST
    @Path("/build/killer/{buildId}")
    public Response addKillerBuildToFavorites(
            @PathParam("buildId") Long buildId) {
        try {
            favoriteService.addKillerBuildToFavorites(userId, buildId);
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }

    @DELETE
    @Path("/build/killer/{buildId}")
    public Response removeKillerBuildFromFavorites(
            @PathParam("buildId") Long buildId) {
        try {
            favoriteService.removeKillerBuildFromFavorites(userId, buildId);
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }
}