package graphicthreadtactoe;

import javafx.scene.layout.GridPane;

/**
 *
 * @author alessandrodipasquale
 */
public class PlayersManager extends GraphicThreadTacToe {
    private GridPane box;
    private String matrix[][];
    private int localCounter;
    private boolean turn = true;

    public PlayersManager() {
        this.box = GraphicThreadTacToe.getBox();
        this.matrix = GraphicThreadTacToe.getRowsColsIndex();
        this.localCounter = GraphicThreadTacToe.getCounter();
    }
    
    public synchronized void readPos() throws InterruptedException {
        while (!turn) {
            wait();
        }
        
        if (this.localCounter < GraphicThreadTacToe.getCounter()) {
            int pos=0;
            for (int i=0; i<3; i++) {
                for (int j=0; j<3; j++) {
                    if (box.getChildren().get(pos).getId() == "xp") {
                        this.matrix[i][j] = "x";
                    } else if (box.getChildren().get(pos).getId() == "op") {
                        this.matrix[i][j] = "o";
                    }
                    pos++;
                }
            }
            this.localCounter++;
            GraphicThreadTacToe.checkVictory();  
        } else {
            Thread.sleep(500);
        }    
        
        this.turn = false;
        notifyAll();
    }
    
    public synchronized void writePos() throws InterruptedException {
        while (turn) {
            wait();
        }
        GraphicThreadTacToe.setRowsColsIndex(matrix);
        
        this.turn = true;
        notifyAll();
    }
}
