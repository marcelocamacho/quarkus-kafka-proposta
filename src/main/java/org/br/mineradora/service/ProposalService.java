package org.br.mineradora.service;

import org.br.mineradora.dto.ProposalDTO;
import org.br.mineradora.dto.ProposalDetailDTO;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface ProposalService {
    
    ProposalDetailDTO findFullProposal(long id);
    
    ProposalDTO createNewProposal(ProposalDetailDTO proposalDetailDTO);

    void removeProposal(long id);
} 