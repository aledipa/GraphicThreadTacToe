package graphicthreadtactoe;

//IMPORTS
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author Alessandro Di Pasquale; 
 */
public class GraphicThreadTacToe extends Application {
    //Istance Variables
    public static int counter = 0;
    private float timeLeft = 15;
    public static boolean win = false;
    public static boolean winTie = false;
    public static String player = "x";
    //Playfield Matrix
    public static String[][] rowsColsIndex = new String[3][3];
    //Boxes of the grid
    private static GridPane box = new GridPane();
    public static final String BUTTONSTYLE = 
                  "           -fx-background-color: #f2f4ff;"
                + "           -fx-font-size: 5em;"
                + "           -fx-border-color: #7087ff;"
                + "           -fx-border: 1px;"
                + "           -fx-outline: 0;";
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        for (int row1 = 0; row1 < 3; row1++) {
            for (int col1 = 0; col1 < 3; col1++) {
                Button newBtn = new Button();
                newBtn.setText("");
                newBtn.setMinSize(120, 120);
                newBtn.setStyle(BUTTONSTYLE);
                EventHandler<ActionEvent> event = (ActionEvent e) -> {
                    if (counter < 9) {
                        if (newBtn.getText().equals("")) {
                            newBtn.setText(player);
                            timeLeft = 15;
                            checkVictory();
                            if ((counter % 2) == 1) {
                                newBtn.setId("op");
                                player = "x";
                                primaryStage.setTitle("Now playing: " + player);
                                newBtn.setStyle(BUTTONSTYLE + "-fx-text-fill: #61d86d;"); //Green
                            } else {
                                newBtn.setId("xp");
                                player = "o";
                                primaryStage.setTitle("Now playing: " + player);
                                newBtn.setStyle(BUTTONSTYLE + "-fx-text-fill: #ff8e8c;"); //Orange
                            }
                            counter++;  
                        }
                    }
                };
                newBtn.setOnAction(event);
                box.add(newBtn, col1, row1);
            }
        }
        
        //Starts the synchronized threads
        PlayersManager pm = new PlayersManager();
        Thread pmRead = new Thread(new RunPositionReader(pm));
        Thread pmWrite = new Thread(new RunPositionWriter(pm));
        pmRead.start();
        pmWrite.start();
        

        Group root = new Group();
        root.getChildren().add(box);

        //Create a ExecutorService threadpool
        ExecutorService threadPool = Executors.newWorkStealingPool();        
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                boolean ticTac = false;
                while (true) {
                    try {
                        if (timeLeft <= 5) {
                            if (ticTac == false) {
                                makeGridColor("#ff7070");
                                ticTac = true;
                            } else {
                                makeGridColor("#7087ff");
                                ticTac = false;
                            }
                        } else {
                               makeGridColor("#7087ff");
                        }
                        Thread.sleep(500);
                        timeLeft -= 0.5;
                        if (timeLeft <= 0 || win == true) {
                            if (player.equals("x")) {
                                player = "o";
                            } else {
                                player = "x";
                            }
                            Platform.runLater(() -> {
                                primaryStage.hide();
                                primaryStage.close();
                                threadPool.shutdownNow();
                                victory();
                            });
                            break;
                        }

                    } catch(Exception e) {
                        System.out.println("[x] Error in main timer thread, process ID: " + Thread.currentThread().getName()
                           + "\n[x] Exception details: " + e);
                    }
                }
                threadPool.shutdownNow();
            }

            public void makeGridColor(String colorHEX) {
                for (int i=0; i<9; i++) {
                    if (box.getChildren().get(i).getId() == "xp") {
                        box.getChildren().get(i).setStyle(BUTTONSTYLE + "-fx-border-color: " + colorHEX + "; -fx-text-fill: #ff8e8c;");
                    } else if (box.getChildren().get(i).getId() == "op") {
                        box.getChildren().get(i).setStyle(BUTTONSTYLE + "-fx-border-color: " + colorHEX + "; -fx-text-fill: #61d86d;");
                    } else {
                        box.getChildren().get(i).setStyle(BUTTONSTYLE + "-fx-border-color: " + colorHEX + ";");
                    }
                }      
            }
        });
        
        root = new Group();
        root.getChildren().add(box);

        Scene scene = new Scene(root, 360, 360);

        primaryStage.setTitle("Now playing: " + player);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    public static void checkVictory() {
        for (int i=0; i<3; i++) {
            //Horizontal
            if (horizontalCheck(i)) {
                win = true;
                return;
                
            }
            //Vertical
            if (verticalCheck(i)) {
                win = true;
                return;
            }
        }
        //Oblique
        if (obliqueLeftCheck()) {
            win = true;
            return;
        }
        if (obliqueRightCheck()) {
            win = true;
            return;
        }
        if (tieCheck()) {
            win = true;
        }
    }
    
    //Checks horizontally (bidirectional)
    public static boolean horizontalCheck(int rStart) {
        boolean tris = true;
        String firstValue = rowsColsIndex[rStart][0];
        if (firstValue != null) {
            for (int i=1; i<3; i++) {
                if (rowsColsIndex[rStart][i] != null) {
                    if (!firstValue.equals(rowsColsIndex[rStart][i])) {
                        tris = false;
                    }
                } else {
                    tris = false;
                }    
            }
        } else {
            tris = false;
        }  
        return tris;
    }
    
    //Checks vertically (bidirectional)
    public static boolean verticalCheck(int rStart) {
        boolean tris = true;
        String firstValue = rowsColsIndex[0][rStart];
        if (firstValue != null) {
            for (int i=1; i<3; i++) {
                if (rowsColsIndex[i][rStart] != null) {
                    if (!firstValue.equals(rowsColsIndex[i][rStart])) {
                        tris = false;
                    }
                } else {
                    tris = false;
                }    
            }
        } else {
            tris = false;
        }  
        return tris;
    }

    //Checks obliquely (unidirectional - from top left to bottom right)
    public static boolean obliqueLeftCheck() {
        boolean tris = true;
        String firstValue = rowsColsIndex[0][0];
        if (firstValue != null) {
            for (int i=1; i<3; i++) {
                if (rowsColsIndex[i][i] != null) {
                    if (!firstValue.equals(rowsColsIndex[i][i])) {
                        tris = false;
                    }
                } else {
                    tris = false;
                }    
            }
        } else {
            tris = false;
        }  
        return tris;
    }

    //Checks obliquely (unidirectional - from top right to bottom left)
    public static boolean obliqueRightCheck() {
        boolean tris = true;
        String firstValue = rowsColsIndex[0][2];
        if (firstValue != null) {
            int j=0;
            for (int i=2; i>=0; i--) {
                if (rowsColsIndex[j][i] != null) {
                    if (!firstValue.equals(rowsColsIndex[j][i])) {
                        tris = false;
                    }
                } else {
                    tris = false;
                }
                j++;
            }
        } else {
            tris = false;
        }  
        return tris;
    }
    
    //Checks a Tie Win
    public static boolean tieCheck() {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (rowsColsIndex[j][i] == null) {
                    return false;
                }
            }  
        }
        winTie = true;
        return true;
    }
    
    
    //Loads the victory panel
    public static void victory() {
        win = true;
        Stage victoryStage = new Stage();
        Text text = new Text();
        if (winTie) {
            text.setText("Tie!");
        } else {
            text.setText(player + ", You Won!");
        }
            
        String txtvictoryStyle = "-fx-font-size: 4em;";
        String paneVictoryStyle = "-fx-background-image: url('graphicthreadtactoe/stage2.jpg');"
                                + "-fx-text-align: center;";

        text.setStyle(txtvictoryStyle);
        text.setFont(Font.font("OCR A Extended"));
        text.setFill(Color.WHITE);
        text.setX(85);
        text.setY(225);

        Pane victoryStyle = new Pane();
        victoryStyle.getChildren().add(text);
        victoryStyle.setStyle(paneVictoryStyle);

        Scene scene = new Scene(victoryStyle, 450, 450);
        victoryStage.setTitle("Game Over!");
        victoryStage.setResizable(false);
        victoryStage.setScene(scene);
        victoryStage.show();
    }

    //GridPane Getter
    public static GridPane getBox() {
        return box;
    }

    //Counter Getter
    public static int getCounter() {
        return counter;
    }

    //Matrix Getter
    public static String[][] getRowsColsIndex() {
        return rowsColsIndex;
    }
    
    //Matrix Setter
    public static void setRowsColsIndex(String[][] rowsColsIndex) {
        GraphicThreadTacToe.rowsColsIndex = rowsColsIndex;
    }
    
    //Main sus
    public static void main(String[] args) {
        launch(args);
    }

}
