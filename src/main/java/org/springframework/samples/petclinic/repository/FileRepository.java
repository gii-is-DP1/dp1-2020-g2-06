package org.springframework.samples.petclinic.repository;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileRepository {

	public void saveZip(MultipartFile file);
	
	public void saveImage(MultipartFile file);

	public Resource load(String filename);

}
