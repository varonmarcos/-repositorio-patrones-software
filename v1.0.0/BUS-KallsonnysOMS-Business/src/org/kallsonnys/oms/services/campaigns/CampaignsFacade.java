package org.kallsonnys.oms.services.campaigns;

import java.util.List;

import org.kallsonnys.oms.dto.CampaignDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;

public interface CampaignsFacade {
	
	List<CampaignDTO> getCampaigns();

	TableResultDTO<CampaignDTO> getCampaigns(TableFilterDTO filter);

	CampaignDTO createCampaign(CampaignDTO campaignDTO);

	void updateCampaign(CampaignDTO campaignDTO);

	void removeCampaign(CampaignDTO campaignDTO);
	
}
