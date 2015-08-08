package com.ensulin.tasks;

import javax.transaction.Transactional;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ensulin.models.dao.PatientDAO;
import com.ensulin.models.dto.OutputObject;
import com.ensulin.models.dto.Patient;
import com.nandu.Utility.EnsulinException;

@RestController
@RequestMapping(value="/patient")
public class PatientController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private PatientDAO patientService;
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired(required=true)
	@Qualifier(value="patientDAO")
	public void setPersonService(PatientDAO ps){
		this.patientService = ps;
	}

	@RequestMapping(value="/isAvailable",method=RequestMethod.GET)
	public @ResponseBody OutputObject checkUserNameAvailability(@RequestParam("uid")String uid){
		OutputObject output = new OutputObject(OutputObject.TYPE_SEARCH, "yes", OutputObject.STATUS_OK);
		try {
			if(!patientService.isUserNameAvailable(uid)){
				output.setMessage("no");
			}
		} catch (EnsulinException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			output.setStatus(OutputObject.STATUS_FAIL);
			output.setMessage("retry");
		}
		return output;
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody OutputObject savePatientData(String postData) throws Exception{
		Patient patient = null;
		try{
			if((patient = patientService.save(postData))!=null)
				return new OutputObject(OutputObject.TYPE_CREATE, 
						patient, OutputObject.STATUS_OK);
		}
		catch(ConstraintViolationException e){
			System.out.println("Exception is "+e.getMessage());
			return new OutputObject(OutputObject.TYPE_CREATE, 
					"UserName already exists", OutputObject.STATUS_FAIL);
		}
		catch (HibernateException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			return new OutputObject(OutputObject.TYPE_CREATE, 
					e.getMessage(), OutputObject.STATUS_FAIL);
		}
		catch(Exception e){
			return new OutputObject(OutputObject.TYPE_CREATE, 
					e.getMessage(), OutputObject.STATUS_FAIL);
		}
		return new OutputObject(OutputObject.TYPE_CREATE, 
				"Unknown Error Occured, Verify JSON object structure", OutputObject.STATUS_FAIL);
	}
	
	@RequestMapping(value="/search/{key}/{id}",method=RequestMethod.GET,headers = {"Accept=text/xml, application/json"})
	public @ResponseBody OutputObject searchPatientById(@PathVariable String id, @PathVariable String key){
		Patient p = null;
		switch(key){
		case "uid":p = patientService.getById(id);
			break;
		case "email":p = patientService.getByEmail(id);
			break;
		case "phone":p = patientService.getByMobile(id);
			break;
			default:
		}
		if(p !=null)
			return new OutputObject(OutputObject.TYPE_SEARCH, 
					p, OutputObject.STATUS_OK);
		return new OutputObject(OutputObject.TYPE_SEARCH, 
				"", OutputObject.STATUS_FAIL);
	}
	
}
