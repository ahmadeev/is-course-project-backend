package _controller;

import _service.ExternalAPIService;
import dto._utils.DiceAPIDTO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import response.ResponseStatus;

@Stateless
@Path("/external")
public class ExternalAPIController {
    @EJB
    protected ExternalAPIService externalAPIService;

    @GET
    @Path("/roll-dice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rollDice(@QueryParam("amount") int amount, @QueryParam("max-value") int maxValue) {
        try {
            DiceAPIDTO dice = externalAPIService.rollDice(amount, maxValue);
            return Response.ok(new responses.ResponseEntity(ResponseStatus.SUCCESS, "", dice)).build();
        } catch (Exception e) {
            // пока не работает корректно
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new responses.ResponseEntity(ResponseStatus.ERROR, e.getMessage(), null))
                    .build();
        }
    }
}
