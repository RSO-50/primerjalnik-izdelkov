package si.rsvo.primerjalnikIzdelkov.api.v1.resources;

import si.rsvo.primerjalnikIzdelkov.services.config.AppProperties;
import si.rsvo.primerjalnikIzdelkov.services.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/demo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DemoResource {


    private Logger log = Logger.getLogger(DemoResource.class.getName());

    @Inject
    private RestProperties restProperties;

    @Inject
    private AppProperties appProperties;

    @POST
    @Path("break")
    public Response makeUnhealthy() {

        restProperties.setBroken(true);

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("toMaintenance")
    public Response toMaintenance() {

        restProperties.setMaintenanceMode(true);

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("isHealthy")
    public Boolean isHealthy() {

        return appProperties.isHealthy();
    }

    @POST
    @Path("setHealthy")
    public Response setHealthy() {

        appProperties.setHealthy(true);

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("setUnhealthy")
    public Response setUnhealthy() {

        appProperties.setHealthy(false);

        return Response.status(Response.Status.OK).build();
    }
}
