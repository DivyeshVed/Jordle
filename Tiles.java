import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.util.Random;
import javafx.scene.*;

public class Tiles {

    public static void makeGrid(){
        Stage window = new Stage();
        HBox h1 = new HBox();
        VBox v1 = new VBox(new TextField());
        VBox v2 = new VBox(new TextField());
        VBox v3 = new VBox(new TextField());
        VBox v4 = new VBox(new TextField());
        VBox v5 = new VBox(new TextField());
        h1.getChildren().addAll(v1,v2,v3,v4,v5);
    }
}
