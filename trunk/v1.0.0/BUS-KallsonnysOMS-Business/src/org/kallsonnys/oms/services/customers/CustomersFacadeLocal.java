package org.kallsonnys.oms.services.customers;

import javax.ejb.Local;

import org.kallsonnys.oms.enums.AddressTypeEnum;
import org.kallsonys.oms.entities.custAndOrders.Address;

@Local
public interface CustomersFacadeLocal extends CustomersFacade{

	Address getCustomerAddress(Long id, AddressTypeEnum addresstype);

}
