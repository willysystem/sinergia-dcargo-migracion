package com.sinergia.dcargo.migracion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.sinergia.dcargo.client.shared.Cliente;
import com.sinergia.dcargo.client.shared.Conocimiento;
import com.sinergia.dcargo.client.shared.Guia;
import com.sinergia.dcargo.client.shared.Usuario;

public class UtilORM {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("sinergia-dcargo-model" );
	private static EntityManager em = emf.createEntityManager();
	
	@SuppressWarnings("unchecked")
	
	public static <E> E  buscarEntityPorAtributo(String atributo, Object valor, Class<E> clazz, EntityManager entityManager){
		if(valor == null){
			return null;
		}
		String q = "SELECT e FROM " + clazz.getSimpleName() + " e WHERE e." + atributo + " = :" + atributo;
		Query query = entityManager.createQuery(q);
		query.setParameter(atributo, valor);
		List<E> e = (List<E>) query.getResultList();
		if(e.size() == 0){
			return null;
		}
		return e.get(0);
	}
	
	public static <E> E  buscarEntityPorAtributo(String atributo, Object valor, Class<E> clazz) {
		return buscarEntityPorAtributo(atributo, valor, clazz, em);
	}
	
	public static void guardar(Object entity) {
		em.persist(entity);
	}
	
	public static <E> E actualizar(E entity) {
		return em.merge(entity);
	}
	
	public static <E> List<E> buscarTodos(Class<E> clazz, EntityManager emLocal) {
		 Query query = emLocal.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e");
		 @SuppressWarnings("unchecked")
		List<E> list = query.getResultList(); 
		return list;
	}
	
	public static Conocimiento buscarPorNroConocimiento(Integer nroConocimiento, List<Conocimiento> list) {
		for (Conocimiento conocimiento : list) {
			if(conocimiento.getNroConocimiento().equals(nroConocimiento)){
				return conocimiento;
			}
		}
		return null;
	}
	
	public static Cliente buscarPorCodigoCliente(Integer codigo, List<Cliente> list) {
		for (Cliente cliente : list) {
			if(cliente.getCodigo().equals(codigo)){
				return cliente;
			}
		}
		return null;
	}
	
	public static Usuario buscarPorCodigoUsuario(String codigo, List<Usuario> list) {
		for (Usuario usuario :list) {
			if(usuario.getNombreUsuario().equals(codigo)) {
				return usuario;
			}
		}
		return null;
	}
	
	public static Guia buscarPorNroGuia(Integer nroGuia, List<Guia> list) {
		for (Guia guia :list) {
			if(guia.getNroGuia() == nroGuia) {
				return guia;
			}
		}
		return null;
	}
	
	public static <E> E buscarPorAtributoEnCache(String metodo, Object valor, List<E> list) {
		try {
			for (E e : list) {
				Method method = e.getClass().getMethod(metodo);
				Object result = method.invoke(e);
				if(result.equals(valor)){
					return e;
				}
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public EntityManager getEntityManager() {
		
		return em;
	} 
	
}
