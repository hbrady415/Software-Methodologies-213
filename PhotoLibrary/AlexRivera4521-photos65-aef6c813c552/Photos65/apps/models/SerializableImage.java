package models;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * JavaFX's image class does not implement Serializable so in order to store an Image its respective place in the photo in the userlist we 
 * must create a serializable object that will store the pictures bit map so that it can recreate the image whenever that object is called
 * 
 * Basically it allows persistent storage of the Images in the UserList object
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */
public class SerializableImage implements Serializable {
    private int width, height;
    private int[][] data;

	/**
	 * stores the image's pixels so that it can map it to new objects with get Image
	 * @param image the image that will be converted to a serialized image
	 */
    public void setImage(Image image) {
        width = ((int) image.getWidth());
        height = ((int) image.getHeight());
        data = new int[width][height];
        PixelReader r = image.getPixelReader();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = r.getArgb(i, j);
            }
        }
    }

	/**
	 * returns the actual image (via pixel bitmap) 
	 * @return gets an image out of serialized image
	 */
    public Image getImage() {
        WritableImage img = new WritableImage(width, height);
        PixelWriter w = img.getPixelWriter();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                w.setArgb(i, j, data[i][j]);
            }
        }

        return img;
    }

}
