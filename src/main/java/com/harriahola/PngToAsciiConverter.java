package com.harriahola;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.imageio.ImageIO;

public class PngToAsciiConverter {
    private static final String ASCII_CHARS = "@#&$%*o!;.";

    public static void convert(String filename, Boolean saveInAscii, String outputFilename, int _height) {
        if (filename == null) {
            filename = "input.png";
        }
        if (saveInAscii == null) {

            saveInAscii = true;
        }
        if (outputFilename == null) {
            outputFilename = "output.png";
        }
        if (!Files.exists(Paths.get(filename))) {
            System.out.println("ERROR: Please provide a valid input image!");
            return;
        }

        try {

            BufferedImage image = ImageIO.read(new File(filename));

            // Resize the image to a smaller size
            int desiredHeight = _height;
            int newWidth = (int) Math.round(image.getWidth() * (double) desiredHeight / image.getHeight());
            int newHeight = desiredHeight;

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
            } else {

                // Convert the ASCII art to a BufferedImage
                String asciiArt = asciiImage.toString();
                String[] lines = asciiArt.split("\n");
                int width = 0;
                for (String line : lines) {
                    if (line.length() > width) {
                        width = line.length();
                    }
                }
                int height = lines.length;
                Font font = new Font("Monospaced", Font.PLAIN, 12);
                FontMetrics fontMetrics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).createGraphics()
                        .getFontMetrics(font);
                int fontHeight = fontMetrics.getHeight();
                int fontWidth = fontMetrics.charWidth('M');
                BufferedImage asciiToImage = new BufferedImage(width * fontWidth, height * fontHeight,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = asciiToImage.createGraphics();
                graphics.setFont(font);
                graphics.setBackground(Color.MAGENTA);
                graphics.clearRect(0, 0, asciiToImage.getWidth(), asciiToImage.getHeight());
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < lines[y].length(); x++) {
                        char ch = lines[y].charAt(x);
                        graphics.drawString(Character.toString(ch), x * fontWidth,
                                (y + 1) * fontHeight - fontMetrics.getDescent());
                    }
                }

                try {
                    File outputFile = new File(outputFilename);
                    ImageIO.write(asciiToImage, "png", outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
