package org.springframework.samples.petclinic.repository;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileRepository {

	public void saveFile(MultipartFile file,Path path,String diferenciador);

	public Resource load(String filename,Path path);

}
