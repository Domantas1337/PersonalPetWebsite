package com.example.petwebapplication.rest;

import com.example.petwebapplication.entities.Product;
import com.example.petwebapplication.repositories.ProductRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    private ProductRepository productRepository;

    @POST
    public Response createProduct(Product product) {
        Product createdProduct = productRepository.create(product);
        return Response.status(Response.Status.CREATED).entity(createdProduct).build();
    }

    @GET
    public Response getAllProducts() {
        return Response.ok(productRepository.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") Long id) {
        return productRepository.findById(id)
                .map(product -> Response.ok(product).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Long id) {
        try {
            productRepository.delete(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") Long id, Product product) {
        if (product.getId() == null || !product.getId().equals(id)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Product ID must match the path parameter").build();
        }
        Product updatedProduct = productRepository.update(product);
        return Response.ok(updatedProduct).build();
    }

    @GET
    @Path("/{id}/with-pet-types")
    public Response getProductWithPetTypes(@PathParam("id") Long productId) {
        try {
            Product product = productRepository.findProductWithPetTypesById(productId);
            return Response.ok(product).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
