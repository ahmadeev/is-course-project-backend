package _controller;

import _service.FavoriteService;
import auth.UserPrincipal;
import dto.build.KillerBuildDTO;
import dto.build.SurvivorBuildDTO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import model._utils.User;
import model.build.KillerBuild;
import model.build.SurvivorBuild;
import response.ResponseStatus;
import responses.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/favorites")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FavoriteController {
    @EJB
    private FavoriteService favoriteService;

    @GET
    @Path("/build/survivor")
    public Response getFavoriteSurvivorBuilds(@Context SecurityContext securityContext) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();

            List<SurvivorBuild> builds = favoriteService.getFavoriteSurvivorBuilds(userPrincipal.getUserId());
            List<SurvivorBuildDTO> dtos = new ArrayList<>();
            for(SurvivorBuild build : builds) {
                dtos.add(SurvivorBuildDTO.fromEntity(build));
            }
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", dtos)).build();
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
            @PathParam("buildId") Long buildId,
            @Context SecurityContext securityContext
    ) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();

            favoriteService.addSurvivorBuildToFavorites(userPrincipal.getUserId(), buildId);
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
            @PathParam("buildId") Long buildId,
            @Context SecurityContext securityContext
    ) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();

            favoriteService.removeSurvivorBuildFromFavorites(userPrincipal.getUserId(), buildId);
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }

    @GET
    @Path("/build/killer")
    public Response getFavoriteKillerBuilds(@Context SecurityContext securityContext) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();

            List<KillerBuild> builds = favoriteService.getFavoriteKillerBuilds(userPrincipal.getUserId());
            List<KillerBuildDTO> dtos = new ArrayList<>();
            for(KillerBuild build : builds) {
                dtos.add(KillerBuildDTO.fromEntity(build));
            }
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", dtos)).build();
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
            @PathParam("buildId") Long buildId,
            @Context SecurityContext securityContext
    ) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();

            favoriteService.addKillerBuildToFavorites(userPrincipal.getUserId(), buildId);
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
            @PathParam("buildId") Long buildId,
            @Context SecurityContext securityContext
    ) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();

            favoriteService.removeKillerBuildFromFavorites(userPrincipal.getUserId(), buildId);
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }
}