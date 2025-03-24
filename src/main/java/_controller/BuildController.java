package _controller;

import _service.DlcService;
import _service.KillerBuildService;
import _service.SurvivorBuildService;
import dto.*;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.*;
import model.utils.User;
import model.utils.UserKillerBuildRating;
import model.utils.UserSurvivorBuildRating;
import responses.ResponseEntity;
import response.ResponseStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Path("/build")
public class BuildController {
    // не происходит никаких create, update и delete событий

    @EJB
    private SurvivorBuildService survivorBuildService;

    @EJB
    private KillerBuildService killerBuildService;

    // TODO: заглушка
    long userId = 1;

    @GET
    @Path("/survivor/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomSurvivorBuild() {
        SurvivorBuildDTO dto = SurvivorBuildDTO.fromEntity(survivorBuildService.generateRandomSurvivorBuild());
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", dto)).build();
    }

    @GET
    @Path("/killer/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomKillerBuild() {
        KillerBuildDTO dto = KillerBuildDTO.fromEntity(killerBuildService.generateRandomKillerBuild());
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", dto)).build();
    }

    @GET
    @Path("/survivor")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSurvivorBuild() {
        List<SurvivorBuild> builds = survivorBuildService.findAll();
        if (builds == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<SurvivorBuildDTO> buildDTOs = new ArrayList<>();
        for (SurvivorBuild build : builds) {
            SurvivorBuildDTO buildDTO = SurvivorBuildDTO.fromEntity(build);
            buildDTOs.add(buildDTO);
        }
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", buildDTOs)).build();
    }

    @GET
    @Path("/killer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKillerBuild() {
        List<KillerBuild> builds = killerBuildService.findAll();
        if (builds == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<KillerBuildDTO> buildDTOs = new ArrayList<>();
        for (KillerBuild build : builds) {
            KillerBuildDTO buildDTO = KillerBuildDTO.fromEntity(build);
            buildDTOs.add(buildDTO);
        }
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", buildDTOs)).build();
    }

    @POST
    @Path("/survivor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSurvivorBuild(SurvivorBuildDTO dto) {
        SurvivorBuild build = new SurvivorBuild();
        build.setPerks(dto.getPerks().stream()
                .map(SurvivorPerkDTO::toEntity)
                .collect(Collectors.toList()));
        build.getPerks().sort(Comparator.comparing(SurvivorPerk::getId));
        survivorBuildService.create(build);
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }

    @PUT
    @Path("/survivor/{id}/approve")
    @Produces(MediaType.APPLICATION_JSON)
    public Response approveSurvivorBuild(@PathParam("id") long buildId, @QueryParam("approved") boolean isApproved) {
        // проверка на админа
        try {
            survivorBuildService.approveSurvivorBuild(buildId, isApproved);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new ResponseEntity(ResponseStatus.SUCCESS, "", null)
            ).build();
        }
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }

    @POST
    @Path("/killer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKillerBuild(KillerBuildDTO dto) {
        KillerBuild build = new KillerBuild();
        build.setPerks(dto.getPerks().stream()
                        .map(KillerPerkDTO::toEntity)
                        .collect(Collectors.toList()));
        build.getPerks().sort(Comparator.comparing(KillerPerk::getId));
        killerBuildService.create(build);
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }

    @PUT
    @Path("/killer/{id}/approve")
    @Produces(MediaType.APPLICATION_JSON)
    public Response approveKillerBuild(@PathParam("id") long buildId, @QueryParam("approved") boolean isApproved) {
        // проверка на админа
        try {
            killerBuildService.approveKillerBuild(buildId, isApproved);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new ResponseEntity(ResponseStatus.SUCCESS, "", null)
            ).build();
        }
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }

    @GET
    @Path("/survivor/rating")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSurvivorBuildRating() {
        User user = new User();
        user.setId(userId);
        List<UserSurvivorBuildRating> ratedBuilds = survivorBuildService.getRatedBuilds(user);
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", ratedBuilds)).build();
    }

    @PATCH
    @Path("/survivor/{id}/rating")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSurvivorBuildRating(@PathParam("id") long buildId, @QueryParam("rating") int rating) {
        // TODO: попробовать через триггеры считать среднее, чтоб было за один запрос (пока некорректно работает)
        SurvivorBuild build = new SurvivorBuild();
        build.setId(buildId);
        User user = new User();
        user.setId(userId);
        survivorBuildService.updateSurvivorBuildRating(user, build, rating);
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }

    @GET
    @Path("/killer/rating")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKillerBuildRating() {
        User user = new User();
        user.setId(userId);
        List<UserKillerBuildRating> ratedBuilds = killerBuildService.getRatedBuilds(user);
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", ratedBuilds)).build();
    }

    @PATCH
    @Path("/killer/{id}/rating")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKillerBuildRating(@PathParam("id") long buildId, @QueryParam("rating") int rating) {
        // TODO: попробовать через триггеры считать среднее, чтоб было за один запрос (пока некорректно работает)
        KillerBuild build = new KillerBuild();
        build.setId(buildId);
        User user = new User();
        user.setId(userId);
        killerBuildService.updateKillerBuildRating(user, build, rating);
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }
}
