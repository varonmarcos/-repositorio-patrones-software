package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CampaignDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonnys.oms.web.beans.model.CustomerDTOLazyList;
import org.kallsonnys.oms.web.beans.model.OrderDTOLazyList;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import test.DAO;

@ManagedBean(name = "updateStateOrder")
@ViewScoped
public class UpdateStateOrderBean implements Serializable {
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private OrderDTO orden;
	private LazyDataModel<OrderDTO> ordenes;
	private List<OrderDTO> list; 
	
	private String messageHeader;
	private String messageBody;
	private Severity severity;
	
	
	public UpdateStateOrderBean(){
		setOrdenes(new OrderDTOLazyList(list));		
	}

	public void onCancel(RowEditEvent event) {  
        messageHeader = "Orden Eliminado";
		messageBody = ((OrderDTO) event.getObject()).getId().toString();
		severity = FacesMessage.SEVERITY_INFO;
		
		Util.addMessage(severity, messageHeader, messageBody);  
    } 
	
	public OrderDTO getOrden() {
		return orden;
	}


	public void setOrden(OrderDTO orden) {
		this.orden = orden;
	}


	public LazyDataModel<OrderDTO> getOrdenes() {
		return ordenes;
	}


	public void setOrdenes(LazyDataModel<OrderDTO> ordenes) {
		this.ordenes = ordenes;
	}


	public List<OrderDTO> getList() {
		return list;
	}


	public void setList(List<OrderDTO> list) {
		this.list = list;
	}

}
