package org.kallsonnys.oms.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.kallsonnys.oms.dto.FilterConstants;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonys.oms.commons.util.DateUtil;
import org.kallsonys.oms.entities.custAndOrders.Campaign;

public class CampaignDAO implements BaseDAO{

	private EntityManager em;

	public CampaignDAO() {
		
	}

	@SuppressWarnings("unchecked")
	public List<Campaign> getCampaigns(){
		
		final StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT cmp FROM Campaign cmp WHERE cmp.startDate <= :end_date AND cmp.endDate >= :start_date");
		Date now = new Date();
		final Query query = em.createQuery(jpql.toString())
				.setParameter("start_date", DateUtil.getFirstDayOfWeek(now))
				.setParameter("end_date", DateUtil.getLastDayOfWeek(now));
		
		final List<Campaign> resultList = query.getResultList();
		
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public TableResultDTO<Campaign> getCampaigns(TableFilterDTO filter){
		
		final String columnSorter = (filter.getColumnSorter() == null) ? "id"
				: filter.getColumnSorter();
		final String sorterType = (filter.getSorterType() == null) ? TableFilterDTO.ASC
				: filter.getSorterType();
		
		final int start = filter.getStart();
		final int pageSize = filter.getPageSize();
		
		final String name = filter.getStringVal(FilterConstants.NAME);
		final String prodName = filter.getStringVal(FilterConstants.PROD_NAME);
		final String desc = filter.getStringVal(FilterConstants.DESCRIPTION);
		
		Long prodId = null;
		if(filter.getStringVal(FilterConstants.PROD_ID)!=null){
			prodId = Long.parseLong(filter.getStringVal(FilterConstants.PROD_ID));						
		}
		
		final StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT cmp FROM Campaign cmp WHERE 1=1 ");
		
		if(name != null){
			jpql.append("AND UPPER(cmp.name) LIKE UPPER(:name) ");
		}

		if(desc != null){
			jpql.append("AND UPPER(cmp.description) LIKE UPPER(:desc) ");
		}
		
		if(prodName != null){
			jpql.append("AND UPPER(cmp.productName) LIKE UPPER(:prodName) ");
		}
		
		if(prodId != null){
			jpql.append("AND cmp.prodId = :prodId ");
		}
		
		jpql.append(" ORDER BY ").append(columnSorter).append(" ").append(sorterType);
		
		// Create count query
		final StringBuilder jpqlCount = new StringBuilder(jpql.toString());
		jpqlCount.replace(7, 11, "COUNT(*) ");
		

		// Define parameters for queries
		final Query query = em.createQuery(jpql.toString());
		final Query queryCount = em.createQuery(jpqlCount.toString());
		
		if(prodId != null){
			query.setParameter("prodId", prodId);
			queryCount.setParameter("prodId", prodId);
		}
		
		if(name != null && !name.equals("")){
			query.setParameter("name", "%" + name + "%");
			queryCount.setParameter("name", "%" + name + "%");
		}
		
		if(prodName != null && !prodName.equals("")){
			query.setParameter("prodName", "%" + prodName + "%");
			queryCount.setParameter("prodName", "%" + prodName + "%");
		}

		if(desc != null && !desc.equals("")){
			query.setParameter("desc", "%" + desc + "%");
			queryCount.setParameter("desc", "%" + desc + "%");
		}
		
		query.setFirstResult(pageSize * start).setMaxResults(pageSize);
		
		final List<Campaign> resultList = query.getResultList();
		
		
		final List<Long> resultListCount = queryCount.getResultList();
		int maxResults = (resultListCount.size() == 0) ? 0 : resultListCount.get(0).intValue();
		
		final TableResultDTO<Campaign> tableResultDTO = new TableResultDTO<Campaign>();
		tableResultDTO.setResult(resultList);
		tableResultDTO.setTotalOfRecords(maxResults);
		
		return tableResultDTO;
	}
	
	@Override
	public void setEm(final EntityManager em) {
		this.em = em;
	}

}
