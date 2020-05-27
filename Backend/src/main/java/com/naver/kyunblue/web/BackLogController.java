package com.naver.kyunblue.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naver.kyunblue.domain.ProjectTask;
import com.naver.kyunblue.repositories.ProjectTaskRepository;
import com.naver.kyunblue.services.MapValidationErrorService;
import com.naver.kyunblue.services.ProjectTaskService;

@RequestMapping("/api/backlog")
@RestController
@CrossOrigin
public class BackLogController {
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("/{projectIdentifier}")
	public ResponseEntity<?> addProjectTaskToBackLog(@Valid @RequestBody ProjectTask projectTask,
			BindingResult result, @PathVariable String projectIdentifier) {
		
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		
		ProjectTask projectTask1 = projectTaskService.addProjectTask(projectIdentifier, projectTask);
		
		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<?> getProjectBackLog(@PathVariable String projectIdentifier) {
		return new ResponseEntity<Iterable<ProjectTask>>(projectTaskService.findProjectTasksByPI(projectIdentifier), HttpStatus.OK);
	}
	
	@GetMapping("/{projectIdentifier}/{sequence}")
	public ResponseEntity<?> getProjectTask(@PathVariable String projectIdentifier, @PathVariable String sequence) {
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(projectIdentifier, sequence);
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}

	@PatchMapping("/{projectIdentifier}/{sequence}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result, 
												@PathVariable String projectIdentifier, @PathVariable String sequence) {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		
		ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, projectIdentifier, sequence);
		
		return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);
	}

}


