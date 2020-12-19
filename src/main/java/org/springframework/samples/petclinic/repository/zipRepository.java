package org.springframework.samples.petclinic.repository;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface zipRepository {

	public void save(MultipartFile file);

	public Resource load(String filename);

}
