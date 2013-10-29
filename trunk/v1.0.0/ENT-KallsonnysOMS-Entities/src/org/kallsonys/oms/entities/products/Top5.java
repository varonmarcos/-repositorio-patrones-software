package org.kallsonys.oms.entities.products;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.kallsonys.oms.entities.base.AbstractEntity;

@Entity
public class Top5 extends AbstractEntity {

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private Long prodId;
	
	private Long top1;
	
	private Long top2;
	
	private Long top3;
	
	private Long top4;
	
	private Long top5;
	
	public Top5() {
		
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public Long getTop1() {
		return top1;
	}

	public void setTop1(Long top1) {
		this.top1 = top1;
	}

	public Long getTop2() {
		return top2;
	}

	public void setTop2(Long top2) {
		this.top2 = top2;
	}

	public Long getTop3() {
		return top3;
	}

	public void setTop3(Long top3) {
		this.top3 = top3;
	}

	public Long getTop4() {
		return top4;
	}

	public void setTop4(Long top4) {
		this.top4 = top4;
	}

	public Long getTop5() {
		return top5;
	}

	public void setTop5(Long top5) {
		this.top5 = top5;
	}


}
