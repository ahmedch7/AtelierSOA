package restApi;

import entities.RendezVous;
import metiers.RendezVousBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/rendezvous")
@Tag(name = "RendezVous", description = "Opérations liées aux rendez-vous")
public class RendezVousRestApi {

    static RendezVousBusiness rvBusiness = new RendezVousBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Lister tous les rendez-vous", description = "Retourne la liste complète des rendez-vous")
    public Response getAllRendezVous() {
        return Response.status(200).entity(rvBusiness.getListeRendezVous()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Ajouter un rendez-vous", description = "Ajoute un nouveau rendez-vous pour un logement donné")
    public Response addRendezVous(
            @Parameter(description = "Détails du rendez-vous à ajouter") RendezVous rv) {
        boolean added = rvBusiness.addRendezVous(rv);
        if (added) {
            return Response.status(201).entity("Rendez-vous ajouté avec succès").build();
        } else {
            return Response.status(400).entity("Logement introuvable. Impossible d’ajouter le rendez-vous").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtenir un rendez-vous par ID", description = "Retourne les détails d’un rendez-vous donné")
    public Response getRendezVousById(
            @PathParam("id") @Parameter(description = "ID du rendez-vous") int id) {
        RendezVous rv = rvBusiness.getRendezVousById(id);
        if (rv != null) {
            return Response.status(200).entity(rv).build();
        } else {
            return Response.status(404).entity("Rendez-vous non trouvé").build();
        }
    }

    @GET
    @Path("/logement/{ref}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Lister les rendez-vous par logement", description = "Retourne tous les rendez-vous associés à un logement donné")
    public Response getRendezVousByLogementRef(
            @PathParam("ref") @Parameter(description = "Référence du logement") int ref) {
        List<RendezVous> list = rvBusiness.getListeRendezVousByLogementReference(ref);
        return Response.status(200).entity(list).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Mettre à jour un rendez-vous", description = "Met à jour un rendez-vous existant par son ID")
    public Response updateRendezVous(
            @PathParam("id") @Parameter(description = "ID du rendez-vous à mettre à jour") int id,
            @Parameter(description = "Nouveaux détails du rendez-vous") RendezVous updatedRV) {
        boolean updated = rvBusiness.updateRendezVous(id, updatedRV);
        if (updated) {
            return Response.status(200).entity("Rendez-vous mis à jour avec succès").build();
        } else {
            return Response.status(404).entity("Rendez-vous ou logement non trouvé").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Supprimer un rendez-vous", description = "Supprime un rendez-vous existant par son ID")
    public Response deleteRendezVous(
            @PathParam("id") @Parameter(description = "ID du rendez-vous à supprimer") int id) {
        boolean deleted = rvBusiness.deleteRendezVous(id);
        if (deleted) {
            return Response.status(200).entity("Rendez-vous supprimé avec succès").build();
        } else {
            return Response.status(404).entity("Rendez-vous non trouvé").build();
        }
    }

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Compter les rendez-vous", description = "Retourne le nombre total de rendez-vous")
    public Response countRendezVous() {
        int total = rvBusiness.getListeRendezVous().size();
        return Response.status(200).entity("Nombre total de rendez-vous : " + total).build();
    }
}
