/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package codes.recursive.cnms.user;

import codes.recursive.cnms.user.model.Product;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

/**
 * Resource for managing products
 */
@Path("/product")
@RequestScoped
public class ProductResource {

    private final codes.recursive.cnms.user.ProductRepository productRepository;

    @Context
    UriInfo uriInfo;

    @Inject
    public ProductResource(codes.recursive.cnms.user.ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDefaultMessage() {
        return Response.ok("OK").build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id) {
        Product product = productRepository.get(id);
        if( product != null ) {
            return Response.ok(product).build();
        }
        else {
            return Response.status(404).build();
        }
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        return Response.ok(this.productRepository.findAll()).build();
    }

    @Path("/list/{offset}/{max}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsersPaginated(@PathParam("offset") int offset, @PathParam("max") int max) {
        return Response.ok(this.productRepository.findAll(offset, max)).build();
    }

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(RequestBody rq) {
        productRepository.create(rq);
        return Response.ok("{ status: Saved, product_id: "+rq.PRODUCT_ID+"}", MediaType.APPLICATION_JSON).build();

    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(Product user) {
        Set<ConstraintViolation<Product>> violations = productRepository.validate(user);

        if( violations.size() == 0 ) {
            productRepository.save(user);
            //return Response.created(
            //        uriInfo.getBaseUriBuilder()
            //                .path("/user/{id}")
            //                .build(user.getPRODUCT_ID())
            //).build();
            return Response.ok("{ status: Saved, product_id: "+user.product_id+"}", MediaType.APPLICATION_JSON).build();

        }
        else {
            List<HashMap<String, String>> errors = new ArrayList<>();

            violations.stream()
                    .forEach( (violation) -> {
                                Object invalidValue = violation.getInvalidValue();
                                HashMap<String, String> errorMap = new HashMap<>();
                                errorMap.put("field", violation.getPropertyPath().toString());
                                errorMap.put("message", violation.getMessage());
                                errorMap.put("currentValue", invalidValue == null ? null : invalidValue.toString());
                                errors.add(errorMap);
                            }
                    );

            return Response.status(422)
                    .entity("validationErrors")
                    .build();
        }

    }

    @Path("{id}")
    @DELETE
    public Response deleteUser(@PathParam("id") String id) {
        productRepository.deleteById(id);
        //return Response.noContent().build();
        return Response.ok("{ status: Deleted, product_id: "+id+"}", MediaType.APPLICATION_JSON).build();
    }


}
