package models;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * In order to display the images inside the table columns in Album Overview and search we set each cell of the table column to
 * display a custom anchor pane that holds an ImageView where we put the image stored within the serialized image field in photo
 * its used in 2 different tables so its best to just make it its own object and use it in both controllers
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 */

public class PhotoCell extends TableCell<Photo, SerializableImage> {
	
	private AnchorPane apane = new AnchorPane();
	private StackPane spane = new StackPane();
	private ImageView imageView = new ImageView();
	public PhotoCell(){
		super();
	
		imageView.setFitWidth(175.0);
		imageView.setFitHeight(128.0);
		imageView.setPreserveRatio(true);
		
		StackPane.setAlignment(imageView, Pos.CENTER);
		spane.getChildren().add(imageView); 
		spane.setPrefHeight(128.0);
		spane.setPrefWidth(175.0);
		AnchorPane.setLeftAnchor(spane, 0.0);
		apane.getChildren().addAll(spane);
		apane.setPrefHeight(128.0);
		setGraphic(apane);
	}
	
	/*
	 * updateItem is the method called whenever a tablecell is changed
	 * override updateItem so that whenever the tablerow gets a photo placed in it or removed from it it
	 * updates the anchorpane that the cell displays to reprsent the change
	 */
	
	@Override
	public void updateItem(SerializableImage item, boolean empty) {
		super.updateItem(item, empty);
		if(item == null){
			imageView.setImage(null);
		}else if (item != null) {
			imageView.setImage(item.getImage());
			setGraphic(imageView);
		}
		
	}
}
