package application;

import controleur.ControleurPrincipal;
import donnees.ParametreDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Parametre;
import redis.clients.jedis.Jedis;

import java.util.Calendar;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ControleurPrincipal controleurPrincipal = ControleurPrincipal.getInstance();

        Parent root = FXMLLoader.load(getClass().getResource("../Vue/TableauDeBord.fxml"));
        primaryStage.setTitle("TableauDeBord");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);


    }


}
