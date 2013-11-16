package test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.kallsonnys.oms.ProfileDTO;
import org.kallsonnys.oms.dto.OptionMenuDTO;
import org.kallsonnys.oms.dto.ProductDTO;
import org.kallsonnys.oms.dto.TableResultDTO;
import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonnys.oms.dto.security.IntialUserLoginDTO;
import org.kallsonnys.oms.enums.ProducerTypeEnum;
import org.kallsonnys.oms.enums.ProductCategoryEnum;
import org.kallsonnys.oms.enums.StatusEnum;

public class DAO {
	
	private String[] manes;  
	  
    private String[] descriptions;
    
	public DAO() {
		descriptions = new String[10];  
		descriptions[0] = "Black";  
		descriptions[1] = "White";  
		descriptions[2] = "Green";  
		descriptions[3] = "Red";  
		descriptions[4] = "Blue";  
		descriptions[5] = "Orange";  
		descriptions[6] = "Silver";  
		descriptions[7] = "Yellow";  
		descriptions[8] = "Brown";  
		descriptions[9] = "Maroon";  
  
		manes = new String[10];  
		manes[0] = "Mercedes";  
		manes[1] = "BMW";  
		manes[2] = "Volvo";  
		manes[3] = "Audi";  
		manes[4] = "Renault";  
		manes[5] = "Opel";  
		manes[6] = "Volkswagen";  
		manes[7] = "Chrysler";  
		manes[8] = "Ferrari";  
		manes[9] = "Ford";  
	}

	public IntialUserLoginDTO getUser(String user, String password)
			throws Exception {
		IntialUserLoginDTO ini = new IntialUserLoginDTO();
		UserDTO usuario = new UserDTO();
		ArrayList<OptionMenuDTO> listMenu = new ArrayList<OptionMenuDTO>();
		ArrayList<ProfileDTO> listProfile = new ArrayList<ProfileDTO>();
		
		try {
			OptionMenuDTO menu = new OptionMenuDTO();			
			menu.setId(1L);
			menu.setOption("Administrar Productos");
			menu.setUrl("manageProduct.xhtml");
			listMenu.add(menu);
			
			OptionMenuDTO menu1 = new OptionMenuDTO();
			menu1.setId(2L);
			menu1.setOption("Consultar Productos");
			menu1.setUrl("getsProduct.xhtml");
			listMenu.add(menu1);
			
			OptionMenuDTO menu2 = new OptionMenuDTO();
			menu2.setId(3L);
			menu2.setOption("Consultar Ranking Productos");
			menu2.setUrl("getsProductRanking.xhtml");
			listMenu.add(menu2);
			
			OptionMenuDTO menu3 = new OptionMenuDTO();
			menu3.setId(4L);
			menu3.setOption("Consultar Ranking Categorias");
			menu3.setUrl("getsProductRanking.xhtml");
			listMenu.add(menu3);
			
			OptionMenuDTO menu4 = new OptionMenuDTO();
			menu4.setId(5L);
			menu4.setOption("Administrar Campañas");
			menu4.setUrl("manageCampaing.xhtml");
			listMenu.add(menu4);
			
			OptionMenuDTO menu5 = new OptionMenuDTO();
			menu5.setId(6L);
			menu5.setOption("Administrar Ordenes");
			menu5.setUrl("manageOrder.xhtml");
			listMenu.add(menu5);
			
			OptionMenuDTO menu6 = new OptionMenuDTO();
			menu6.setId(7L);
			menu6.setOption("Consultar Ordenes");
			menu6.setUrl("getsOrder.xhtml");
			listMenu.add(menu6);
			
			OptionMenuDTO menu7 = new OptionMenuDTO();
			menu7.setId(8L);
			menu7.setOption("Consultar Ranking Ordenes Abiertas");
			menu7.setUrl("getsOrderRankingOpen.xhtml");
			listMenu.add(menu7);
			
			OptionMenuDTO menu8 = new OptionMenuDTO();
			menu8.setId(9L);
			menu8.setOption("Consultar Ranking Ordenes Cerradas");
			menu8.setUrl("getsOrderRankingClose.xhtml");
			listMenu.add(menu8);
			
			OptionMenuDTO menu9 = new OptionMenuDTO();
			menu9.setId(10L);
			menu9.setOption("Actualizar Estado Orden");
			menu9.setUrl("updateStateOrder.xhtml");
			listMenu.add(menu9);
			
			OptionMenuDTO menu10 = new OptionMenuDTO();
			menu10.setId(11L);
			menu10.setOption("Administrar Clientes");
			menu10.setUrl("manageClient.xhtml");
			listMenu.add(menu10);
			
			OptionMenuDTO menu11 = new OptionMenuDTO();
			menu11.setId(12L);
			menu11.setOption("Consultar Clientes");
			menu11.setUrl("getClient.xhtml");
			listMenu.add(menu11);
			
			OptionMenuDTO menu12 = new OptionMenuDTO();
			menu12.setId(13L);
			menu12.setOption("Consultar Ranking Clientes");
			menu12.setUrl("getClientRanking.xhtml");			
			listMenu.add(menu12);
			
			/*perfiles*/
			ProfileDTO pf1 = new ProfileDTO();
			pf1.setId(1L);
			pf1.setName("PRODUCTOS_CONSULTA");
			pf1.setStatus(StatusEnum.ENABLED);
			listProfile.add(pf1);
			
			ProfileDTO pf2 = new ProfileDTO();
			pf2.setId(1L);
			pf2.setName("PRODUCTOS_ADMON");
			pf2.setStatus(StatusEnum.ENABLED);
			listProfile.add(pf2);
			
			ProfileDTO pf3 = new ProfileDTO();
			pf3.setId(1L);
			pf3.setName("CAMPANNAS");
			pf3.setStatus(StatusEnum.ENABLED);
			listProfile.add(pf3);
			
			ProfileDTO pf4 = new ProfileDTO();
			pf4.setId(1L);
			pf4.setName("ORDENES_CONSULTA");
			pf4.setStatus(StatusEnum.ENABLED);
			listProfile.add(pf4);
			
			ProfileDTO pf5 = new ProfileDTO();
			pf5.setId(1L);
			pf5.setName("ORDENES_ADMON");
			pf5.setStatus(StatusEnum.ENABLED);
			listProfile.add(pf5);
			
			ProfileDTO pf6 = new ProfileDTO();
			pf6.setId(1L);
			pf6.setName("CLIENTES_CONSULTA");
			pf6.setStatus(StatusEnum.ENABLED);
			listProfile.add(pf6);
			
			ProfileDTO pf7 = new ProfileDTO();
			pf7.setId(1L);
			pf7.setName("CLIENTES_ADMON");
			pf7.setStatus(StatusEnum.ENABLED);
			listProfile.add(pf7);
			
			
			usuario.setId(1L);
			usuario.setName(user);
			usuario.setPassword(password);
			usuario.setSurname(user);
			usuario.setEmail(user);
			usuario.setStatus(StatusEnum.ENABLED);
			usuario.setListamenu(listMenu);
			usuario.setProfiles(listProfile);
			ini.setUser(usuario);
			ini.setTgt(null);
			ini.setServiceTicket(null);
			

		} catch (Exception ex) {
			System.out.println("ERROR DAO: " + ex.getMessage());
			throw new Exception(ex.getMessage());
		}
		return ini;
	}
	
	public TableResultDTO<ProductDTO> getProducts(){
		
		List<ProductDTO> productos;
		TableResultDTO<ProductDTO> result = new TableResultDTO<ProductDTO>();
		
		productos = new ArrayList<ProductDTO>();
		
		populateRandomProducts(productos, 50); 
		
		result.setResult(productos);
		result.setTotalOfRecords(productos.size());		
		return result;
		
	}
	
	private String getRandomNames() {  
        return this.manes[(int) (Math.random() * 10)];  
    }
	
	private String getRandomDescription() {  	
        return this.descriptions[(int) (Math.random() * 10)];  
    } 
	
	private Long getRandomLong() {  
        return (long) (Math.random() * 50 + 12);  
    } 
	
	private Double getRandomDouble() {  
        return (double) (Math.random() * 50 + 13);  
    } 
	
	private void populateRandomProducts(List<ProductDTO> list, int size) {  
        for(int i = 0 ; i < size ; i++){
        	ProductDTO prd = new ProductDTO();
        	prd.setId(getRandomLong());
        	prd.setProdId(getRandomLong());
        	prd.setName(getRandomNames());
        	prd.setDescription(getRandomDescription());
        	prd.setCategory(ProductCategoryEnum.CAT1);
        	prd.setProducer(ProducerTypeEnum.SONY);
        	prd.setPrice(getRandomDouble());
        	list.add(prd);
        }             
    } 

	
	public ProductDTO createProduct(ProductDTO product){
		
		System.out.println("getProdId " + product.getProdId());
    	System.out.println("getName " + product.getName());
    	System.out.println("getDescription " + product.getDescription());
    	System.out.println("getPrice " + product.getPrice());
    	System.out.println("getCategory " + product.getCategory());
    	System.out.println("getProducer " + product.getProducer());
    	System.out.println("getImage_full_bytes " + product.getImage_full_bytes());
    	System.out.println("getImage_thumb_bytes " + product.getImage_thumb_bytes());
    	
    	product.setId(1L);
    	
		return product;
	}
}
