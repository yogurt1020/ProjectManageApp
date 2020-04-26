package com.naver.kyunblue.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.kyunblue.domain.Project;
import com.naver.kyunblue.exceptions.ProjectIdException;
import com.naver.kyunblue.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try {
        	project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());;
        	return projectRepository.save(project);
        }catch (Exception e) {
			throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
		}
    }

    public Project findProjectByIdentifier(String projectId) {
	    Project project = projectRepository.findByProjectIdentifier(projectId);
	    
	    if(project == null) {
	    	throw new ProjectIdException("Project does not exist");
	    }
    	
	    return project;
    }
}