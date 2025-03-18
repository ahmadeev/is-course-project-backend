package _controller;

import _service.DlcService;
import _service.KillerBuildService;
import _service.SurvivorBuildService;
import dto.DlcDTO;
import dto.KillerBuildDTO;
import dto.SurvivorBuildDTO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Dlc;
import model.KillerBuild;
import model.SurvivorBuild;

import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/build")
public class BuildController {
    // не происходит никаких create, update и delete событий

    @EJB
    private SurvivorBuildService survivorBuildService;

    @EJB
    private KillerBuildService killerBuildService;

/*    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDlc(@PathParam("id") long id) {
        Dlc dlc = dlcService.findById(id);
        if (dlc == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        DlcDTO dlcDTO = DlcDTO.fromEntity(dlc);
        return Response.ok(dlcDTO).build();
    }*/

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
        return Response.ok(buildDTOs).build();
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
        return Response.ok(buildDTOs).build();
    }
}
