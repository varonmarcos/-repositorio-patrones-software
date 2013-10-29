package org.kallsonnys.oms.services.campaigns;

import java.util.List;

import org.kallsonnys.oms.dto.CampaignDTO;

public interface CampaignsFacade {
	
	List<CampaignDTO> getCampaigns();
	
}
