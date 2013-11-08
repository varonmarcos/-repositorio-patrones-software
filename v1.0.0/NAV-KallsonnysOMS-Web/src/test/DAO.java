package test;

import java.util.ArrayList;

import org.kallsonnys.oms.dto.OptionMenuDTO;
import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonnys.oms.dto.security.IntialUserLoginDTO;

public class DAO {

	public DAO() {

	}

	public IntialUserLoginDTO getTransaccionesPendientes(String user, String password)
			throws Exception {
		IntialUserLoginDTO ini = new IntialUserLoginDTO();
		UserDTO usuario = new UserDTO();
		ArrayList<OptionMenuDTO> listMenu = new ArrayList<OptionMenuDTO>();
		try {
			OptionMenuDTO menu = new OptionMenuDTO();			
			menu.setId(1L);
			menu.setOption("Administrar Productos");
			menu.setUrl("/manageProducts");
			listMenu.add(menu);
			
			OptionMenuDTO menu1 = new OptionMenuDTO();
			menu1.setId(2L);
			menu1.setOption("Consultar Productos");
			menu1.setUrl("/getsProduct");
			listMenu.add(menu1);
			
			OptionMenuDTO menu2 = new OptionMenuDTO();
			menu2.setId(3L);
			menu2.setOption("Consultar Ranking Productos");
			menu2.setUrl("/getsProductRanking");
			listMenu.add(menu2);
			
			OptionMenuDTO menu3 = new OptionMenuDTO();
			menu3.setId(4L);
			menu3.setOption("Consultar Ranking Categorias");
			menu3.setUrl("/getsProductRanking");
			listMenu.add(menu3);
			
			OptionMenuDTO menu4 = new OptionMenuDTO();
			menu4.setId(5L);
			menu4.setOption("Administrar Campañas");
			menu4.setUrl("/manageCampaing");
			listMenu.add(menu4);
			
			OptionMenuDTO menu5 = new OptionMenuDTO();
			menu5.setId(6L);
			menu5.setOption("Administrar Ordenes");
			menu5.setUrl("/manageOrders");
			listMenu.add(menu5);
			
			OptionMenuDTO menu6 = new OptionMenuDTO();
			menu6.setId(7L);
			menu6.setOption("Consultar Ordenes");
			menu6.setUrl("/getsOrder");
			listMenu.add(menu6);
			
			OptionMenuDTO menu7 = new OptionMenuDTO();
			menu7.setId(8L);
			menu7.setOption("Consultar Ranking Ordenes Abiertas");
			menu7.setUrl("/getsOrderRankingOpen");
			listMenu.add(menu7);
			
			OptionMenuDTO menu8 = new OptionMenuDTO();
			menu8.setId(9L);
			menu8.setOption("Consultar Ranking Ordenes Cerradas");
			menu8.setUrl("/getsOrderRankingClose");
			listMenu.add(menu8);
			
			OptionMenuDTO menu9 = new OptionMenuDTO();
			menu9.setId(10L);
			menu9.setOption("Actualizar Estado Orden");
			menu9.setUrl("/updateStateOrder");
			listMenu.add(menu9);
			
			OptionMenuDTO menu10 = new OptionMenuDTO();
			menu10.setId(11L);
			menu10.setOption("Administrar Clientes");
			menu10.setUrl("/manageClients");
			listMenu.add(menu10);
			
			OptionMenuDTO menu11 = new OptionMenuDTO();
			menu11.setId(12L);
			menu11.setOption("Consultar Clientes");
			menu11.setUrl("/getClient");
			listMenu.add(menu11);
			
			OptionMenuDTO menu12 = new OptionMenuDTO();
			menu12.setId(13L);
			menu12.setOption("Consultar Ranking Clientes");
			menu12.setUrl("/getClientRanking");			
			listMenu.add(menu12);

			usuario.setId(1L);
			usuario.setName(user);
			usuario.setPassword(password);
			usuario.setSurname(user);
			usuario.setEmail(user);
			usuario.setStatus(null);
			usuario.setListamenu(listMenu);
			
			ini.setUser(usuario);
			ini.setTgt(null);
			ini.setServiceTicket(null);
			

		} catch (Exception ex) {
			System.out.println("ERROR DAO: " + ex.getMessage());
			throw new Exception(ex.getMessage());
		}
		return ini;
	}

}
