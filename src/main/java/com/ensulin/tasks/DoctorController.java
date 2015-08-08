package com.ensulin.tasks;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ensulin.models.dao.DoctorDAO;
import com.ensulin.models.dto.Doctor;
import com.ensulin.models.dto.DoctorSpeciality;
import com.ensulin.models.dto.OutputObject;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
	private DoctorDAO doctorService;
    
    @Autowired(required=true)
    @Qualifier(value="doctorDAO")
    public void setPersonService(DoctorDAO ps){
        this.doctorService = ps;
    }

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody OutputObject create(String postData){
    	OutputObject obj = new OutputObject(OutputObject.TYPE_CREATE, "", OutputObject.STATUS_FAIL);
    	Doctor doc = null;
    	if((doc=doctorService.save(postData))!=null){
    		obj.setMessage(doc);
 		   	obj.setStatus(OutputObject.STATUS_OK);
    	}
    	return obj;
    }
    
	/**
	 * Simply selects the home view to render by returning its name.
	 */
   @RequestMapping(value="/{id}",method=RequestMethod.GET)
	public @ResponseBody OutputObject doctorHome(@PathVariable String id){
	   OutputObject obj = new OutputObject(OutputObject.TYPE_SEARCH, "", OutputObject.STATUS_FAIL);
	   Doctor doc = null;
	   if((doc=doctorService.getDoctorById(id))!=null){
		   obj.setMessage(doc);
		   obj.setStatus(OutputObject.STATUS_OK);
	   }
	   return obj;
	}
   
   @RequestMapping(value="/search")
   public @ResponseBody OutputObject searchDoctor(@RequestParam("speciality") int speciality){
	   
	   List<Doctor> docList = null;
	   OutputObject obj = new OutputObject(OutputObject.TYPE_SEARCH, "", OutputObject.STATUS_FAIL);
	   if((docList=doctorService.getDoctorBySpeciality(speciality))!=null){
		   obj.setMessage(docList);
		   obj.setStatus(OutputObject.STATUS_OK);
	   }
	   return obj;
   }
	
	@RequestMapping(method = RequestMethod.GET, params="new")
	public String displayPtientRegistration(Model model) {

		model.addAttribute("doctor",new Doctor());

		return "newDoctor";
	}
	
/*	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public String savePatientData(Doctor doctor){
		logger.info("Adding patient "+ doctor.toString());
		if(doctorService.save(doctor))
			return "redirect:/patient/"+doctor.getUsername();
		
		return "error";
	}*/

}
