package org.springframework.samples.petclinic.service;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FileServiceTests {
				
		@Autowired
		private FileService fileService;
		
		@Test
		public void shouldSaveFile() throws IOException {
			Path path = Paths.get("TestTxt");
			String name = "test.txt";
			byte[] content = "Hello".getBytes();
			
			MultipartFile file = new MockMultipartFile(name,content);
			
			this.fileService.saveFile(file, path, "test.txt");
			
			byte[] content2 = null;
			Path path2 = Paths.get("TestTxt/test.txt");
			try {
			    content2 = Files.readAllBytes(path2);
			} catch (final IOException e) {
			}
			assertThat(content).isEqualTo(content2);
			this.fileService.delete(Paths.get("TestTxt/test.txt"));
		}
		
		@Test
		public void shouldLoadFile() throws IOException{
			byte[] content = "Hello".getBytes();
			byte[] content2 = Files.readAllBytes(this.fileService.load("testLoad.txt", Paths.get("TestTxt")).getFile().toPath());
			assertThat(content).isEqualTo(content2);
		}
		
		@Test
		public void shouldDeleteFile() throws IOException {
			Path path = Paths.get("TestTxt/testDelete.txt");
			byte[] content = null;
			String name = "testDelete.txt";
			byte[] aux = null;
			try {
			    aux = Files.readAllBytes(path);
			} catch (final IOException e) {
			}
			MultipartFile fichero = new MockMultipartFile(name, aux);
			
			this.fileService.delete(path);
			
			byte[] content2 = null;
			try {
			    content2 = Files.readAllBytes(path);
			} catch (final IOException e) {
			}
			assertThat(content).isEqualTo(content2);
			this.fileService.saveFile(fichero, Paths.get("TestTxt"), "testDelete.txt");
		}
		
		@Test
		public void shouldCropImage() throws IOException {

			// imagen que no es cuadrada (width != height)
			fileService.imageCrop("resources/images/kdfjaodf6dlasd7prueba.png", fileService);
			
			File imageFile2 = new File("src/main/resources/static/resources/images/kdfjaodf6dlasd7prueba.png");
			BufferedImage init2 = ImageIO.read(imageFile2);
			
			// comprobamos que despues de aplicar imageCrop si lo es
			
			boolean res = init2.getHeight() == init2.getWidth();
			assertThat(res).isEqualTo(true);
			
		}
		
}
