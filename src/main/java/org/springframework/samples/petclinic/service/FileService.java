package org.springframework.samples.petclinic.service;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
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
	  
	  public void imageCrop(String path,FileService fileService) throws IOException {
			
			File imageFile = new File("src/main/resources/static/" + path);
			BufferedImage init = ImageIO.read(imageFile);
			BufferedImage bi = new BufferedImage(init.getWidth(), init.getHeight(),BufferedImage.TYPE_INT_RGB);
			bi.getGraphics().drawImage(init, 0, 0, null);
			
			
			int h = bi.getHeight();
			int w = bi.getWidth();
			  
			  if(h>w) {
				  int dif = h-w;
				  bi = bi.getSubimage(0, dif/2, w, w);
			  }
				 
			  else if(w>h) {
				  int dif = w-h;
				  bi = bi.getSubimage(dif/2, 0, h, h);
			  }
			  
			  fileService.delete(Paths.get("src/main/resources/static/" + path));
			  File pathFile = new File("src/main/resources/static/" + path);
			  ImageIO.write(bi,"jpg", pathFile);	  
			  
		}
	  
	  
}
