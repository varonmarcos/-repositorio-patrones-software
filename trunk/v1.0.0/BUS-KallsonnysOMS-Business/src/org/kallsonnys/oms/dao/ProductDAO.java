package org.kallsonnys.oms.dao;

import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.ENTITY_NOT_FOUND;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.kallsonnys.oms.dto.FilterConstants;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.ProductCategoryEnum;
import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.entities.products.Product;
import org.kallsonys.oms.entities.products.Top5;

public class ProductDAO implements BaseDAO {

	private EntityManager em;

	@Override
	public void setEm(final EntityManager em) {
		this.em = em;
	}
	
	public Product getProduct(final Long prodId) {
		if (prodId == null)
			return null;
		try {
			final Product product = (Product) em
					.createQuery(
							"SELECT prod FROM Product prod WHERE prod.prodId = :prodId")
					.setParameter("prodId", prodId).getSingleResult();

			return product;
		} catch (final NoResultException e) {
			throw new OMSException(ENTITY_NOT_FOUND.getCode(),
					ENTITY_NOT_FOUND.getMsg());
		}
	}

	@SuppressWarnings("unchecked")
	public TableResultDTO<Product> getProducts(final TableFilterDTO filter) {

		final String columnSorter = (filter.getColumnSorter() == null) ? "id"
				: filter.getColumnSorter();
		final String sorterType = (filter.getSorterType() == null) ? TableFilterDTO.ASC
				: filter.getSorterType();
		
		Long prodId = null;
		if(filter.getStringVal(FilterConstants.PROD_ID)!=null){
			prodId = Long.parseLong(filter.getStringVal(FilterConstants.PROD_ID));						
		}
		
		final ProductCategoryEnum category = filter.getVal(FilterConstants.PROD_CAT);
		final String prodName = filter.getStringVal(FilterConstants.NAME);
		final String desc = filter.getStringVal(FilterConstants.DESCRIPTION);

		final int start = filter.getStart();
		final int pageSize = filter.getPageSize();
		
		final StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT prod FROM Product prod WHERE 1=1 ");
		
		if(prodId != null){
			jpql.append("AND prod.prodId = :prodId ");
		}
		
		if(prodName != null){
			jpql.append("AND UPPER(prod.name) LIKE UPPER(:prodName) ");
		}

		if(desc != null){
			jpql.append("AND UPPER(prod.description) LIKE UPPER(:desc) ");
		}
		
		if(category != null){
			jpql.append("AND prod.category = :category");
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
		
		if(prodName != null && !prodName.equals("")){
			query.setParameter("prodName", "%" + prodName + "%");
			queryCount.setParameter("prodName", "%" + prodName + "%");
		}

		if(desc != null && !desc.equals("")){
			query.setParameter("desc", "%" + desc + "%");
			queryCount.setParameter("desc", "%" + desc + "%");
		}
		
		if(category != null){
			query.setParameter("category", category);
			queryCount.setParameter("category", category);
		}
		
		if(prodId==null){
			query.setFirstResult(pageSize * start).setMaxResults(pageSize);			
		}
		
		final List<Product> resultList = query.getResultList();
		
		int maxResults = 1;
		if(prodId==null){
			final List<Long> resultListCount = queryCount.getResultList();
			maxResults = (resultListCount.size() == 0) ? 0 : resultListCount.get(0).intValue();
		}
		
	
		
		final TableResultDTO<Product> tableResultDTO = new TableResultDTO<Product>();
		tableResultDTO.setResult(resultList);
		tableResultDTO.setTotalOfRecords(maxResults);
		
		return tableResultDTO;
	}
	
	public Top5 getProductTop5(final Long prodId){
		
		try {
			
			final Top5 top5 = (Top5) em
					.createQuery("SELECT tp FROM Top5 tp WHERE tp.prodId = :prodId")
					.setParameter("prodId", prodId).getSingleResult();
			
			return top5;
			
		}catch (final NoResultException e) {
			return null;
		}
    	
    }
	
	public void removeTop5(Long prodId){
		
		em.createQuery(
				"DELETE FROM Top5 tp WHERE  tp.id = :prodId").setParameter("prodId", prodId).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getTopProducts(Top5 top5) {

		List<Long> productIds = new ArrayList<Long>();
		productIds.add(top5.getTop1());
		productIds.add(top5.getTop2());
		productIds.add(top5.getTop3());
		productIds.add(top5.getTop4());
		productIds.add(top5.getTop5());

		List<Product> resultList = em
				.createQuery(
						"SELECT prod FROM Product prod WHERE prod.id IN (:productIds)")
				.setParameter("productIds", productIds).getResultList();
		
		return resultList.size() == 0 || resultList.get(0) == null ? null
				: resultList;
	}
    
    @SuppressWarnings("unchecked")
	public Map<Long,Top5> getProductsTop5(final List<Long> productIds){
    	
		final List<Object[]> resultList = em
				.createQuery(
						"SELECT tp.prodId, tp FROM Top5 tp WHERE tp.prodId IN (:productIds) GROUP BY tp.prodId")
				.setParameter("productIds", productIds).getResultList();
		
		 Map<Long,Top5> top5Map = new HashMap<Long, Top5>();
		for (Object[] objects : resultList) {
			top5Map.put((Long)objects[0], (Top5)objects[1]);
		}
    	
		return top5Map;
    }


}
