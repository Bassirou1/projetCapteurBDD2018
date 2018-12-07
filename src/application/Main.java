package application;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import controleur.ControleurPrincipal;
import donnees.ParametreDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.converter.ByteStringConverter;
import model.Parametre;
import org.apache.commons.lang.SerializationUtils;
import redis.clients.jedis.Jedis;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        launch(args);

        /*String example = "Convert Java String";
        byte[] bytes = example.getBytes();
        System.out.println(bytes);

        byte[] serialisation = SerializationUtils.serialize(example);
        System.out.println("Serialisation: " + serialisation);

        String test = serialisation.toString();
        System.out.println("test: " + test);
        System.out.println("test: " + test);
        System.out.println("test bytes: " + hexStringToByteArray(test));


        Object o = SerializationUtils.deserialize(serialisation);
        System.out.println("Objet retrouvé : " + o);*/


        //==== partie eric

        /*String string = null;
        try {
            string = toString(parametre);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(" Encoded serialized version " );
        System.out.println( string );
        Parametre some = ( Parametre ) fromString( string );
        System.out.println( "\n\nReconstituted object");
        System.out.println( some );*/


    }


}
