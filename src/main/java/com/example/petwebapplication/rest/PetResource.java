package com.example.petwebapplication.rest;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.repositories.PetRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    private PetRepository petRepository;

    @POST
    public Response createPet(Pet pet) {
        Pet createdPet = petRepository.create(pet);

        return Response.status(Response.Status.CREATED).entity(createdPet).build();
    }

    @GET
    public Response getAllPets() {
        System.out.println("Kaaaaa");
        return Response.ok(petRepository.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getPetById(@PathParam("id") Long id) {
        return petRepository.findById(id)
                .map(pet -> Response.ok(pet).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response deletePet(@PathParam("id") Long id) {
        try {
            petRepository.delete(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updatePet(@PathParam("id") Long id, Pet pet) {
        if (pet.getId() == null || !pet.getId().equals(id)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Pet ID must match the path parameter").build();
        }
        Pet updatedPet = petRepository.update(pet);
        return Response.ok(updatedPet).build();
    }

}
