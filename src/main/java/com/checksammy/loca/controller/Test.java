package com.checksammy.loca.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class Test {

	public static void main(String[] args) {
		String fileName="66463464_01142020_090211.xlsx";
		File dir = new File("C:\\Loca\\Reports" + File.separator + fileName);
	
			if (!dir.exists())
				dir.mkdirs();
			Path file = Paths.get(dir.getAbsolutePath());
			try {
				Resource resource= new UrlResource(file.toUri());
			
		System.out.println(resource);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
