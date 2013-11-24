package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.ItemDTO;
import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.CountryEnum;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonnys.oms.enums.OrderStatusEnum;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.utilities.Util;

import test.DAO;

@ManagedBean(name = "updateOrder")
@ViewScoped
public class UpdateOrderBean implements Serializable {

	private static final long serialVersionUID = -2152389656664659476L;

	private Long inputId;
	private Date inputDate;
	private Double inputPrice;
	private OrderStatusEnum inputState;
	private String inputComments;
	private String slCustomer;
	private String slShipper;
	private ProducerTypeEnum slProduccer;
	private OrderDTO orden;
	private List<ItemDTO> items;
	
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

				System.out.println("id " + idOrd);
				DAO d = new DAO();
				
				//invocacion EJB
				// orden = d.editCliente();

				inputId = orden.getId();
				inputDate = orden.getOrderDate();
				inputPrice = orden.getPrice();
				inputState = orden.getStatus();
				inputComments = orden.getComments();
				slCustomer = orden.getCustomer().getName();
				slShipper = orden.getShippingProvider();
				slProduccer = orden.getProducer();
			}

		}
		
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
		
	}
	
	public void factory(){
			
	}
	
	public void send(){
		
	}
	
	
	public void cotizar(){
		String resultCotizaSony = null;
		String resultCotizaRapid = null;
		
		
		if (Double.parseDouble(resultCotizaSony) > Double.parseDouble(resultCotizaRapid)){
			setStyleCotizaRapidService("color:#FF0000; font-weight: bold;");
			setStyleCotizaSony("");
		}else{
			setStyleCotizaRapidService("");
			setStyleCotizaSony("color:#FF0000; font-weight: bold;");
		}
		//invocacion ejb
		setCotizaSony(resultCotizaSony);
		setCotizaRapidService(resultCotizaRapid);
		
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
	
	

}
