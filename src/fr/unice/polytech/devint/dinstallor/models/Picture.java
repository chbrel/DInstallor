package fr.unice.polytech.devint.dinstallor.models;

/*************************************************************************
 *  Compilation:  javac Picture.java
 *  Execution:    java Picture filename
 * 
 *  Data type for manipulating individual pixels of an image. The original
 *  image can be read from a file in JPEG, GIF, or PNG format, or the
 *  user can create a blank image of a given size. Includes methods for
 *  displaying the image in a window on the screen or saving to a file.
 * Use : new Picture(); 
 * Use : new Picture(int w, int h); 
 * Use : new Picture(File file); 
*/

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Picture {
    private BufferedImage image;
    private JFrame f;

    // create a blank w-by-h image
    public Picture(int w, int h) {
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // set to TYPE_INT_ARGB to support transparency
    }

    // create an image by reading in the PNG, GIF, or JPEG from a filename
    public Picture(String filename) {
        try { image = ImageIO.read(new File(filename)); }
        catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not open file: " + filename);
        }
        if (image == null)
            throw new RuntimeException("Invalid image file: " + filename);
    }

    // create an image by reading in the PNG, GIF, or JPEG from a file
    public Picture(File file) {
        try { image = ImageIO.read(file); }
        catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not open file: " + file);
        }
        if (image == null)
            throw new RuntimeException("Invalid image file: " + file);
    }

    // to embed in a JPanel, JFrame or other GUI widget
    public JLabel getJLabel() {
        if (image == null) return null;         // no image available
        ImageIcon icon = new ImageIcon(image);
        return new JLabel(icon);
    }

    // view on-screen, creating new frame if necessary
    public void show() {

        // create the GUI for viewing the image if needed
        if (f == null) {
            f = new JFrame();
            f.setContentPane(getJLabel());
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setTitle("Picture Frame");
            f.setResizable(false);
            f.pack();
            f.setVisible(true);
        }

        // draw
        f.repaint();
    }


    // accessor methods
    public int height() { return image.getHeight(null); }
    public int width()  { return image.getWidth(null);  }

    // return Color of pixel (i, j)
    public Color getColor(int i, int j) {
        return new Color(image.getRGB(i, j));
    }

    // return grayscale equivalent of pixel (i, j)
    public int getGray(int i, int j) {
        Color color = getColor(i, j);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int luminance = (int) (0.299*r + 0.587*g + 0.114*b);  // NTSC formula
        return luminance;
    }

    // set pixel (i, j) to given grayscale value
    public void setGray(int i, int j, int c) {
        Color color = new Color(c, c, c);
        setColor(i, j, color);
    }

    // change color of pixel (i, j) to c
    public void setColor(int i, int j, Color c) {
        image.setRGB(i, j, c.getRGB());
    }

    // save to given filename - suffix must be png, jpg, or gif
    public void save(String filename) { save(new File(filename)); }

    // save to given filename - suffix mst be png, jpg, or gif
    public void save(File file) {
        String filename = file.getName();
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        try { ImageIO.write(image, suffix, file); }
        catch (IOException e) { e.printStackTrace(); }
    }


    // test client: read in input file and display
    public static void main(String args[]) {
        Picture pic = new Picture(args[0]);
        pic.show();
    }
   
}