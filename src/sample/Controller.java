package sample;
/*
Crear una aplicació d'escriptori que mostri informació sobre diferents Pokémons. Haurà de complir les següents característiques:

    - La informació l'heu de aconseguir de la API REST http://pokeapi.co/
    - L'aplicació ha de permetre triar d'una llista de Pokémons disponibles, de quin es vol veure la informació.
    - La recuperació i tractament de la informació ha d'estar en una (o més) classes separades de la lògica de la GUI.
        Aquesta classe (o classes) NO tindran carregada a memòria sempre tota la informació de tots els Pokémons, només
        podran tenir de forma permanent la llista dels disponibles. Quan es demani de informació d'un pokémon concret
        es recuperarà de la API.
    - La informació mostrada ha d'incloure una imatge del Pokémon. La podeu extreure de la mateixa API.
    - Les operacions de l'aplicació han d'estar disponibles mitjançant menús a més a més dels controls que vosaltres trieu.
    - Usar algun component de la llibreria ControlsFX.
    - Modificar l'aspecte per defecte de la GUI usant fulles d'estil CSS.

Com sempre heu de cuidar al màxim els aspectes relacionats amb la usabilitat. Fixeu-vos també en la redimensió de la finestra de l'aplicació.
 */

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    ListView pokemonListView = new ListView();
    @FXML
    ListView movesListView = new ListView();
    @FXML
    ListView typeListView = new ListView();
    @FXML
    ImageView pokemonImageView;
    @FXML
    Text nameText;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Button catchPokemonButton;
    @FXML
    TextField idTextField;

    Image image;

    ObservableList<String> items = FXCollections.observableArrayList();

    public void initialize(){
        DAOPokemonDB.createPokemonDb();
    }

    /**
     * Mètode que agafa la id introduida i l'envia al mètode que busca la id a la API i descarrega les dades.
     * @param actionEvent
     * @throws Exception
     */
    public void catchPokemon(ActionEvent actionEvent) throws Exception {
        int idCatched = Integer.parseInt(idTextField.getText());
        PokemonJSON.getPokemonFromJson(idCatched);
    }

    /**
     * Tanca programa.
     * @param actionEvent
     */
    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }
}
