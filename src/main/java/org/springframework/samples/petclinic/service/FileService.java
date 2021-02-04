package org.springframework.samples.petclinic.service;

import java.io.IOException;
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
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService implements FileRepository{

	  @Override
	  public void saveFile(MultipartFile file,Path path,String diferenciador) {
	    try {
	      Files.copy(file.getInputStream(), path.resolve(diferenciador));
	    } catch (Exception e) {
	      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
	    }
	  }

	  
	  @Override
	  public Resource load(String filename,Path path) {
	    try {
	      Path file = path.resolve(filename);
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
	  
	  @Override
	  public void delete(Path path) throws IOException {
	    FileSystemUtils.deleteRecursively(path);
	  }
	  
	  
}
