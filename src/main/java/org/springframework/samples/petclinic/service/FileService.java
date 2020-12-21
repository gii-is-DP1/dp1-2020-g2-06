package org.springframework.samples.petclinic.service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.ConstraintViolationException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.samples.petclinic.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService implements FileRepository{
	
	private final Path rootZip = Paths.get("uploads");
	private final Path rootImage = Paths.get("CodeUsImages");
	public static Integer id= 0;


	  @Override
	  public void saveZip(MultipartFile file) {
	    try {
	      Files.copy(file.getInputStream(), this.rootZip.resolve(id.toString() + file.getOriginalFilename()));
	      id++;
	    } catch (Exception e) {
	      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
	    }
	  }
	  
	  @Override
	  public void saveImage(MultipartFile file) {
	    try {
	      Files.copy(file.getInputStream(), this.rootImage.resolve(id.toString() + file.getOriginalFilename()));
	      id++;
	    } catch (Exception e) {
	      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
	    }
	  }

	  @Override
	  public Resource load(String filename) {
	    try {
	      Path file = rootImage.resolve(filename);
	      Resource resource = new UrlResource(file.toUri());

	      if (resource.exists() || resource.isReadable()) {
	        return resource;
	      } else {
	        throw new RuntimeException("Could not read the file!");
	      }
	    } catch (MalformedURLException e) {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
	  }
}
