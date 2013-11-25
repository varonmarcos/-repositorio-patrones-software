package org.kallsonnys.security.dao;

import static org.kallsonys.oms.commons.Exception.ErrorCodesEnum.ENTITY_NOT_FOUND;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.kallsonys.oms.commons.Exception.OMSException;
import org.kallsonys.oms.entities.security.OptionMenu;
import org.kallsonys.oms.entities.security.Profile;
import org.kallsonys.oms.entities.security.User;

public class UserDAO {

	private EntityManager em;

	public UserDAO() {

	}

	public User getUser(Long id) {
		if (id == null)
			return null;
		final User user = em.find(User.class, id);
		if (user == null)
			throw new OMSException(ENTITY_NOT_FOUND.getCode(),
					ENTITY_NOT_FOUND.getMsg());
		return user;
	}

	public User getUser(String email) {
		if (email == null)
			return null;
		try {
			final User user = (User) em
					.createQuery(
							"SELECT u FROM User u WHERE u.email = :email")
					.setParameter("email", email).getSingleResult();

			return user;
		} catch (NoResultException e) {
			throw new OMSException(ENTITY_NOT_FOUND.getCode(),
					ENTITY_NOT_FOUND.getMsg());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Profile> getUserProfiles(Long id) {
		if (id == null)
			return null;

		List<Profile> resultList = em
				.createQuery("SELECT prof FROM User u JOIN u.profiles prof WHERE u.id = :id")
				.setParameter("id", id).getResultList();

		return resultList.size() == 0 || resultList.get(0) == null ? null
				: resultList;
	}

	@SuppressWarnings("unchecked")
	public List<OptionMenu> getOptionMenusByUser(Long id) {
		if (id == null)
			return null;
		List<OptionMenu> resultList = em
				.createQuery(
						"SELECT DISTINCT opm FROM User u JOIN u.profiles prof JOIN prof.optionsMenu opm WHERE u.id = :id")
				.setParameter("id", id).getResultList();
		return resultList.size() == 0 || resultList.get(0) == null ? null
				: resultList;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
