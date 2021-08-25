package com.rakuten.training.controller;

import java.util.ArrayList;
//import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rakuten.training.exception.ResourceNotFoundException;
import com.rakuten.training.model.Tutorial;
import com.rakuten.training.repository.TutorialRepository;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class TutorialController {
	
	@Autowired
	 private TutorialRepository tutorialRepository;
	
	//get all tutorial
	@GetMapping("/tutorials")	
	 public List<Tutorial> getAllTutorial()
	 {
		 return tutorialRepository.findAll();
	 }
	//get all tutorials by title
	@GetMapping("/tutorialss")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
		try {
			List<Tutorial> tutorials = new ArrayList<Tutorial>();

			if (title == null)
				tutorialRepository.findAll().forEach(tutorials::add);
			else
				tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 
	//create tutorial
	@PostMapping("/tutorials")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial )
	{
		try {
		Tutorial localtutorial=new Tutorial();
		localtutorial.setTitle(tutorial.getTitle());
		localtutorial.setDescription(tutorial.getDescription());
		localtutorial.setStatus(tutorial.getStatus());
		tutorialRepository.save(localtutorial);
		return new ResponseEntity<>(localtutorial,HttpStatus.CREATED);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	// get tutorial by id
	@GetMapping("/tutorials/{id}")	
	 public ResponseEntity<Tutorial> getTutorial(@PathVariable Long id)
	 {
		
		 Optional<Tutorial> tutorial= tutorialRepository.findById(id);
		 if(tutorial.isPresent())
		 {
			 return new ResponseEntity<>(tutorial.get(),HttpStatus.OK);
		 }
		 else
		 {
			 return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		 }
		
	 }
	
	// update tutorial
	
	@PutMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> updateTutorial(@PathVariable Long id,@RequestBody Tutorial tutorialdetails)
	{
		Tutorial tutorial= tutorialRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Tutorial not found  with id: "+ id));
		tutorial.setTitle(tutorialdetails.getTitle());
		tutorial.setDescription(tutorialdetails.getDescription());
		tutorial.setStatus(tutorialdetails.getStatus());
		
		Tutorial updatetutorial=tutorialRepository.save(tutorial);
		
		return ResponseEntity.ok(updatetutorial);
	}
	//delete tutorial
	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable Long id)
	{
		try {
			tutorialRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//delete all
	@DeleteMapping("/tutorials")
	public ResponseEntity<Map<String,Boolean>> deleteAllTutorial()
	{
	    tutorialRepository.deleteAll();
		Map<String,Boolean> response=new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	


}
