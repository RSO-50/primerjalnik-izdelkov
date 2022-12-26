package si.rsvo.izdelki.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.rsvo.izdelki.api.v1.dtos.UploadImageResponse;
import si.rsvo.izdelki.lib.izdelkiMetadata;
import si.rsvo.izdelki.services.beans.izdelkiMetadataBean;
import si.rsvo.izdelki.services.clients.AmazonRekognitionClient;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Log
@ApplicationScoped
@Path("/izdelki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class izdelkiMetadataResource {

    private Logger log = Logger.getLogger(izdelkiMetadataResource.class.getName());

    @Inject
    private izdelkiMetadataBean izdelkiMetadataBean;

    @Operation(description = "Get all izdelki.", summary = "Get all metadata")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of izdelki",
                    content = @Content(schema = @Schema(implementation = izdelkiMetadata.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getizdelkiMetadata() {

        List<izdelkiMetadata> izdelki = izdelkiMetadataBean.getizdelkiMetadata();

        return Response.status(Response.Status.OK).entity(izdelki).build();
    }

    /*
    @Operation(description = "Get izdelki by uporabnik", summary = "Get all izdelki by one uporabnik")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of izdelki by uporabnik",
                    content = @Content(schema = @Schema(implementation = izdelkiMetadata.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    @Path("/{izdelekId}")
    public Response getizdelkiMetadata(@Parameter(description = "izdelek ID.", required = true)
                                                               @PathParam("izdelekId") Integer izdelekId) {

        List<izdelkiMetadata> favouritesMetadata = izdelkiMetadataBean.getIzdelkibyCena(izdelekId);

        return Response.status(Response.Status.OK).entity(favouritesMetadata).build();
    }

    @Operation(description = "Get all favourites for all users.", summary = "Get all metadata")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of favourites and users",
                    content = @Content(schema = @Schema(implementation = izdelkiMetadata.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getizdelkiMetadata() {

        List<izdelkiMetadata> favouritesMetadata = izdelkiMetadataBean.getizdelkiMetadata();

        return Response.status(Response.Status.OK).entity(favouritesMetadata).build();
    }
    @Operation(description = "Add izdelki metadata.", summary = "Add metadata")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Metadata successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })


    @POST
    public Response createizdelkiMetadata(@RequestBody(
            description = "DTO object with izdelki metadata.",
            required = true, content = @Content(
            schema = @Schema(implementation = izdelkiMetadata.class))) izdelkiMetadata izdelkiMetadata) {

        if ((izdelkiMetadata.getCenaId() == null || izdelkiMetadata.getIzdelekId() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            izdelkiMetadata = izdelkiMetadataBean.createizdelkiMetadata(izdelkiMetadata);
        }

        return Response.status(Response.Status.CONFLICT).entity(izdelkiMetadata).build();

    }

    @Operation(description = "Delete metadata for izdelek by one uporabnik.", summary = "Delete metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "204",
                    description = "Metadata successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("/{cenaId}/{izdelekId}")
    public Response deleteImageMetadata(@Parameter(description = "Metadata ID.", required = true)
                                        @PathParam("cenaId") Integer cenaId,
                                        @PathParam("izdelekId") Integer izdelekId){

        boolean deleted = izdelkiMetadataBean.deleteizdelkiMetadata(cenaId, izdelekId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Get metadata for an id.", summary = "Get metadata for an id")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Shramba metadata",
                    content = @Content(
                            schema = @Schema(implementation = UporabnikoviIzdelkiMetadata.class))
            )})
    @GET
    @Path("/{id}")
    public Response getImageMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("id") Integer id) {

        UporabnikoviIzdelkiMetadata shrambaMetadata = uporabnikoviIzdelkiMetadataBean.getUporabnikoviIzdelkiMetadata(id);

        if (shrambaMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(shrambaMetadata).build();
    }

     */

    /*
    @Operation(description = "Add image metadata.", summary = "Add metadata")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Metadata successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @POST
    public Response createImageMetadata(@RequestBody(
            description = "DTO object with image metadata.",
            required = true, content = @Content(
            schema = @Schema(implementation = ImageMetadata.class))) ImageMetadata imageMetadata) {

        if ((imageMetadata.getTitle() == null || imageMetadata.getDescription() == null || imageMetadata.getUri() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            imageMetadata = uporabnikoviIzdelkiMetadata.createImageMetadata(imageMetadata);
        }

        return Response.status(Response.Status.CONFLICT).entity(imageMetadata).build();

    }


    @Operation(description = "Update metadata for an image.", summary = "Update metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully updated."
            )
    })
    @PUT
    @Path("{imageMetadataId}")
    public Response putImageMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("imageMetadataId") Integer imageMetadataId,
                                     @RequestBody(
                                             description = "DTO object with image metadata.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = ImageMetadata.class)))
                                             ImageMetadata imageMetadata){

        imageMetadata = uporabnikoviIzdelkiMetadata.putImageMetadata(imageMetadataId, imageMetadata);

        if (imageMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete metadata for an image.", summary = "Delete metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{imageMetadataId}")
    public Response deleteImageMetadata(@Parameter(description = "Metadata ID.", required = true)
                                        @PathParam("imageMetadataId") Integer imageMetadataId){

        boolean deleted = uporabnikoviIzdelkiMetadata.deleteImageMetadata(imageMetadataId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response uploadImage(InputStream uploadedInputStream) {

        String imageId = UUID.randomUUID().toString();
        String imageLocation = UUID.randomUUID().toString();

        byte[] bytes = new byte[0];
        try (uploadedInputStream) {
            bytes = uploadedInputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UploadImageResponse uploadImageResponse = new UploadImageResponse();

        Integer numberOfFaces = amazonRekognitionClient.countFaces(bytes);
        uploadImageResponse.setNumberOfFaces(numberOfFaces);

        if (numberOfFaces != 1) {
            uploadImageResponse.setMessage("Image must contain one face.");
            return Response.status(Response.Status.BAD_REQUEST).entity(uploadImageResponse).build();

        }

        List<String> detectedCelebrities = amazonRekognitionClient.checkForCelebrities(bytes);

        if (!detectedCelebrities.isEmpty()) {
            uploadImageResponse.setMessage("Image must not contain celebrities. Detected celebrities: "
                    + detectedCelebrities.stream().collect(Collectors.joining(", ")));
            return Response.status(Response.Status.BAD_REQUEST).entity(uploadImageResponse).build();
        }

        uploadImageResponse.setMessage("Success.");

        // Upload image to storage

        // Generate event for image processing


        return Response.status(Response.Status.CREATED).entity(uploadImageResponse).build();
    }


     */

}
