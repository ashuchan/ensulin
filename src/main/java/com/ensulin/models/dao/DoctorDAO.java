package com.ensulin.models.dao;

import java.util.List;

import com.ensulin.models.dto.Doctor;
import com.ensulin.models.dto.DoctorSpeciality;

public interface DoctorDAO {

	public Doctor save(String _doctor);

	public boolean delete(Doctor _doctor);

	public List<Doctor> getAll();
	
	public Doctor getDoctorById(String uid);
	
	public List<Doctor> getDoctorBySpeciality(int id);
}
