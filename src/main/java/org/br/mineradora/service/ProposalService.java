package org.br.mineradora.service;

import org.br.mineradora.dto.ProposalDetailDTO;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface ProposalService {
    
    ProposalDetailDTO findFullProposal(long id);
    
    void createNewProposal(ProposalDetailDTO proposalDetailDTO);

    void removeProposal(long id);
} 