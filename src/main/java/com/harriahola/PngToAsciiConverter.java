package com.harriahola;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PngToAsciiConverter {
    private static final String ASCII_CHARS = "@#&$%*o!;.";

    public static void convert(String filename, Boolean saveInAscii) {

        if (filename == null) {
            filename = "input.png";
        }
        if (saveInAscii == null) {
            saveInAscii = true;
        }

        try {
            // Load the input image
            BufferedImage image = ImageIO.read(new File(filename));
            
            // Resize the image to a smaller size
            int newWidth = 80;
            int newHeight = (int) Math.round(image.getHeight() * (double) newWidth / image.getWidth());
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            resizedImage.createGraphics().drawImage(image, 0, 0, newWidth, newHeight, null);
            
            // Convert the image to ASCII art
            StringBuilder asciiImage = new StringBuilder();
            for (int y = 0; y < newHeight; y++) {
                for (int x = 0; x < newWidth; x++) {
                    int rgb = resizedImage.getRGB(x, y);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;
                    double grayscale = 0.2126 * r + 0.7152 * g + 0.0722 * b;
                    int index = (int) Math.round((grayscale * (ASCII_CHARS.length() - 1)) / 255.0);
                    asciiImage.append(ASCII_CHARS.charAt(index));
                }
                asciiImage.append('\n');
            }
            
            if (saveInAscii) {
                // Print the ASCII art to the console
                System.out.println(asciiImage.toString());
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
