import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Font;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;
/**
* Creating the main Jordle class.
* @author dved6
* @version 13.3.1
 */

public class Jordle extends Application {
    /**
     * Creating the main method.
     *
     * @param args input
     */

    public static void main(String[] args) {
        launch(args);
    }

    //Overriding the start method.
    @Override
    public void start(Stage primaryStage) {
        ArrayList<String> wordsList = new ArrayList<>(Words.list);
        int length = wordsList.size();
        Random rand = new Random();
        int randomNumber = rand.nextInt(length) + 1;
        String word = wordsList.get(randomNumber);

        //We already  have a given stage/window to work with. Setting the title of the stage.
        primaryStage.setTitle("Jordle");

        //Creating a big VBox to hold everything.
        VBox bigBox = new VBox();

        //Creating a label to add the games name.
        Label gameName = new Label("JORDLE!");
        gameName.setFont(new Font("Times New Roman", 30));
        gameName.setAlignment(Pos.CENTER);

        //CREATING THE GRID.
        CustomVBox v1 = new CustomVBox();

        //Creating a HBox to hold the buttons
        HBox buttonHolderBox = new HBox();
        //Creating the Instructions button and adding functionality.
        Button instructionsButton = new Button("Instructions");
        instructionsButton.setAlignment(Pos.CENTER);
        instructionsButton.setFocusTraversable(false);
        instructionsButton.setOnAction(e -> {
            Stage window = new Stage();

            window.setTitle("INSTRUCTIONS!");
            window.setMinWidth(250);

            Label label = new Label();
            label.setText("Guess the JORDLE in six tries. Each guess must be a valid five-letter word."
                    + "Hit the enter button to submit.");
            Button closeButton = new Button("I have read the instructions. Take me back");
            closeButton.setOnAction(s -> window.close());

            VBox layout = new VBox(10);
            layout.getChildren().addAll(label, closeButton);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
        //Creating the restarting button. NO FUNCTIONALITY YET.
        Button restartButton = new Button("Restart");
        restartButton.setFocusTraversable(false);
        restartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int randomNumber2 = rand.nextInt(length) + 1;
                String word = wordsList.get(randomNumber2);
                v1.reset();
            }
        });
        buttonHolderBox.getChildren().addAll(instructionsButton, restartButton);


        //Adding the nodes to the overall bigBox
        bigBox.getChildren().addAll(gameName, buttonHolderBox, v1);

        //Creating a scene.
        Scene scene = new Scene(bigBox, 500, 500);

        scene.setOnKeyPressed(e -> {
            String inputText = e.getCode().toString();
            if ((inputText.length() == 1) && Character.isLetter(inputText.charAt(0))) {
                v1.addWord(inputText);
            } else if (inputText.equals("BACK_SPACE")) {
                v1.removeWord();
            } else if (inputText.equals("ENTER")) {
                v1.nextWord(word);
            }
        });

        //Setting the scene.
        primaryStage.setScene(scene);
        //Showing the stage.
        primaryStage.show();
    }

    //Creating a customVBox.
    static class CustomVBox extends VBox {
        private int currentWord = 0;
        //Adding 6 wordBoxes to it.
        private WordPane w1 = new WordPane();
        private WordPane w2 = new WordPane();
        private WordPane w3 = new WordPane();
        private WordPane w4 = new WordPane();
        private WordPane w5 = new WordPane();
        private WordPane w6 = new WordPane();
        //Creating an array to hold all the word boxs
        private WordPane[] array1 = new WordPane[]{w1, w2, w3, w4, w5, w6};

        //Creating the constructor for the CustomVBox.
        private CustomVBox() {
            Label status = new Label("Try guessing a word");
            this.getChildren().add(status);
            //Adding each stackPane to the HBox.
            for (WordPane w : array1) {
                this.getChildren().add(w);
            }
        }

        //Creating the addWord method to add the word to the VBox.
        public void addWord(String s) {
            array1[currentWord].addCharacter(s);
        }

        //Creating a removeWord method that will remove the character when the backspace is typed in.
        public void removeWord() {
            array1[currentWord].removeCharacter();
        }

        //Creating the nextLIne method to work for the enter key.
        public void nextWord(String word) {
            if (currentWord <= 5) {
                if (array1[currentWord].getCurrentCharacter() == 5) {
                    array1[currentWord].nextLine();
                    array1[currentWord].colorLetters(word);
                    currentWord++;
                } else {
                    Stage alertwindow = new Stage();
                    Label alertLabel = new Label("Please enter 5 letters to continue");
                    Button button1 = new Button("Take me back");
                    button1.setFocusTraversable(false);
                    button1.setOnAction(f -> alertwindow.close());
                    VBox v2 = new VBox();
                    v2.getChildren().addAll(alertLabel, button1);
                    Scene scene1 = new Scene(v2, 200, 200);
                    alertwindow.setScene(scene1);
                    alertwindow.showAndWait();
                }
            } else {
                this.gameStop();
            }
        }

        public void setCurrentWord(int currentWord) {
            this.currentWord = currentWord;
        }

        public void reset() {
            for (int b = 0; b < array1.length; b++) {
                array1[b].reset();
            }
            currentWord = 0;
        }

        public void gameStop() {
            if (currentWord > 5) {
                currentWord = 10;
            }
        }
    }

    //Creating an WordPane class.
    static class WordPane extends HBox {
        private int currentCharacter = 0;
        //Creating several stack panes using the custom stack pane class to place in the VBox.
        private CharacterPane s1 = new CharacterPane();
        private CharacterPane s2 = new CharacterPane();
        private CharacterPane s3 = new CharacterPane();
        private CharacterPane s4 = new CharacterPane();
        private CharacterPane s5 = new CharacterPane();
        //Creating an array to hold all the stackPanes
        private CharacterPane[] array2 = new CharacterPane[]{s1, s2, s3, s4, s5};

        //Creating a constructor.
        private WordPane() {
            //Adding each stackPane to the HBox.
            for (CharacterPane s : array2) {
                this.getChildren().add(s);
            }
        }

        //Creating a method that adds each character to the stackPane.
        public void addCharacter(String s) {
            if (currentCharacter < 5) {
                array2[currentCharacter].setUserInput(s);
                currentCharacter++;
            }
        }

        //Creating a method that removes the character from the stack pane
        public void removeCharacter() {
            if (currentCharacter >= 1) {
                //First decrementing the characterCount.
                currentCharacter--;
                //Then replacing the character with an empty string.
                array2[currentCharacter].remove();
            } else {
                array2[currentCharacter].remove();
            }
        }

        //Creating the method when the enter button is hit.
        public void nextLine() {
            currentCharacter = 0;
        }

        //Making a getter for the currentCharacter value.
        public int getCurrentCharacter() {
            return currentCharacter;
        }

        public void colorLetters(String guess) {
            String guessword = guess;
            int numGreen = 0;

            if (s1.getText().equals((("" + guessword.charAt(0)).toUpperCase()))) {
                s1.setBackground(new Background(new BackgroundFill(
                        Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                numGreen += 1;
            } else if (s1.getText().equals((("" + guessword.charAt(1)).toUpperCase()))
                    || s1.getText().equals((("" + guessword.charAt(2)).toUpperCase()))
                    || s1.getText().equals((("" + guessword.charAt(3)).toUpperCase()))
                    || s1.getText().equals((("" + guessword.charAt(4)).toUpperCase()))) {
                s1.setBackground(new Background(new BackgroundFill(
                        Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                s1.setBackground(new Background(new BackgroundFill(Color.GRAY,
                        CornerRadii.EMPTY, Insets.EMPTY)));
            }

            if (s2.getText().equals((("" + guessword.charAt(1)).toUpperCase()))) {
                s2.setBackground(new Background(new BackgroundFill(Color.GREEN,
                        CornerRadii.EMPTY, Insets.EMPTY)));
                numGreen += 1;
            } else if (s2.getText().equals((("" + guessword.charAt(0)).toUpperCase()))
                    || s2.getText().equals((("" + guessword.charAt(2)).toUpperCase()))
                    || s2.getText().equals((("" + guessword.charAt(3)).toUpperCase()))
                    || s2.getText().equals((("" + guessword.charAt(4)).toUpperCase()))) {
                s2.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                s2.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            }

            if (s3.getText().equals((("" + guessword.charAt(2)).toUpperCase()))) {
                s3.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                numGreen += 1;
            } else if (s3.getText().equals((("" + guessword.charAt(0)).toUpperCase()))
                    || s3.getText().equals((("" + guessword.charAt(1)).toUpperCase()))
                    || s3.getText().equals((("" + guessword.charAt(3)).toUpperCase()))
                    || s3.getText().equals((("" + guessword.charAt(4)).toUpperCase()))) {
                s3.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                s3.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            }

            if (s4.getText().equals((("" + guessword.charAt(3)).toUpperCase()))) {
                s4.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                numGreen += 1;
            } else if (s4.getText().equals((("" + guessword.charAt(0)).toUpperCase()))
                    || s4.getText().equals((("" + guessword.charAt(1)).toUpperCase()))
                    || s4.getText().equals((("" + guessword.charAt(2)).toUpperCase()))
                    || s4.getText().equals((("" + guessword.charAt(4)).toUpperCase()))) {
                s4.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                s4.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            }

            if (s5.getText().equals((("" + guessword.charAt(4)).toUpperCase()))) {
                s5.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                numGreen += 1;
            } else if (s5.getText().equals((("" + guessword.charAt(0)).toUpperCase()))
                    || s5.getText().equals((("" + guessword.charAt(1)).toUpperCase()))
                    || s5.getText().equals((("" + guessword.charAt(2)).toUpperCase()))
                    || s5.getText().equals((("" + guessword.charAt(3)).toUpperCase()))) {
                s5.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                s5.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            }

            if (numGreen == 5) {
                Stage winnerWindow = new Stage();
                VBox v2 = new VBox();
                HBox hb1 = new HBox();
                Label alertLabel = new Label("Winner Winner chicken dinner!");
                hb1.getChildren().addAll(alertLabel);
                HBox hb2 = new HBox();
                Button yesButton = new Button("Yes");
                yesButton.setOnAction(t -> {
                    winnerWindow.close();
                    array2[currentCharacter].reset();
                });
                hb2.getChildren().add(yesButton);
                HBox hb3 = new HBox();
                Button noButton = new Button("No");
                noButton.setOnAction(g -> {
                    winnerWindow.close();
                });
                hb3.getChildren().add(noButton);
                v2.getChildren().addAll(hb1, hb2, hb3);
                Scene scene1 = new Scene(v2, 200, 200);
                winnerWindow.setScene(scene1);
                winnerWindow.showAndWait();
            } else {
                Stage winnerWindow = new Stage();
                Label alertLabel = new Label("Try again. You are almost there!");
                Button tryAgain = new Button("Try another word");
                tryAgain.setFocusTraversable(false);
                tryAgain.setOnMouseClicked(r -> winnerWindow.close());
                VBox v2 = new VBox();
                v2.getChildren().addAll(alertLabel, tryAgain);
                Scene scene1 = new Scene(v2, 200, 200);
                winnerWindow.setScene(scene1);
                winnerWindow.showAndWait();
            }
        }

        public void reset() {
            for (int a = 0; a < array2.length; a++) {
                array2[a].reset();
                array2[a].setBackground(new Background(new BackgroundFill(
                        Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            currentCharacter = 0;
        }

        //Creating a custom CharacterPane class
        static class CharacterPane extends StackPane {
            //Creating an instance variable to store the input.
            private Text userInput = new Text("");

            //Creating a default contructor.
            private CharacterPane() {
                //Adding the text type to the CharacterPane
                this.getChildren().addAll(userInput);
                setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                this.setPrefSize(50, 50);
            }

            //Making the remove method to work with backspace.
            public void remove() {
                this.userInput.setText("");
            }

            //Sets the user input from the text object.
            public void setUserInput(String text) {
                this.userInput.setText(text);
            }

            //Getter function
            public String getText() {
                return this.userInput.getText();
            }

            public void reset() {
                this.setUserInput("");
            }

            public void checkLetters(String word) {
                int counter = 0;
                char[] letters = word.toCharArray();
                for (char c : letters) {
                    String letter = String.valueOf(c);
                    if (userInput.toString().equalsIgnoreCase(letter)) {
                        counter++;
                    }
                }
                if (counter == 5) {
                    System.out.println("You have won");
                } else {
                    System.out.println("You have lost");
                }
            }

        }
    }
}
