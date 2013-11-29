package org.kallsonnys.oms.services.campaigns;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kallsonnys.oms.dao.CampaignDAO;
import org.kallsonnys.oms.dto.CampaignDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.mapper.OMSMapper;
import org.kallsonys.oms.entities.custAndOrders.Campaign;


@Stateless
public class CampaignBean implements CampaignFacadeRemote, CampaignFacadeLocal {

	@PersistenceContext(unitName = "kallsonnysOrders")
	private EntityManager em;
	
	CampaignDAO dao = new CampaignDAO();
  
    public CampaignBean() {
       
    }

	@Override
	public List<CampaignDTO> getCampaigns() {
		
		dao.setEm(em);
		
		final List<Campaign> resultList = dao.getCampaigns();
		
		List<CampaignDTO> campaignDTOs = new ArrayList<CampaignDTO>(resultList.size());
		
		for (final Campaign campaign : resultList) {
			campaignDTOs.add(OMSMapper.mapCampaign(campaign));
		}
		
		return campaignDTOs;
	}
	
	public CampaignDTO createCampaign(CampaignDTO campaignDTO){
		
		Campaign campaign = new Campaign();
		campaign.setName(campaignDTO.getName());
		campaign.setDescription(campaignDTO.getDescription());
		campaign.setProdId(campaignDTO.getProdId());
		campaign.setProductName(campaignDTO.getName());
		campaign.setStartDate(campaignDTO.getStartDate());
		campaign.setEndDate(campaignDTO.getEndDate());
		campaign.setImage_url_full(campaignDTO.getImage_url_full());
		
		em.persist(campaign);
		
		campaignDTO.setId(campaign.getId());
		
		return campaignDTO;
		
	}
	
	public void updateCampaign(CampaignDTO campaignDTO){
		
		Campaign campaign = em.find(Campaign.class, campaignDTO.getId());
		campaign.setName(campaignDTO.getName());
		campaign.setDescription(campaignDTO.getDescription());
		campaign.setStartDate(campaignDTO.getStartDate());
		campaign.setEndDate(campaignDTO.getEndDate());
		
	}
	
	public void removeCampaign(CampaignDTO campaignDTO){
		Campaign campaign = em.find(Campaign.class, campaignDTO.getId());
		em.remove(campaign);
	}
	
	public TableResultDTO<CampaignDTO> getCampaigns(TableFilterDTO filter){
		
		dao.setEm(em);
		
		TableResultDTO<Campaign> campaigns = dao.getCampaigns(filter);
		
		TableResultDTO<CampaignDTO> tableResultDTO = new TableResultDTO<CampaignDTO>();
		tableResultDTO.setTotalOfRecords(campaigns.getTotalOfRecords());
		
		List<CampaignDTO> campaignDTOs = new ArrayList<CampaignDTO>(campaigns.getResult().size());
		
		for (final Campaign campaign : campaigns.getResult()) {
			campaignDTOs.add(OMSMapper.mapCampaign(campaign));
		}
		
		
		tableResultDTO.setResult(campaignDTOs);
		
		return tableResultDTO;
	}

}
