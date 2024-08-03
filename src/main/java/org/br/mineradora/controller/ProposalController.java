package org.br.mineradora.controller;

import org.br.mineradora.dto.ProposalDetailDTO;
import org.br.mineradora.service.ProposalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/proposal")

public class ProposalController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Inject
    ProposalService proposalService;

    @GET
    @Path("/{id}")
    public ProposalDetailDTO findDetailsProposal(@PathParam("id") long id){
        return proposalService.findFullProposal(id);
    }

    @POST
    public Response createProposal(ProposalDetailDTO proposalDetails){
        LOG.info("-- Recebendo Proposta de Compra --");

        try{
           var proposal =  proposalService.createNewProposal(proposalDetails);
            return Response.ok().entity(proposal)
            .build();
        } catch(Exception e){

            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removeProposal(@PathParam("id") long id){

        try{
            proposalService.removeProposal(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }



}
