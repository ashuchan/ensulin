package com.ensulin.models.mysql.dao;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ensulin.models.dao.DoctorDAO;
import com.ensulin.models.dto.Doctor;
import com.ensulin.models.dto.DoctorSpeciality;

@Repository
public class DoctorDAOImpl implements DoctorDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(PatientDAOImpl.class);
	private SessionFactory sessionFactory;
	private static ObjectMapper mapper = new ObjectMapper();
	
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public Doctor save(String _doctor) {
		Doctor doctor = null;
		try {
			doctor = mapper.readValue(_doctor, Doctor.class);
			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.persist(doctor);
			tx.commit();
			session.close();
			return doctor;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doctor;
	}

	@Override
	public boolean delete(Doctor _doctor) {
		try {
			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.delete(_doctor);
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Doctor> getAll() {
		try {
			Session session = this.sessionFactory.openSession();
			List<Doctor> personList = session.createQuery("from Doctor").list();
			session.close();
			return personList;
		} catch (HibernateException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Doctor getDoctorById(String uid) {
		Session session = null;
		System.out.println("Searching doctor with ID as "+ uid);
		logger.info("Searching doctor with ID as "+ uid);
		try{
			session = this.sessionFactory.openSession();
			Criteria cr = session.createCriteria(Doctor.class);
			cr.add(Restrictions.eq("username", uid));
			List results = cr.list();
			if(results.size()>0){
				return (Doctor)results.get(0);
			}
		}
		finally{
			session.close();
		}
		return null;
	}

	@Override
	public List<Doctor> getDoctorBySpeciality(int id) {
		Session session = null;
		logger.info("Searching doctor with speciality as "+ id);
		try{
			session = this.sessionFactory.openSession();
			Query query = session.createQuery("Select DS.doctor from DoctorSpeciality as DS where DS.speciality.id=:id");
			query.setParameter("id", id);
			List<Doctor> results = query.list();
			if(results.size()>0){
				return results;
			}
		}
		finally{
			session.close();
		}
		
		return null;
	}

}
