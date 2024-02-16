package org.jsp.todoapp.dao;

import java.util.List;

//DAO(Hibernate)
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.jsp.todoapp.dto.Task;
import org.jsp.todoapp.dto.User;

public class TodoDao {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
	EntityManager manager = factory.createEntityManager();
	EntityTransaction transaction = manager.getTransaction();

	public void saveUser(User user) {
		transaction.begin();
		manager.persist(user);
		transaction.commit();
	}
	
	public List<User> findUserByEmail(String email) {
		return manager.createQuery("select x from User x where email=?1").setParameter(1, email).getResultList();
	}

	public List<User> findUserByMobile(long mobile) {
		return manager.createQuery("select x from User x where mobile=?1").setParameter(1, mobile).getResultList();
	}
	
	public void saveTask(Task task) {
		transaction.begin();
		manager.persist(task);
		transaction.commit();
	}
	
	public List<Task> fetchTasksByUserId(int userId){
		return manager.createQuery("select x from Task x where user_id=?1").setParameter(1, userId).getResultList();
	}
	
	public Task findById(int id) {
		return manager.find(Task.class, id);
	}

	public void updateTask(Task task) {
		transaction.begin();
		manager.merge(task);
		transaction.commit();
	}

	public void deleteTask(Task task) {
		transaction.begin();
		manager.remove(task);
		transaction.commit();
	}
}
