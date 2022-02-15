package graphicthreadtactoe;

/**
 *
 * @author alessandrodipasquale
 */
public class RunPositionReader extends GraphicThreadTacToe implements Runnable {
    private PlayersManager pm;

    public RunPositionReader(PlayersManager pm) {
        this.pm = pm;
    }
    
    public void run() {
        try {
            while(!win) {
                pm.readPos();
            }
        } catch(Exception e) {
             System.out.println("[x] Error " + e);
        }
    }
}
