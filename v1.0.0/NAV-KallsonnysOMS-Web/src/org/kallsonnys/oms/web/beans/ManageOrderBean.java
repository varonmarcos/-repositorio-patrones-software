package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.enums.OrderStatusEnum;
import org.kallsonnys.oms.services.orders.OrdersFacadeRemote;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonnys.oms.web.beans.model.OrderDTOLazyList;
import org.kallsonys.oms.commons.locator.ServiceLocator;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "manageOrder")
@ViewScoped
public class ManageOrderBean implements Serializable {
	
	private static final long serialVersionUID = -2152389656664659476L;
	
	private OrderDTO orden;
	private LazyDataModel<OrderDTO> ordenes;
	private List<OrderDTO> list; 
	
	private String messageHeader;
	private Severity severity;
	
	
	public ManageOrderBean(){
		setOrdenes(new OrderDTOLazyList(list));		
	}

	public void onCancel(RowEditEvent event) {  
		
		OrderDTO orderDTO = (OrderDTO) event.getObject();
		try {
			
			if(orderDTO.getStatus() == OrderStatusEnum.CREATED){
				
				OrdersFacadeRemote ordersFacadeEJB = ServiceLocator.getInstance().getRemoteObject("OrdersBean");
				ordersFacadeEJB.removeOrder(orderDTO);
				
				messageHeader = "La Orden ha sido Eliminada "+orderDTO.getId()+" con exito";
				severity = FacesMessage.SEVERITY_INFO;
				
				Util.addMessage(severity, messageHeader, "");  
			}else{
				messageHeader = "La Orden Id."+orderDTO.getId()+" no fue eliminada porque su estado es diferente de Creada";
				severity = FacesMessage.SEVERITY_WARN;
				Util.addMessage(severity, messageHeader, "");  
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			messageHeader = "Ocurrio un error al Eliminar la Orden "+ orderDTO.getId();
			severity = FacesMessage.SEVERITY_ERROR;
			Util.addMessage(severity, messageHeader, "");
		}

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
