package controleur;

import donnees.TemperatureDAO;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Parametre;
import model.Temperature;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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

            String concatenationParametre = nbHeure.getText() + nbElement.getText() + superieurA.getText() + inferieurA.getText();
            int hash = concatenationParametre.hashCode();

            if (!existeDansCache(hash, cache)){
                try{

                    System.out.println(parametre);
                    System.out.println("concatenation : " + concatenationParametre);
                    System.out.println("hash  : " + hash);

                    byte[] serialisation = serialiser(parametre.toString());
                    System.out.println("serialisarion : " + serialisation);


                    cache.set(Integer.toString(hash), serialisation.toString());
                    cache.set("timestamp", Calendar.getInstance().getTimeInMillis() + "");
                    String nomElement1 = cache.get(Integer.toString(hash));
                    System.out.println("nomElement1 : " + nomElement1);


                }catch(Exception e){
                    System.out.println(e);
                }
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

    private byte[] serialiser(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    private boolean existeDansCache(int hash, Jedis cache){
        return cache.exists(Integer.toString(hash));
    }
}