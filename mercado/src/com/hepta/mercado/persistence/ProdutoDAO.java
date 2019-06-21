package com.hepta.mercado.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.hepta.mercado.entity.Fabricante;
import com.hepta.mercado.entity.Produto;

public class ProdutoDAO {

	public void save(Produto produto) throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		try {
			em.getTransaction().begin();			
			em.persist(produto.getFabricante());
			em.persist(produto);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
	}

	public Produto update(Produto produto) throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		Produto produtoAtualizado = null;
		Fabricante fabricanteAtualizado = null;
		try {
			em.getTransaction().begin();
			produtoAtualizado = em.merge(produto);
			fabricanteAtualizado = em.merge(produto.getFabricante());
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
		return produtoAtualizado;
	}

	public void delete(Integer id) throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			Produto produto = em.find(Produto.class, id);
			em.remove(produto);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}

	}

	public Produto find(Integer id) throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		Produto produto = null;
		try {
			produto = em.find(Produto.class, id);
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
		return produto;
	}

	@SuppressWarnings("unchecked")
	public List<Produto> getAll() throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		List<Produto> produtos = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Produto");
			produtos = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
		return produtos;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getNextIdProduto() throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		int nextId = 0; 
		try {
			Query query = em.createQuery("SELECT MAX(id) + 1 FROM Produto", Integer.class);
			nextId = (Integer) query.getSingleResult();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
		return nextId;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getNextIdFabricante() throws Exception {
		EntityManager em = HibernateUtil.getEntityManager();
		Integer nextId = 0; 
		try {
			Query query = em.createQuery("SELECT MAX(id) + 1 FROM Fabricante", Integer.class);
			nextId = (Integer) query.getSingleResult();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			em.close();
		}
		return nextId;
	}
	
}
