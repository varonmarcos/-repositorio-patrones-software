package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonnys.oms.services.customers.CustomersFacadeRemote;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonnys.oms.web.beans.model.CustomerDTOLazyList;
import org.kallsonys.oms.commons.locator.ServiceLocator;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name="manageClient")
@ViewScoped
public class ManageClientBean
  implements Serializable
{
  private static final long serialVersionUID = -2152389656664659476L;
  private CustomerDTO cliente;
  private List<CustomerStatusEnum> statuss;
  private LazyDataModel<CustomerDTO> clientes;
  private List<CustomerDTO> list;
  private String ship;
  private String bill;
  private String messageHeader;
  private String messageBody;
  private FacesMessage.Severity severity;

  public ManageClientBean()
  {
    setClientes(new CustomerDTOLazyList(this.list));

    HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    if (req != null) {
      String idCli = req.getParameter("id");
      String status = req.getParameter("state");
      if ((idCli != null) && (status != null)) {
        if (status.equals("OK")) {
          this.messageHeader = "Cliente con Id ";
          this.messageBody = (idCli + " creado correctamente");
          this.severity = FacesMessage.SEVERITY_INFO;
        } else {
          this.messageHeader = "Error al crear el Cliente ";
          this.messageBody = idCli;
          this.severity = FacesMessage.SEVERITY_ERROR;
        }
        Util.addMessage(this.severity, this.messageHeader, this.messageBody);
      }
    }
  }
  
  public void onEdit(RowEditEvent event) {
		
		CustomerDTO customerDTO = (CustomerDTO) event.getObject();
		
		try {
			
			CustomersFacadeRemote customersFacadeEJB = ServiceLocator.getInstance().getRemoteObject("CustomersBean");
			customersFacadeEJB.updateCustomerStatus(customerDTO);
			
			messageHeader = "El Cliente ha sido Editado "+customerDTO.getName()+"con exito";
			messageBody = ((ProductDTO) event.getObject()).getProdId().toString();
			severity = FacesMessage.SEVERITY_INFO;

			Util.addMessage(severity, messageHeader, messageBody);
			
		} catch (Exception e) {
			e.printStackTrace();
			messageHeader = "Ocurrio un error al Editar el Cliente "+ customerDTO.getName();
			messageBody = customerDTO.getId().toString();
			severity = FacesMessage.SEVERITY_ERROR;
			Util.addMessage(severity, messageHeader, messageBody);
		}
	
	}

  public List<CustomerStatusEnum> getStatuss()
  {
    this.statuss = new ArrayList<CustomerStatusEnum>();
    this.statuss.add(CustomerStatusEnum.PLATINUM);
    this.statuss.add(CustomerStatusEnum.GOLDEN);
    this.statuss.add(CustomerStatusEnum.SILVER);
    this.statuss.add(CustomerStatusEnum.DISABLED);
    return this.statuss;
  }

  public void setStatuss(List<CustomerStatusEnum> statuss) {
    this.statuss = statuss;
  }

  public String getShip() {
    return this.ship;
  }

  public void setShip(String ship) {
    this.ship = ship;
  }

  public String getBill() {
    return this.bill;
  }

  public void setBill(String bill) {
    this.bill = bill;
  }

  public CustomerDTO getCliente() {
    return this.cliente;
  }

  public void setCliente(CustomerDTO cliente) {
    this.cliente = cliente;
  }

  public LazyDataModel<CustomerDTO> getClientes() {
    return this.clientes;
  }

  public void setClientes(LazyDataModel<CustomerDTO> clientes) {
    this.clientes = clientes;
  }

  public List<CustomerDTO> getList() {
    return this.list;
  }

  public void setList(List<CustomerDTO> list) {
    this.list = list;
  }
}