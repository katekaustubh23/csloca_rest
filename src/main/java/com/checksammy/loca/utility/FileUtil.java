/**
 * 
 */
package com.checksammy.loca.utility;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Abhishek Srivastava
 *
 */
public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static File convert(MultipartFile file) throws IOException {

		FileOutputStream fos = null;
		
		File convFile = new File(file.getOriginalFilename());
		String mimetype = file.getContentType();
		String type = mimetype.split("/")[0];
		
		if (type.equals("image")) {
			
			InputStream is = new BufferedInputStream(file.getInputStream());
			
			BufferedImage image = ImageIO.read(is);
			convFile.createNewFile();
			fos = new FileOutputStream(convFile);
			ImageIO.write(image, "jpg", fos);
			is.close();
			logger.debug("Converted file name: "+ convFile.getAbsolutePath());
			return convFile;
		} else {
			convFile.createNewFile();
			fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());

		}
		fos.close();
		return convFile;
	}
	
	public static void copyFileUsingStream(File source, File dest) throws IOException {
		
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
			source.delete();
		}
	}

}
