package org.br.mineradora.service;

import java.util.Date;

import org.br.mineradora.dto.ProposalDTO;
import org.br.mineradora.dto.ProposalDetailDTO;
import org.br.mineradora.entity.ProposalEntity;
import org.br.mineradora.message.KafkaEvent;
import org.br.mineradora.repository.ProposalRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

public class ProposalServiceImpl implements ProposalService {

    @Inject
    ProposalRepository proposalRepository;

    @Inject
    KafkaEvent kafkaMessages;

    @Override
    public ProposalDetailDTO findFullProposal(long id) {
        ProposalEntity proposal = proposalRepository.findById(id);


        return ProposalDetailDTO.builder()
            .proposalId(proposal.getId())
            .proposalValidityDays(proposal.getProposalValidtyDays())
            .country(proposal.getCountry())
            .priceTonne(proposal.getPriceTonne())
            .customer(proposal.getCustomer())
            .tonnes(proposal.getTonnes())
            .build();

    }

    @Transactional
    public void createNewProposal(ProposalDetailDTO proposalDetailDTO) {
        ProposalDTO proposal = buildAndSaveNewProposal(proposalDetailDTO);
        kafkaMessages.sendNewKafkaEvent(proposal);
    }

    @Transactional
    private ProposalDTO buildAndSaveNewProposal(ProposalDetailDTO proposalDetailDTO) {

        try {
            ProposalEntity proposal = new ProposalEntity();

            proposal.setCreatedAt(new Date());
            proposal.setProposalValidtyDays(proposalDetailDTO.getProposalValidityDays());
            proposal.setCountry(proposalDetailDTO.getCountry());
            proposal.setCustomer(proposalDetailDTO.getCustomer());
            proposal.setPriceTonne(proposalDetailDTO.getPriceTonne());
            proposal.setTonnes(proposalDetailDTO.getTonnes());

            proposalRepository.persist(proposal);

            return ProposalDTO.builder()
                .proposalId(proposalRepository.findByCustomer(proposal.getCustomer()).get().getId())
                .priceTonne(proposal.getPriceTonne())
                .customer(proposal.getCustomer()).build();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional
    public void removeProposal(long id) {
       proposalRepository.deleteById(id);

    }
    
}
