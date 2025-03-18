package _controller;

import _service.DlcService;
import _service.KillerPerkService;
import _service.SurvivorPerkService;
import dto.*;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.*;
import response.ResponseStatus;
import responses.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/perk")
public class PerkController {
    // не происходит никаких create, update и delete событий

    @EJB
    private SurvivorPerkService survivorPerkService;

    @EJB
    private KillerPerkService killerPerkService;

    @GET
    @Path("/survivor")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSurvivorPerk() {
        List<SurvivorPerk> perks = survivorPerkService.findAll();
        if (perks == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<SurvivorPerkDTO> perkDTOs = new ArrayList<>();
        for (SurvivorPerk perk : perks) {
            SurvivorPerkDTO perkDTO = SurvivorPerkDTO.fromEntity(perk);
            perkDTOs.add(perkDTO);
        }
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", perkDTOs)).build();
    }

    @GET
    @Path("/killer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKillerPerk() {
        List<KillerPerk> perks = killerPerkService.findAll();
        if (perks == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<KillerPerkDTO> perkDTOs = new ArrayList<>();
        for (KillerPerk perk : perks) {
            KillerPerkDTO perkDTO = KillerPerkDTO.fromEntity(perk);
            perkDTOs.add(perkDTO);
        }
        return Response.ok(new ResponseEntity(ResponseStatus.SUCCESS, "", perkDTOs)).build();
    }
}
