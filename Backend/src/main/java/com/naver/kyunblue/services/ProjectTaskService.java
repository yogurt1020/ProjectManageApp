package com.naver.kyunblue.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.kyunblue.domain.BackLog;
import com.naver.kyunblue.domain.Project;
import com.naver.kyunblue.domain.ProjectTask;
import com.naver.kyunblue.exceptions.ProjectNotFoundException;
import com.naver.kyunblue.repositories.BackLogRepository;
import com.naver.kyunblue.repositories.ProjectRepository;
import com.naver.kyunblue.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	@Autowired
	private BackLogRepository backLogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		try {
			BackLog backLog = backLogRepository.findByProjectIdentifier(projectIdentifier);
			projectTask.setBacklog(backLog);
			
			Integer BackLogSequence = backLog.getPTSequence();
			backLog.setPTSequence(++BackLogSequence);
			
			projectTask.setProjectSequence(projectIdentifier + "-" + BackLogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			if(projectTask.getPriority() == null) {
				projectTask.setPriority(3);
			}
			
			if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}
			
			return projectTaskRepository.save(projectTask);			
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}

	}

	public Iterable<ProjectTask> findProjectTasksByPI(String projectIdentifier) {
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
		
		if(project == null) {
			throw new ProjectNotFoundException("Project with ID [" + projectIdentifier + "] is not found");
		}
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
	}
}
