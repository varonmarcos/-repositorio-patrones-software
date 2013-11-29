package org.kallsonnys.oms.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.kallsonnys.oms.dto.AddressDTO;
import org.kallsonnys.oms.dto.CustomerDTO;
import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonnys.oms.enums.CustomerStatusEnum;
import org.kallsonnys.oms.utilities.Util;
import org.kallsonnys.oms.web.beans.model.CustomerDTOLazyList;
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
    setAddressShip(this.list);

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

  private void setAddressShip(List<CustomerDTO> clientes)
  {
    List<AddressDTO> listAddress = new ArrayList<AddressDTO>();

    for (CustomerDTO cliente : clientes) {
      listAddress = cliente.getCustomerAddress();
      for (AddressDTO address : listAddress) {
        if (address.getAddresstype().equals(AddressTypeEnum.SHIPPING_ADDRESS)) {
          setShip(address.getCountry() + ", " + address.getStateName() + ", " + address.getCityName() + ", " + address.getStreet());
        }

        if (address.getAddresstype().equals(AddressTypeEnum.BILLING_ADDRESS))
          setBill(address.getCountry() + ", " + address.getStateName() + ", " + address.getCityName() + ", " + address.getStreet());
      }
    }
  }

  public List<CustomerStatusEnum> getStatuss()
  {
    this.statuss = new ArrayList();
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