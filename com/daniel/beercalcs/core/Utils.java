package com.daniel.beercalcs.core;

import javax.microedition.lcdui.*;
import com.daniel.beercalcs.base.*;

public class Utils {
	
	/**
	  * This methog resizes an image by resampling its pixels
	  * @param src The image to be resized
	  * @return The resized image
	  

	  public Image resizeImage(Image src, int width, int height) {
	      int srcWidth = src.getWidth();
	      int srcHeight = src.getHeight();
	      Image tmp = Image.createImage(width, srcHeight);
	      Graphics g = tmp.getGraphics();
	      int ratio = (srcWidth << 16) / width;
	      int pos = ratio/2;

	      //Horizontal Resize        

	      for (int x = 0; x < width; x++) {
	          g.setClip(x, 0, 1, srcHeight);
	          g.drawImage(src, x - (pos >> 16), 0, Graphics.LEFT | Graphics.TOP);
	          pos += ratio;
	      }

	      Image resizedImage = Image.createImage(width, height);
	      g = resizedImage.getGraphics();
	      ratio = (srcHeight << 16) / height;
	      pos = ratio/2;        

	      //Vertical resize

	      for (int y = 0; y < height; y++) {
	          g.setClip(0, y, width, 1);
	          g.drawImage(tmp, 0, y - (pos >> 16), Graphics.LEFT | Graphics.TOP);
	          pos += ratio;
	      }
	      return resizedImage;

	  }
	  
	  */
	
	  public Image resizeImage(Image src, int width, int height) {
		  int rgb[] = new int[src.getWidth() * src.getHeight()];

		  src.getRGB(rgb, 0, src.getWidth(), 0, 0, src.getWidth(), src.getHeight());

		  int rgb2[] = reescalaArray(rgb, src.getWidth(), src.getHeight(), width, height);

		  Image temp2 = Image.createRGBImage(rgb2, width, height, true);

		  return temp2;
	  }
	  
	  private int[] reescalaArray(int[] ini, int x, int y, int x2, int y2) {
		  int out[] = new int[x2*y2];

		  for (int yy = 0; yy < y2; yy++) {
			  int dy = yy * y / y2;

			  for (int xx = 0; xx < x2; xx++) {
				  int dx = xx * x / x2;
				  out[(x2*yy)+xx]=ini[(x*dy)+dx];
			  }
		  }

		  return out;
	  }
	
}
