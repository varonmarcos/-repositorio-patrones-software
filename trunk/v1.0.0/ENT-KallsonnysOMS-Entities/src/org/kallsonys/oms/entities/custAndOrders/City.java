/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Ian Hlavats.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */
package org.kallsonys.oms.entities.custAndOrders;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.kallsonys.oms.entities.AbstractEntity;


@Entity
public class City extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1124357744815785459L;

	private Double latitude;

	private Double longitude;

	private String name;

	private ProvinceState provinceState;

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getName() {
		return name;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public ProvinceState getProvinceState() {
		return provinceState;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProvinceState(ProvinceState state) {
		this.provinceState = state;
	}
}
