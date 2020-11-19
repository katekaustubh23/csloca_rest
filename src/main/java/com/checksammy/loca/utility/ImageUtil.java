package com.checksammy.loca.utility;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	public static void compress(File file, String resizeType) throws IOException {
		
		logger.debug("compressing file: "+file + "\n");
		
		logger.debug("Original file size: " + file.length());
		/*
		 * if(originalImageFile.length()<=500000) return;
		 */
		
		InputStream is = new FileInputStream(file);
		
		BufferedImage image = ImageIO.read(is);
		if(resizeType.equalsIgnoreCase("CARD"))
			image = resizeToCard(image);
		else
			image = resizeToThumbnail(image);
		
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(file.getName()));

		if (!writers.hasNext()){
			throw new IllegalStateException("No writers found");
		}

		ImageWriter writer = writers.next();
		OutputStream os = new FileOutputStream(file);
		ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		writer.setOutput(ios);
		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(0.5f);
		writer.write(null, new IIOImage(image, null, null), param);
		is.close();
		os.close();
		ios.close();
		writer.dispose();
	}	
	
	private static BufferedImage resizeToThumbnail(BufferedImage originalImage){
		
		int height = originalImage.getHeight()/3;
		int width = originalImage.getWidth()/3;
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
			
		return resizedImage;
	}
	
	private static BufferedImage resizeToCard(BufferedImage originalImage){
		
		int height = originalImage.getHeight()/2;
		int width = originalImage.getWidth()/2;
		
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
			
		return resizedImage;
	}
}
