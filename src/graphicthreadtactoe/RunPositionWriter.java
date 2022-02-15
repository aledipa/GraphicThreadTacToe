package graphicthreadtactoe;

/**
 *
 * @author alessandrodipasquale
 */
public class RunPositionWriter extends GraphicThreadTacToe implements Runnable {
    private PlayersManager pm;

    public RunPositionWriter(PlayersManager pm) {
        this.pm = pm;
    }
    
    public void run() {
        try {
            while (!win) {
                pm.writePos();
            }
        } catch(Exception e) {
             System.out.println("[x] Error " + e);
        }
    }
}
