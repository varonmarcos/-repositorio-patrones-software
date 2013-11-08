package org.kallsonnys.security.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.kallsonnys.oms.dto.UserDTO;
import org.kallsonnys.security.dao.LDAPUserDAO;

public class SecurityTest {

	
	public static void main(String[] args) {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Pablo Arturo");
		userDTO.setSurname("Tirado Valencia");
		userDTO.setEmail("pabloarturot@kallsonnys.com");
		userDTO.setPassword("pa1234$");
		
		try {
			String passwordHash = SecurityUtils.createHash(userDTO.getPassword());
			userDTO.setPassword(passwordHash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		LDAPUserDAO ldapUserDAO  = new LDAPUserDAO();
		
		ldapUserDAO.createUser(userDTO);
		
		UserDTO found = ldapUserDAO.getUser(userDTO);
		
		try {
			boolean validatePassword = SecurityUtils.validatePassword(userDTO.getPassword(), found.getPassword());
			if(validatePassword){
				System.out.println("EL PASSWORD HA SIDO VALIDADO :)");
			}else{
				System.out.println("EL PASSWORD NO HA SIDO VALIDADO :(");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
}
