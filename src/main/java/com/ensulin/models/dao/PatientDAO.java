package com.ensulin.models.dao;

import java.util.List;

import com.ensulin.models.dto.Patient;
import com.nandu.Utility.EnsulinException;

public interface PatientDAO {

	public Patient save(String patientData);

	public boolean delete(Patient _patient);

	public List<Patient> getAll();

	public Patient getById(String id);
	
	public Patient getByEmail(String id);
	
	public Patient getByMobile(String id);
	
	public boolean isUserNameAvailable(String uid) throws EnsulinException;
}
