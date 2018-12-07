package controleur;

import donnees.TemperatureDAO;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Parametre;
import model.Temperature;
import org.apache.commons.lang.SerializationUtils;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;

public class ControleurVueTableauDeBord {
    protected TemperatureDAO temperatureDAO;
    private ControleurPrincipal controleurPrincipal = ControleurPrincipal.getInstance();

    public ControleurVueTableauDeBord() {
        temperatureDAO = new TemperatureDAO();
    }

    public void initialize() {

        Parametre parametre = controleurPrincipal.rechercherParametre();

        superieurA.setText("" + parametre.getSuperieurA());
        inferieurA.setText("" + parametre.getInferieurA());
        nbElement.setText("" + parametre.getNbElement());
        nbHeure.setText("" + parametre.getNbHeure());

        actualiser();
    }

    @FXML
    private Text min;

    @FXML
    private Text max;

    @FXML
    private Text moyenne;

    @FXML
    private Text mode;

    @FXML
    private Text mediane;

    @FXML
    private Text derniereHeure;

    @FXML
    private Text dernierElement;

    @FXML
    private Text erreur;

    @FXML
    private TextField nbElement;

    @FXML
    private TextField nbHeure;

    @FXML
    private TextField superieurA;

    @FXML
    private TextField inferieurA;

    @FXML
    private RadioButton heureChoix;

    @FXML
    private RadioButton elementChoix;

    private boolean boolHeure;

    @FXML
    private void modifier() {

        try {
            int heure = Integer.parseInt(nbHeure.getText());
            int element = Integer.parseInt(nbElement.getText());
            double superieur = Double.parseDouble(superieurA.getText());
            double inferieur = Double.parseDouble(inferieurA.getText());
            initChoixSelect();


            //=====JEDIS======
            Jedis cache = new Jedis();

            Parametre parametre = new Parametre();
            parametre.setNbHeure(heure);
            parametre.setNbElement(element);
            parametre.setSuperieurA((int) superieur);
            parametre.setInferieurA((int) inferieur);

            String concatenationParametre = nbHeure.getText() + " " + nbElement.getText() + " " + superieurA.getText() + " " + inferieurA.getText();
            int hash = concatenationParametre.hashCode();

            if (!existeDansCache(hash, cache)){
                try{

                    System.out.println("Parametre : " + parametre);
                    System.out.println("Concatenation : " + concatenationParametre);
                    System.out.println("Hash  : " + hash);

                    /*byte[] serialisation = SerializationUtils.serialize(toString(parametre));
                    System.out.println("Serialisation: " + serialisation);*/

                    System.out.println(" Encoded serialized version " );
                    System.out.println(toString(parametre));

                    cache.set(Integer.toString(hash), toString(parametre));
                    cache.set("timestamp", Calendar.getInstance().getTimeInMillis() + "");

                    String nomElement1 = cache.get(Integer.toString(hash));
                    System.out.println("Valeur associée au hash dans le cache : " + nomElement1);


                }catch(Exception e){
                    System.out.println(e);
                }
            }else{
                System.out.println("Reconstitution de l'objet");
                String serialisationCache = cache.get(Integer.toString(hash));
                System.out.println("SérialisationCache : " + serialisationCache);

                Parametre some = ( Parametre ) fromString(serialisationCache);
                System.out.println( "\n\nObjet reconstitué");
                System.out.println( some );
            }



            controleurPrincipal.modifierParametre(heure, element, superieur,inferieur,boolHeure);

            if (boolHeure) {
                derniereHeure.setText("" + heure);
                dernierElement.setText("XXX");
            } else {
                derniereHeure.setText("XXX");
                dernierElement.setText("" + element);
            }
            erreur.setVisible(false);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
            erreur.setVisible(true);
        } catch (Exception exception) {
            exception.printStackTrace();
            erreur.setVisible(true);
        }

        actualiser();


    }

    @FXML
    private void actualiser() {

        Temperature temperature = controleurPrincipal.rechercherTemperature();

        moyenne.setText((temperature.getMoyenne() != 999999) ? "" + temperature.getMoyenne() : "Valeur erron�e");
        mode.setText((temperature.getMode() != 999999) ? "" + temperature.getMode() : "Valeur erron�e");
        min.setText((temperature.getMinimum() != 999999) ? "" + temperature.getMinimum() : "Valeur erron�e");
        max.setText((temperature.getMaximum() != 999999) ? "" + temperature.getMaximum() : "Valeur erron�e");
        mediane.setText((temperature.getMediane() != 999999) ? "" + temperature.getMediane() : "Valeur erron�e");
    }

    private void initChoixSelect() {
        boolHeure = heureChoix.isSelected();
    }

    public Text getErreur() {
        return erreur;
    }

    /*private byte[] serialiser(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    private Object deserialiser(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return o;

        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }*/

    private boolean existeDansCache(int hash, Jedis cache){
        return cache.exists(Integer.toString(hash));
    }

    private static Object fromString( String s ) throws IOException ,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * Receive  Serializable
     * Use      Base64 https://docs.oracle.com/javase/8/docs/api/java/util/Base64.html
     * Use      ByteArrayOutputStream https://docs.oracle.com/javase/7/docs/api/java/io/ByteArrayOutputStream.html
     * Use      ObjectOutputStream https://docs.oracle.com/javase/7/docs/api/java/io/ObjectOutputStream.html
     * Return   Object
     */
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}