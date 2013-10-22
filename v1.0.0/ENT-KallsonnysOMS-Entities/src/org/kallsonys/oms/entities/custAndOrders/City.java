package org.kallsonys.oms.entities.custAndOrders;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.kallsonys.oms.entities.base.AbstractEntity;


@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class City extends AbstractEntity {

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Length(min = 3, max = 50)
	private String name;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private State stateOrProvince;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getStateOrProvince() {
		return stateOrProvince;
	}

	public void setStateOrProvince(State stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}

}
