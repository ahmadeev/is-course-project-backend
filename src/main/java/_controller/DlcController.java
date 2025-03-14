package _controller;

import _service.DlcService;
import dto.DlcDTO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Dlc;

import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/dlc")
public class DlcController {
    // не происходит никаких create, update и delete событий

    @EJB
    private DlcService dlcService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDlc(@PathParam("id") long id) {
        Dlc dlc = dlcService.findById(id);
        if (dlc == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        DlcDTO dlcDTO = DlcDTO.fromEntity(dlc);
        return Response.ok(dlcDTO).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDlc() {
        List<Dlc> dlcs = dlcService.findAll();
        if (dlcs == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<DlcDTO> dlcDTOs = new ArrayList<>();
        for (Dlc dlc : dlcs) {
            DlcDTO dlcDTO = DlcDTO.fromEntity(dlc);
            dlcDTOs.add(dlcDTO);
        }
        return Response.ok(dlcDTOs).build();
    }
}
