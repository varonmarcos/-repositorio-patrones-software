package org.kallsonnys.oms.services.campaigns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kallsonnys.oms.dto.CampaignDTO;
import org.kallsonnys.oms.mapper.OMSMapper;
import org.kallsonys.oms.commons.DateUtil;
import org.kallsonys.oms.entities.custAndOrders.Campaign;


@Stateless
public class CampaignBean implements CampaignFacadeRemote, CampaignFacadeLocal {

	@PersistenceContext(unitName = "kallsonnysOrders")
	private EntityManager em;
  
    public CampaignBean() {
       
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<CampaignDTO> getCampaigns() {
		
		final StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT cmp FROM Campaign cmp WHERE cmp.endDate <= :end_date AND cmp.id = (SELECT MAX(cmp2.startDate) FROM Campaign cmp2)");
		
		final Query query = em.createQuery(jpql.toString()).setParameter("end_date", DateUtil.formatDateLastHour(new Date()));
		
		final List<Campaign> resultList = query.getResultList();
		
		List<CampaignDTO> campaignDTOs = new ArrayList<CampaignDTO>(resultList.size());
		
		for (final Campaign campaign : resultList) {
			campaignDTOs.add(OMSMapper.mapCampaign(campaign));
		}
		
		return campaignDTOs;
	}

}
