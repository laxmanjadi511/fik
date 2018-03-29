package com.fik.springmvc.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fik.springmvc.dao.CustomerDAO;
import com.fik.springmvc.domain.FIKResponse;
import com.fik.springmvc.domain.FIKResponseObject;
import com.fik.springmvc.model.Customer;
import com.fik.springmvc.service.TrainerService;

@RestController
public class FIKRestController {
	
	 private final Logger logger = LoggerFactory.getLogger(FIKRestController.class);
	 
	 private static String UPLOADED_FOLDER = "C://temp//";
	 
	@Autowired
	TrainerService service;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	CustomerDAO customerDAO;

	
	@RequestMapping("/")
	public String welcome() {//
		return "Welcome to FIK E-commerce private limited.";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public FIKResponseObject registerTrainer(@RequestBody Trainer trainer) {
		
		FIKResponseObject object = new FIKResponseObject();
		FIKResponse response = null;
		
			com.fik.springmvc.model.Trainer destTrainer = new com.fik.springmvc.model.Trainer();
			BeanUtils.copyProperties(trainer, destTrainer);
			response = service.saveTrainer(destTrainer);
			object.setResponse(response);
			return object;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public FIKResponseObject message(@RequestBody LoginRequest login) {
		
		FIKResponseObject object = new FIKResponseObject();
		FIKResponse response = null;
		
			com.fik.springmvc.model.LoginRequest destLogin= new com.fik.springmvc.model.LoginRequest();
			BeanUtils.copyProperties(login, destLogin);
			response = service.login(destLogin);
			object.setResponse(response);
			return object;
	}
	
    @PostMapping("/api/upload/multi")
    public ResponseEntity<?> uploadFileMulti(
            @RequestParam("trainer-resume") MultipartFile uploadfiles) {

        logger.debug("Multiple file upload!");

        // Get file name
        String uploadedFileName = null;/* = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));*/

   /*     if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }*/

        try {

            saveUploadedFiles(Arrays.asList(uploadfiles));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - "
                + uploadedFileName, HttpStatus.OK);

    }

    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        }

    }
	
/*	
	@RequestMapping("/hello/{player}")
	public FIKResponse register(@ModelAttribute Trainer trainerRegisterDetails) {

//		Message msg = new Message(player, "Hello " + player);
//		return msg;
	}*/

	/*
	 * This method will list all existing trainers.
	 */
/*	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listTrainers(ModelMap model) {

		List<Trainer> trainers = null;service.findAllTrainers();
		model.addAttribute("trainers", trainers);
		return "alltrainers";
	}*/

	/*
	 * This method will provide the medium to add a new trainer.
	 
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newTrainer(ModelMap model) {
		Trainer trainer = new Trainer();
		model.addAttribute("trainer", trainer);
		model.addAttribute("edit", false);
		return "registration";
	}

	
	 * This method will be called on form submission, handling POST request for
	 * saving trainer in database. It also validates the user input
	 
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveTrainer(@Valid Trainer trainer, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		
		 * Preferred way to achieve uniqueness of field [ssn] should be
		 * implementing custom @Unique annotation and applying it on field [ssn]
		 * of Model class [Trainer].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you
		 * can fill custom errors outside the validation framework as well while
		 * still using internationalized messages.
		 * 
		 
		if (!service.isTrainerSsnUnique(trainer.getId(), trainer.getSsn())) {
			FieldError ssnError = new FieldError("trainer", "ssn",
					messageSource.getMessage("non.unique.ssn", new String[] { trainer.getSsn() }, Locale.getDefault()));
			result.addError(ssnError);
			return "registration";
		}

		service.saveTrainer(trainer);

		model.addAttribute("success", "Trainer " + trainer.getName() + " registered successfully");
		return "success";
	}

	
	 * This method will provide the medium to update an existing trainer.
	 
	@RequestMapping(value = { "/edit-{ssn}-trainer" }, method = RequestMethod.GET)
	public String editTrainer(@PathVariable String ssn, ModelMap model) {
		Trainer trainer = service.findTrainerBySsn(ssn);
		model.addAttribute("trainer", trainer);
		model.addAttribute("edit", true);
		return "registration";
	}

	
	 * This method will be called on form submission, handling POST request for
	 * updating trainer in database. It also validates the user input
	 
	@RequestMapping(value = { "/edit-{ssn}-trainer" }, method = RequestMethod.POST)
	public String updateTrainer(@Valid Trainer trainer, BindingResult result, ModelMap model,
			@PathVariable String ssn) {

		if (result.hasErrors()) {
			return "registration";
		}

		if (!service.isTrainerSsnUnique(trainer.getId(), trainer.getSsn())) {
			FieldError ssnError = new FieldError("trainer", "ssn",
					messageSource.getMessage("non.unique.ssn", new String[] { trainer.getSsn() }, Locale.getDefault()));
			result.addError(ssnError);
			return "registration";
		}

		service.updateTrainer(trainer);

		model.addAttribute("success", "Trainer " + trainer.getName() + " updated successfully");
		return "success";
	}

	
	 * This method will delete an trainer by it's SSN value.
	 
	@RequestMapping(value = { "/delete-{ssn}-trainer" }, method = RequestMethod.GET)
	public String deleteTrainer(@PathVariable String ssn) {
		service.deleteTrainerBySsn(ssn);
		return "redirect:/list";
	}*/
	
	
	@GetMapping("/customers")
	public List getCustomers(){
		return customerDAO.list();
	}
	
	@GetMapping("/customers/{id}")
	public ResponseEntity getcustomer(@PathVariable("id") Long id){
		Customer customer=customerDAO.get(id);
		if (customer==null) {
			return new ResponseEntity("No Customer found for ID" + id,HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity(customer,HttpStatus.OK);
	}
	
	@PostMapping(value="/customers")
	public ResponseEntity creatcustomers(@RequestBody Customer customer)
	{
		customerDAO.create(customer);
		return new ResponseEntity(customer,HttpStatus.OK);
	}
	
	@DeleteMapping("/customers/{id}")
	public ResponseEntity deletecustomer(@PathVariable Long id){
		if (null== customerDAO.delete(id)) {
			
			return new ResponseEntity("No Customer found for ID" + id,HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity(id,HttpStatus.OK);
	}
	
	@PutMapping("/customers/{id}")
	public ResponseEntity updatecustomer(@PathVariable Long id,@RequestBody Customer customer){
		customer = customerDAO.update(id, customer);

		if (null == customer) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(customer, HttpStatus.OK);
	}


}
