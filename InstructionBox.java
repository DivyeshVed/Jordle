import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.stage.*;

//TO make this alerts box: instructionsButton.setOnAction(e -> InstructionBox.display());
public class InstructionBox {

    public static void display() {
        Stage window = new Stage();

        window.setTitle("INSTRUCTIONS!");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("Guess the JORDLE in six tries. Each guess must be a valid five-letter word." +
                "Hit the enter button to submit.");
        Button closeButton = new Button("I have read the instructions. Take me back");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}

