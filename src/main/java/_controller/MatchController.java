package _controller;

import _service.match.KillerMatchService;
import _service.match.SurvivorMatchService;
import auth.UserPrincipal;
import dto.build.KillerBuildDTO;
import dto.build.SurvivorBuildDTO;
import dto.match.KillerMatchDTO;
import dto.match.SurvivorMatchDTO;
import dto.perk.KillerPerkDTO;
import dto.perk.SurvivorPerkDTO;
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
import model.match.KillerMatch;
import model.match.SurvivorMatch;
import model.perk.SurvivorPerk;
import response.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Path("/match")
public class MatchController {
    @EJB
    private KillerMatchService killerMatchService;

    @EJB
    private SurvivorMatchService survivorMatchService;

    @POST
    @Path("/killer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKillerMatch(KillerMatchDTO dto, @Context SecurityContext securityContext) {
        KillerMatch match = new KillerMatch();

        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        User user = new User();
        user.setId(userPrincipal.getUserId());

        KillerBuild build = new KillerBuild();
        build.setPerks(dto.getBuild().getPerks().stream().map(KillerPerkDTO::toEntity).collect(Collectors.toList()));

        match.setWon(dto.isWon());
        match.setBuild(build);
        match.setUser(user);

        killerMatchService.addKillerMatch(match);
        return Response.ok(new responses.ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }

    @GET
    @Path("/killer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKillerMatchByUser(@Context SecurityContext securityContext) {
        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        User user = new User();
        user.setId(userPrincipal.getUserId());

        List<KillerMatch> matches = killerMatchService.getKillerMatches(user);
        List<KillerMatchDTO> dtos = matches
                .stream()
                .map(KillerMatchDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(new responses.ResponseEntity(ResponseStatus.SUCCESS, "", dtos)).build();
    }

    @POST
    @Path("/survivor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSurvivorMatch(SurvivorMatchDTO dto, @Context SecurityContext securityContext) {
        SurvivorMatch match = new SurvivorMatch();

        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        User user = new User();
        user.setId(userPrincipal.getUserId());

        SurvivorBuild build = new SurvivorBuild();
        build.setPerks(dto.getBuild().getPerks().stream().map(SurvivorPerkDTO::toEntity).collect(Collectors.toList()));

        match.setWon(dto.isWon());
        match.setBuild(build);
        match.setUser(user);

        survivorMatchService.addSurvivorMatch(match);
        return Response.ok(new responses.ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }

    @GET
    @Path("/survivor")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSurvivorMatchByUser(@Context SecurityContext securityContext) {
        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        User user = new User();
        user.setId(userPrincipal.getUserId());

        List<SurvivorMatch> matches = survivorMatchService.getSurvivorMatches(user);
        List<SurvivorMatchDTO> dtos = matches
                .stream()
                .map(SurvivorMatchDTO::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(new responses.ResponseEntity(ResponseStatus.SUCCESS, "", dtos)).build();
    }
}