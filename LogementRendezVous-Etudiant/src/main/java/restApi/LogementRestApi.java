package restApi;

import entities.Logement;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import metiers.LogementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/logement")
@Tag(name = "Logement API", description = "Opérations CRUD sur les logements")
public class LogementRestApi {

    static LogementBusiness lBHelper = new LogementBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Lister tous les logements", responses = {
            @ApiResponse(responseCode = "200", description = "Liste des logements",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Logement.class))))
    })
    public Response listLogement() {
        return Response.status(200).entity(lBHelper.getLogements()).build();
    }

    @GET
    @Path("/{ref}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtenir un logement par référence", responses = {
            @ApiResponse(responseCode = "200", description = "Logement trouvé",
                    content = @Content(schema = @Schema(implementation = Logement.class))),
            @ApiResponse(responseCode = "404", description = "Logement non trouvé")
    })
    public Response getLogByRef(@PathParam("ref") int ref) {
        Logement log = lBHelper.getLogementsByReference(ref);
        if (log != null) {
            return Response.status(200).entity(log).build();
        } else {
            return Response.status(404).entity("Logement non trouvé").build();
        }
    }

    @GET
    @Path("/deleg")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Filtrer les logements par délégation", responses = {
            @ApiResponse(responseCode = "200", description = "Liste des logements filtrés",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Logement.class))))
    })
    public Response getLogementsByDeleguation(@QueryParam("delg") String deleguation) {
        List<Logement> result = lBHelper.getLogementsByDeleguation(deleguation);
        return Response.status(200).entity(result).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Ajouter un logement", requestBody = @RequestBody(
            required = true, content = @Content(schema = @Schema(implementation = Logement.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Logement ajouté")
            }
    )
    public Response addLogement(Logement l) {
        lBHelper.addLogement(l);
        return Response.status(201).entity("Logement ajouté avec succès").build();
    }

    @PUT
    @Path("/{ref}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Mettre à jour un logement", requestBody = @RequestBody(
            required = true, content = @Content(schema = @Schema(implementation = Logement.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Logement mis à jour"),
                    @ApiResponse(responseCode = "404", description = "Logement non trouvé pour mise à jour")
            }
    )
    public Response updateLogement(@PathParam("ref") int ref, Logement logement) {
        boolean updated = lBHelper.updateLogement(ref, logement);
        if (updated) {
            return Response.status(200).entity("Logement mis à jour avec succès").build();
        } else {
            return Response.status(404).entity("Logement non trouvé pour mise à jour").build();
        }
    }

    @DELETE
    @Path("/{ref}")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Supprimer un logement", responses = {
            @ApiResponse(responseCode = "200", description = "Logement supprimé"),
            @ApiResponse(responseCode = "404", description = "Logement non trouvé")
    })
    public Response deleteLogement(@PathParam("ref") int ref) {
        boolean deleted = lBHelper.deleteLogement(ref);
        if (deleted) {
            return Response.status(200).entity("Logement supprimé avec succès").build();
        } else {
            return Response.status(404).entity("Logement non trouvé").build();
        }
    }

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Compter le nombre total de logements", responses = {
            @ApiResponse(responseCode = "200", description = "Nombre total retourné")
    })
    public Response countLogements() {
        int total = lBHelper.getLogements().size();
        return Response.status(200).entity("Nombre total de logements : " + total).build();
    }
}
