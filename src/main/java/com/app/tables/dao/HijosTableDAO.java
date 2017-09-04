package com.app.tables.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.app.domain.Hijos;
import com.app.domain.Usuarios;
import com.app.utils.tables.dao.GenericDaoImpl;

public class HijosTableDAO extends GenericDaoImpl<Usuarios> {

	@PersistenceUnit(unitName = "app")
    private EntityManagerFactory entityManagerFactory;

    
	public Hijos getHijo(Long idHijo) throws SQLException, NamingException {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Hijos hijo = new Hijos();
		hijo = em.find(Hijos.class, idHijo);		
		em.close();
		return hijo;
	}
	
    public List<Map<String, Object>> getHijos(Long idPadre) {
      StringBuilder countQuery = new StringBuilder();
      countQuery.append("select * ");
      countQuery.append(" from public.hijos");
      countQuery.append(" where id_padre ='"+idPadre.toString()+"'");
      List<Object[]> oResultList = em.createNativeQuery(countQuery.toString()).
              getResultList();
      if(oResultList.size()<=0){
    	  return null;
      }
      List<Map<String, Object>> resultlist = new ArrayList<>();
      for (Object[] oResultArray : oResultList) {
          Map<String, Object> oMapResult = new HashMap<>();
          oMapResult.put("idHijo", oResultArray[0]);
          oMapResult.put("IdPadre", oResultArray[1]);
          oMapResult.put("nombres", oResultArray[2]);
          oMapResult.put("apellidos", oResultArray[3]);
          oMapResult.put("edad", oResultArray[4]);
          oMapResult.put("documento", oResultArray[5]);
          oMapResult.put("fechaNacimiento", oResultArray[6]);
          oMapResult.put("sexo", oResultArray[7]);
          resultlist.add(oMapResult);
      }
      return resultlist;
    }
    
	public Hijos crearHijo(Hijos hijos) throws SQLException, NamingException {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(hijos);
		em.getTransaction().commit();
		em.close();
		return hijos;
	}
    
	public String modificarHijo(Hijos hijos) throws SQLException, NamingException {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.find(Hijos.class, hijos.getIdHijo());
		em.merge(hijos);
		em.getTransaction().commit();
		em.close();
		return "OK";
	}
	
	public String eliminarHijo(Long id) {
	    try{
			em = entityManagerFactory.createEntityManager();
	    	em.getTransaction().begin();
	    	Hijos hijos = new Hijos();
	    	hijos = em.find(Hijos.class, id);
			em.remove(hijos);
	    	em.getTransaction().commit();
	    	em.close();
	        return "OK";
	    }catch (PersistenceException pe){
	        pe.printStackTrace();
	        return "ERROR";
	    }
	}
}
