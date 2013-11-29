package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.kallsonnys.oms.dto.ItemDTO;
import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.dto.ShipmentCotizationDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.OrderStatusEnum;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.services.orders.OrdersFacadeRemote;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonys.oms.commons.locator.ServiceLocator;

@ManagedBean(name = "updateOrder")
@ViewScoped
public class UpdateOrderBean implements Serializable {

	private static final long serialVersionUID = -2152389656664659476L;

	private Long inputId;
	private Date inputDate;
	private Double inputPrice;
	private OrderStatusEnum inputState;
	private ShipmentCotizationDTO minCotization;
	private String inputComments;
	private String slCustomer;
	private String slShipper;
	private ProducerTypeEnum slProduccer;
	private OrderDTO orden;
	private List<ItemDTO> items;
	private String shipTo;
	
	private boolean disCotizar;
	private boolean disApprove;
	private boolean disFactory;
	private boolean disSend;
	
	private String cotizaSony;
	private String cotizaRapidService;
	
	private String styleCotizaSony;
	private String styleCotizaRapidService;

	public UpdateOrderBean() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		if (req != null) {
			String idOrd = req.getParameter("id");
			if (idOrd != null) {
				
				OrdersFacadeRemote ordersFacadeEJB = ServiceLocator.getInstance().getRemoteObject("OrdersBean");
				orden = ordersFacadeEJB.getOrderDetail(Long.parseLong(idOrd));


				inputId = orden.getId();
				inputDate = orden.getOrderDate();
				inputPrice = orden.getPrice();
				inputState = orden.getStatus();
				inputComments = orden.getComments();
				slCustomer = orden.getCustomer().getName()+" "+orden.getCustomer().getSurname();
				shipTo = Util.buildAdderss(orden.getCustomer(), AddressTypeEnum.SHIPPING_ADDRESS);
				slShipper = orden.getShippingProvider();
				slProduccer = orden.getProducer();
				items = orden.getItems();
			}

		}
		
		setStatusButtoms();
		
		
	}

	private void setStatusButtoms() {
		if (inputState.equals(OrderStatusEnum.CREATED)){
			setDisApprove(true);
		}
		
		if (inputState.equals(OrderStatusEnum.APPROVED)){
			setDisFactory(true);
			setDisCotizar(true);
		}
		
		if (inputState.equals(OrderStatusEnum.PROCESSING)){
			setDisSend(true);
		}
	}
	
	public void approve(){
		try {
			OrdersFacadeRemote ordersFacadeEJB = ServiceLocator.getInstance().getRemoteObject("OrdersBean");
			orden = ordersFacadeEJB.approveOrder(orden);
			inputState = orden.getStatus();
			
			setStatusButtoms();
			
		} catch (Exception e) {
			e.printStackTrace();
			Util.addMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un error aprobar la order "+orden.getId(), "");
		}
		
	}
	
	public void factory(){
		try {
			OrdersFacadeRemote ordersFacadeEJB = ServiceLocator.getInstance().getRemoteObject("OrdersBean");
			ordersFacadeEJB.fabricOrder(orden, minCotization);
			setStatusButtoms();
		} catch (Exception e) {
			e.printStackTrace();
			Util.addMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un error fabricar la order "+orden.getId(), "");
		}
		
	}
	
	public void send(){
		setStatusButtoms();
	}
	
	
	public void cotizar(){
		
		try {
			OrdersFacadeRemote ordersFacadeEJB = ServiceLocator.getInstance().getRemoteObject("OrdersBean");
			List<ShipmentCotizationDTO> orderCotization = ordersFacadeEJB.getOrderCotization(orden.getId());

			if (orderCotization.size() == 2) {

				ShipmentCotizationDTO resultCotizaSony = orderCotization.get(0);
				ShipmentCotizationDTO resultCotizaRapid = orderCotization.get(1);

				if (resultCotizaSony.getPrice() > resultCotizaRapid.getPrice()) {
					minCotization = resultCotizaRapid;
					setStyleCotizaRapidService("color:#FF0000; font-weight: bold;");
					setStyleCotizaSony("");
				} else {
					minCotization = resultCotizaSony;
					setStyleCotizaRapidService("");
					setStyleCotizaSony("color:#FF0000; font-weight: bold;");
				}
				// invocacion ejb
				setCotizaSony(resultCotizaSony.getPrice().toString());
				setCotizaRapidService(resultCotizaRapid.getPrice().toString());

				setStatusButtoms();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Util.addMessage(
					FacesMessage.SEVERITY_ERROR,
					"Ocurrio un error al obtener las cotizaciones de los fabricantes", "");
		}
		
		
	}

	public Long getInputId() {
		return inputId;
	}

	public void setInputId(Long inputId) {
		this.inputId = inputId;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Double getInputPrice() {
		return inputPrice;
	}

	public void setInputPrice(Double inputPrice) {
		this.inputPrice = inputPrice;
	}

	public OrderStatusEnum getInputState() {
		return inputState;
	}

	public void setInputState(OrderStatusEnum inputState) {
		this.inputState = inputState;
	}

	public String getInputComments() {
		return inputComments;
	}

	public void setInputComments(String inputComments) {
		this.inputComments = inputComments;
	}

	public String getSlCustomer() {
		return slCustomer;
	}

	public void setSlCustomer(String slCustomer) {
		this.slCustomer = slCustomer;
	}

	public String getSlShipper() {
		return slShipper;
	}

	public void setSlShipper(String slShipper) {
		this.slShipper = slShipper;
	}

	public OrderDTO getOrden() {
		return orden;
	}

	public void setOrden(OrderDTO orden) {
		this.orden = orden;
	}

	public List<ItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}

	public ProducerTypeEnum getSlProduccer() {
		return slProduccer;
	}

	public void setSlProduccer(ProducerTypeEnum slProduccer) {
		this.slProduccer = slProduccer;
	}

	public boolean isDisCotizar() {
		return disCotizar;
	}

	public void setDisCotizar(boolean disCotizar) {
		this.disCotizar = disCotizar;
	}

	public boolean isDisApprove() {
		return disApprove;
	}

	public void setDisApprove(boolean disApprove) {
		this.disApprove = disApprove;
	}

	public boolean isDisFactory() {
		return disFactory;
	}

	public void setDisFactory(boolean disFactory) {
		this.disFactory = disFactory;
	}

	public boolean isDisSend() {
		return disSend;
	}

	public void setDisSend(boolean disSend) {
		this.disSend = disSend;
	}

	public String getCotizaSony() {
		return cotizaSony;
	}

	public void setCotizaSony(String cotizaSony) {
		this.cotizaSony = cotizaSony;
	}

	public String getCotizaRapidService() {
		return cotizaRapidService;
	}

	public void setCotizaRapidService(String cotizaRapidService) {
		this.cotizaRapidService = cotizaRapidService;
	}

	public String getStyleCotizaSony() {
		return styleCotizaSony;
	}

	public void setStyleCotizaSony(String styleCotizaSony) {
		this.styleCotizaSony = styleCotizaSony;
	}

	public String getStyleCotizaRapidService() {
		return styleCotizaRapidService;
	}

	public void setStyleCotizaRapidService(String styleCotizaRapidService) {
		this.styleCotizaRapidService = styleCotizaRapidService;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}
	
	

}
