//package graphicthreadtactoe;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//import java.util.concurrent.TimeUnit;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.stage.Stage;
//
///**
// *
// * @author Gabriel Doro
// */
//public class FXMLDocumentController implements Initializable {
//
//    GraphicThreadTacToe mgttt = new GraphicThreadTacToe();
//
//    //prelevo dalla classe principale gli elementi che servono per comporre la pagina
//    Group root = mgttt.getRoot();
//    Scene scene = mgttt.getScene();
//
//    @FXML
//    public Button button;
//
//    @FXML
//    private void swapPage(ActionEvent event) throws InterruptedException {
//
//        event.consume();
//
//        System.out.println("Partita iniziata");
//
//        //pausa 2 sec
//        TimeUnit.SECONDS.sleep(1);
//
//        //chiudo la schermata principale
//        Stage stage = (Stage) button.getScene().getWindow();
//        stage.close();
//
//        scene = new Scene(root, 300, 300);
//        Stage secondaryStage = new Stage();
//        secondaryStage.setTitle("ThreadTacToe");
//        secondaryStage.setScene(scene);
//        secondaryStage.show();
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//    }
//
//}
