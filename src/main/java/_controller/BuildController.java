package _controller;

import _service.build.KillerBuildService;
import _service.build.SurvivorBuildService;
import auth.UserPrincipal;
import dto.build.KillerBuildDTO;
import dto.build.SurvivorBuildDTO;
import dto.perk.KillerPerkDTO;
import dto.perk.SurvivorPerkDTO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import model.build.KillerBuild;
import model.build.SurvivorBuild;
import model.perk.KillerPerk;
import model.perk.SurvivorPerk;
import model._utils.User;
import model._utils.rating.UserKillerBuildRating;
import model._utils.rating.UserSurvivorBuildRating;
import responses.ResponseEntity;
import response.ResponseStatus;

import java.security.Principal;
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
    public Response getSurvivorBuildRating(@Context SecurityContext securityContext) {
        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        User user = new User();
        user.setId(userPrincipal.getUserId());

        List<UserSurvivorBuildRating> ratedBuilds = survivorBuildService.getRatedBuilds(user);
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", ratedBuilds)).build();
    }

    @PATCH
    @Path("/survivor/{id}/rating")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSurvivorBuildRating(
            @PathParam("id") long buildId,
            @QueryParam("rating") int rating,
            @Context SecurityContext securityContext
    ) {
        // TODO: попробовать через триггеры считать среднее, чтоб было за один запрос (пока некорректно работает)
        SurvivorBuild build = new SurvivorBuild();
        build.setId(buildId);

        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        User user = new User();
        user.setId(userPrincipal.getUserId());

        survivorBuildService.updateSurvivorBuildRating(user, build, rating);
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }

    @GET
    @Path("/killer/rating")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKillerBuildRating(@Context SecurityContext securityContext) {
        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        User user = new User();
        user.setId(userPrincipal.getUserId());

        List<UserKillerBuildRating> ratedBuilds = killerBuildService.getRatedBuilds(user);
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", ratedBuilds)).build();
    }

    @PATCH
    @Path("/killer/{id}/rating")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKillerBuildRating(
            @PathParam("id") long buildId,
            @QueryParam("rating") int rating,
            @Context SecurityContext securityContext
    ) {
        // TODO: попробовать через триггеры считать среднее, чтоб было за один запрос (пока некорректно работает)
        KillerBuild build = new KillerBuild();
        build.setId(buildId);

        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        User user = new User();
        user.setId(userPrincipal.getUserId());

        killerBuildService.updateKillerBuildRating(user, build, rating);
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }

    // ------

    // 1. Случайный билд из лучших по rating (топ-10)
    @GET
    @Path("/killer/random/top-rated")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRandomTopRatedKillerBuild() {
        try {
            KillerBuild build = killerBuildService.findRandomTopRatedBuild();
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", KillerBuildDTO.fromEntity(build))).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }

    // 2. Случайный билд из самых популярных по usageCount (топ-10)
    @GET
    @Path("/killer/random/most-popular")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRandomMostPopularKillerBuild() {
        try {
            KillerBuild build = killerBuildService.findRandomMostPopularBuild();
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", KillerBuildDTO.fromEntity(build))).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }    
    }

    // 3. Случайный билд с approvedByAdmin == true
    @GET
    @Path("/killer/random/approved")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRandomApprovedKillerBuild() {
        try {
            KillerBuild build = killerBuildService.findRandomApprovedBuild();
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", KillerBuildDTO.fromEntity(build))).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }    
    }

    // 1. Случайный билд из лучших по rating (топ-10)
    @GET
    @Path("/survivor/random/top-rated")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRandomTopRatedSurvivorBuild() {
        try {
            SurvivorBuild build = survivorBuildService.findRandomTopRatedBuild();
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", SurvivorBuildDTO.fromEntity(build))).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }

    // 2. Случайный билд из самых популярных по usageCount (топ-10)
    @GET
    @Path("/survivor/random/most-popular")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRandomMostPopularSurvivorBuild() {
        try {
            SurvivorBuild build = survivorBuildService.findRandomMostPopularBuild();
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", SurvivorBuildDTO.fromEntity(build))).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }

    // 3. Случайный билд с approvedByAdmin == true
    @GET
    @Path("/survivor/random/approved")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRandomApprovedSurvivorBuild() {
        try {
            SurvivorBuild build = survivorBuildService.findRandomApprovedBuild();
            return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", SurvivorBuildDTO.fromEntity(build))).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }
}
