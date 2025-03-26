package _controller;

import _service.tag.KillerBuildTagService;
import _service.tag.SurvivorBuildTagService;
import dto.tag.KillerBuildTagDTO;
import dto.tag.SurvivorBuildTagDTO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model._utils.User;
import model.build.KillerBuild;
import model.build.SurvivorBuild;
import model.tag.KillerBuildTag;
import model.tag.SurvivorBuildTag;
import response.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/tag")
public class TagController {
    @EJB
    protected KillerBuildTagService killerBuildTagService;

    @EJB
    protected SurvivorBuildTagService survivorBuildTagService;

    private final static long userId = 1;

    @GET
    @Path("/build/killer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKillerBuildTagByBuild(
            @DefaultValue("-1") @QueryParam("build") long buildId,
            @DefaultValue("false") @QueryParam("all") boolean all
    ) {
        List<KillerBuildTag> tags;

        System.out.println("build: " + buildId + ", all: " + all);

        // 1) юзер, 2) билд, 3) все
        if (!all && buildId == -1) {
            System.out.println("user");
            User user = new User();
            user.setId(userId);
            tags = killerBuildTagService.getKillerBuildTags(user);
        } else if (!all && buildId != -1) {
            System.out.println("build");
            KillerBuild build = new KillerBuild();
            build.setId(buildId);
            tags = killerBuildTagService.getKillerBuildTags(build);
        } else if (all && buildId == -1) {
            System.out.println("all");
            tags = killerBuildTagService.findAll();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (tags == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<KillerBuildTagDTO> tagDTOs = new ArrayList<>();
        for (KillerBuildTag tag : tags) {
            KillerBuildTagDTO tagDTO = KillerBuildTagDTO.fromEntity(tag);
            tagDTOs.add(tagDTO);
        }
        return Response.ok(new responses.ResponseEntity(ResponseStatus.SUCCESS, "", tagDTOs)).build();
    }

    @POST
    @Path("/build/killer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKillerBuildTag(KillerBuildTagDTO dto) {
        KillerBuildTag tag = new KillerBuildTag();

        User user = new User();
        user.setId(userId);

        KillerBuild build = new KillerBuild();
        build.setId(dto.getBuild().getId());

        tag.setTag(dto.getTag());
        tag.setBuild(build);
        tag.setUser(user);

        killerBuildTagService.addKillerBuildTag(tag);
        return Response.ok(new responses.ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }

    @DELETE
    @Path("/build/killer/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeKillerBuildTag(@PathParam("id") long id) {
        KillerBuildTag tag = new KillerBuildTag();

        User user = new User();
        user.setId(userId);

        tag.setUser(user);
        tag.setId(id);

        killerBuildTagService.removeKillerBuildTag(tag);
        return Response.ok(new responses.ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }

    @GET
    @Path("/build/survivor")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSurvivorBuildTagByBuild(
            @DefaultValue("-1") @QueryParam("build") long buildId,
            @DefaultValue("false") @QueryParam("all") boolean all
    ) {
        List<SurvivorBuildTag> tags;

        System.out.println("build: " + buildId + ", all: " + all);

        // 1) юзер, 2) билд, 3) все
        if (!all && buildId == -1) {
            System.out.println("user");
            User user = new User();
            user.setId(userId);
            tags = survivorBuildTagService.getSurvivorBuildTags(user);
        } else if (!all && buildId != -1) {
            System.out.println("build");
            SurvivorBuild build = new SurvivorBuild();
            build.setId(buildId);
            tags = survivorBuildTagService.getSurvivorBuildTags(build);
        } else if (all && buildId == -1) {
            System.out.println("all");
            tags = survivorBuildTagService.findAll();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (tags == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<SurvivorBuildTagDTO> tagDTOs = new ArrayList<>();
        for (SurvivorBuildTag tag : tags) {
            SurvivorBuildTagDTO tagDTO = SurvivorBuildTagDTO.fromEntity(tag);
            tagDTOs.add(tagDTO);
        }
        return Response.ok(new responses.ResponseEntity(ResponseStatus.SUCCESS, "", tagDTOs)).build();
    }

    @POST
    @Path("/build/survivor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSurvivorBuildTag(SurvivorBuildTagDTO dto) {
        SurvivorBuildTag tag = new SurvivorBuildTag();

        User user = new User();
        user.setId(userId);

        SurvivorBuild build = new SurvivorBuild();
        build.setId(dto.getBuild().getId());

        tag.setTag(dto.getTag());
        tag.setBuild(build);
        tag.setUser(user);

        survivorBuildTagService.addSurvivorBuildTag(tag);
        return Response.ok(new responses.ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }

    @DELETE
    @Path("/build/survivor/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeSurvivorBuildTag(@PathParam("id") long id) {
        SurvivorBuildTag tag = new SurvivorBuildTag();

        User user = new User();
        user.setId(userId);

        tag.setUser(user);
        tag.setId(id);

        survivorBuildTagService.removeSurvivorBuildTag(tag);
        return Response.ok(new responses.ResponseEntity(ResponseStatus.SUCCESS, "", null)).build();
    }
}
