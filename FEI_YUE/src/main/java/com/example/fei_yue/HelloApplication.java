package com.example.fei_yue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {
  /**
   * @param args the command line arguments
   */

  private GridPane centerPane = new GridPane();
  public static void main(String[] args) {
    launch(HelloApplication.class, args);
  }

  @Override
  public void start(Stage stage) {
    BorderPane border = new BorderPane();
    HBox hbox = addHBox();
    border.setTop(hbox);
    border.setLeft(addVBox());
    border.setCenter(centerPane);
    Scene scene = new Scene(border);
    stage.setScene(scene);
    stage.setTitle(AppConstants.APP_NAME);
    stage.setWidth(AppConstants.WINDOW_WIDTH);
    stage.setHeight(AppConstants.WINDOW_HEIGHT);
    stage.show();
  }

  /*
   * Creates an HBox with two buttons for the top region
   */

  private HBox addHBox() {
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(15, 12, 15, 12));
    hbox.setSpacing(10);   // Gap between nodes
    hbox.setStyle("-fx-background-color: #336699;");
    Text title = new Text(AppConstants.APP_NAME);
    title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    hbox.getChildren().addAll(title);
    return hbox;
  }

  /*
   * Creates a VBox with a list of links for the left region
   */
  private VBox addVBox() {

    VBox vbox = new VBox();
    vbox.setPadding(new Insets(10)); // Set all sides to 10
    vbox.setSpacing(8);              // Gap between nodes

    Text title = new Text(AppConstants.MENU);
    title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    vbox.getChildren().add(title);

    Hyperlink linkAddHY =  new Hyperlink(AppConstants.MENU_ADD_HY);
    linkAddHY.setOnAction((actionEvent -> {
      this.centerPane.getChildren().clear();
      this.addHuiYuanPane();
    }));
    Hyperlink linkUpdateHY =  new Hyperlink(AppConstants.MENU_UPDATE_HY);
    linkUpdateHY.setOnAction((actionEvent -> {
      this.centerPane.getChildren().clear();
      this.editHuiYuanPane();
    }));
    Hyperlink linkViewHY =  new Hyperlink(AppConstants.MENU_VIEW_HY);
    Hyperlink linkFinance =  new Hyperlink(AppConstants.MENU_FINANCE);

    Hyperlink options[] = new Hyperlink[]{
        linkAddHY, linkUpdateHY,
        linkViewHY, linkFinance
        };

    for (int i=0; i<4; i++) {
      VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
      vbox.getChildren().add(options[i]);
    }
    return vbox;
  }


  /*
   * Creates a grid for the center region with four columns and three rows
   */
  private GridPane addHuiYuanPane() {
    centerPane = new AddHYPane().buildAddHYPane(centerPane);
    return centerPane;
  }

  private GridPane editHuiYuanPane() {
    centerPane = new EditHYPane().buildEditHYPane(centerPane);
    return centerPane;
  }
  /*
   * Creates an anchor pane using the provided grid and an HBox with buttons
   *
   * @param grid Grid to anchor to the top of the anchor pane
   */
  private AnchorPane addAnchorPane(GridPane grid) {

    AnchorPane anchorpane = new AnchorPane();

    Button buttonSave = new Button("Save");
    Button buttonCancel = new Button("Cancel");

    HBox hb = new HBox();
    hb.setPadding(new Insets(0, 10, 10, 10));
    hb.setSpacing(10);
    hb.getChildren().addAll(buttonSave, buttonCancel);

    anchorpane.getChildren().addAll(grid,hb);
    // Anchor buttons to bottom right, anchor grid to top
    AnchorPane.setBottomAnchor(hb, 8.0);
    AnchorPane.setRightAnchor(hb, 5.0);
    AnchorPane.setTopAnchor(grid, 10.0);

    return anchorpane;
  }
}