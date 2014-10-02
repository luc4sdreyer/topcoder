import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ex1_6 {
	public static void main(String[] args) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("photo.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataBuffer buffer = image.getRaster().getDataBuffer();
		int x = (int) Math.sqrt(buffer.getSize()/3);
		byte[][] img = new byte[x][x*3];
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[i].length; j++) {
				img[i][j] = (byte) buffer.getElem(i * img[i].length + j);
			}
		}
		rotate(img);
		//flip(img);
		//clear(img);
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[i].length; j++) {
				buffer.setElem(i * img[i].length + j, img[i][j]);
			}
		}
		//image.setData(image.getRaster().);
		try {
			ImageIO.write(image, "bmp", new File("photo2.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void clear(byte[][] img) {
		for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[i].length; j++) {
				img[i][j] = 0;
			}
		}
	}

	public static void flip(byte[][] img) {
		for (int i = 0; i < img.length/2; i++) {
			for (int j = 0; j < img[i].length; j++) {
				byte temp = img[i][j];
				img[i][j] = img[img.length - i - 1][j];
				img[img.length - i - 1][j] = temp;
			}
		}		
	}

	public static void rotate(byte[][] image) {
		int bpp = 3;
		for (int i = 0; i < image.length; i++) {
			if (image[i].length != image.length*bpp) {
				return;
			}
		} 

		for (int i = 0; i < image.length/2; i++) {
			for (int j = i; j < image.length - i - 1; j++) {
				for (int k = 0; k < bpp; k++) {
					byte temp = image[i][(j)*bpp + k];
					image[i][(j)*bpp + k] = image[image.length - j - 1][(i)*bpp + k];
					image[image.length - j - 1][(i)*bpp + k] = image[image.length - i - 1][(image.length - j - 1)*bpp + k];
					image[image.length - i - 1][(image.length - j - 1)*bpp + k] = image[j][(image.length - i - 1)*bpp + k];
					image[j][(image.length - i - 1)*bpp + k] = temp;
				}
			}
		}
	}
}