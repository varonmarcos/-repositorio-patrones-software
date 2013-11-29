package org.kallsonnys.oms.services.orders;

import java.util.List;

import org.kallsonnys.oms.dto.OrderDTO;
import org.kallsonnys.oms.dto.ShipmentCotizationDTO;
import org.kallsonnys.oms.dto.TableFilterDTO;
import org.kallsonnys.oms.dto.TableResultDTO;


public interface OrdersFacade {
	
	void processOrder(OrderDTO orderDTO);

	List<ShipmentCotizationDTO> getOrderCotization(Long orderId);

	TableResultDTO<OrderDTO> getOrdersList(TableFilterDTO filter);

	OrderDTO getOrderDetail(Long orderId);

	OrderDTO fabricOrder(OrderDTO orderDTO, ShipmentCotizationDTO shipmentCotizationDTO);

	OrderDTO sendOrder(OrderDTO orderDTO);
	
	void removeOrder(OrderDTO orderDTO);

	OrderDTO approveOrder(OrderDTO orderDTO);
	
}
