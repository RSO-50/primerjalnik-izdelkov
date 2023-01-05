package si.rsvo.primerjalnikIzdelkov.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.rsvo.primerjalnikIzdelkov.lib.primerjalnikIzdelkovMetadata;
import si.rsvo.primerjalnikIzdelkov.services.beans.primerjalnikIzdelkovMetadataBean;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;


@Log
@ApplicationScoped
@Path("/primerjalnikIzdelkov")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class primerjalnikIzdelkovMetadataResource {

    private Logger log = Logger.getLogger(primerjalnikIzdelkovMetadataResource.class.getName());

    @Inject
    private primerjalnikIzdelkovMetadataBean primerjalnikIzdelkovMetadataBean;

    @Operation(description = "Get all izdelki.", summary = "Get all metadata")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of izdelkov",
                    content = @Content(schema = @Schema(implementation = primerjalnikIzdelkovMetadata.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getizdelkiMetadata() {

        List<primerjalnikIzdelkovMetadata> izdelki = primerjalnikIzdelkovMetadataBean.getizdelkiMetadata();

        return Response.status(Response.Status.OK).entity(izdelki).build();
    }

    @Operation(description = "Get izdelki by trgovina", summary = "Get all izdelki by given trgovina")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of izdelki",
                    content = @Content(schema = @Schema(implementation = primerjalnikIzdelkovMetadata.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    @Path("/byTrgovina/{trgovina}")
    public Response getIzdelekByTrgovina(@Parameter(description = "Trgovina izdelka.", required = true)
                                                             @PathParam("trgovina") String trgovina) {

        return primerjalnikIzdelkovMetadataBean.getTrgovinaByTrgovina(trgovina);
    }

    @Operation(description = "Get izdelki by tip", summary = "Get all izdelki by given tip")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of izdelki",
                    content = @Content(schema = @Schema(implementation = primerjalnikIzdelkovMetadata.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    @Path("/byTip/{tip}")
    public Response getIzdelekByTip(@Parameter(description = "Tip izdelka.", required = true)
                                                             @PathParam("tip") String tip) {

        return primerjalnikIzdelkovMetadataBean.getTipByTip(tip);
    }

    @GET
    @Path("/recept/{hrana}")
    public Response recept(@Parameter(description = "hrana", required = true) @PathParam("hrana") String hrana) {

        return primerjalnikIzdelkovMetadataBean.recept(hrana);
    }


}
