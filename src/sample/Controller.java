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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

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
    Text idText;
    @FXML
    Text catchText;
    @FXML
    Text pokemonListText;
    @FXML
    Text typeText;
    @FXML
    Text movesText;
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

    ArrayList<Integer> idsSelectedPokemon = new ArrayList<Integer>();


    public void initialize(){
        DAOPokemonDB.deletePokemonDb();
        DAOPokemonDB.createPokemonDb();
        anchorPane.getStyleClass().add("pane");
        nameText.setId("fancytext");
        pokemonListText.setId("fancytext2");
        catchText.setId("fancytext3");
        idText.setId("fancytext2");
        movesText.setId("fancytext3");
        typeText.setId("fancytext3");
    }

    /**
     * Mètode que agafa la id introduida i l'envia al mètode que busca la id a la API i descarrega les dades.
     * @param actionEvent
     * @throws Exception
     */
    public void catchPokemon(ActionEvent actionEvent) throws Exception {
        int idCatched = Integer.parseInt(idTextField.getText());
        PokemonJSON.getPokemonFromJson(idCatched);
        idsSelectedPokemon.add(idCatched);
        insertInfo(idCatched);
    }

    /**
     * Metode resum.
     * @param id
     */
    public void insertInfo(int id){
        showPokemon(id);
        showTypes(id);
        showMoves(id);
        showImage(pokemonImageView,id);
    }

    // Mostra info.

    /**
     * Inserta els Pokemon en el ListView passant-li la ID del Pokemon. També inserta el nom del Pokemon.
     * @param id
     */
    public void showPokemon(int id){
        ObservableList <String> pokemonItems = FXCollections.observableArrayList();
        DAOPokemonDB.listPokemon(pokemonItems, nameText, id);
        pokemonListView.setItems(pokemonItems);
    }

    /**
     * Inserta els tipus en el ListView passant-li la ID del Pokemon.
     * @param id
     */
    public void showTypes(int id){
        ObservableList <String> typesItems = FXCollections.observableArrayList();
        DAOPokemonDB.listType(typesItems,id);
        typeListView.setItems(typesItems);
    }

    /**
     * Inserta els moves en el ListView passant-li la ID del Pokemon.
     * @param id
     */
    public void showMoves(int id){
        ObservableList <String> moveItems = FXCollections.observableArrayList();
        DAOPokemonDB.listMoves(moveItems,id);
        movesListView.setItems(moveItems);
    }

    /**
     * Mostra la imatge del pokemon.
     * @param pokemonImageView
     * @param id
     */
    public void showImage(ImageView pokemonImageView, int id){
        String idToString = "sample/pokemonImages/"+Integer.toString(id)+".png";
        image = new Image(idToString);
        pokemonImageView.setImage(image);
    }

    /**
     * Ensenya la descripció del moviment seleccionat.
     * @param move
     */
    public void showDescriptionMove(String move){
        String [] moveAndDescription = DAOPokemonDB.infoMoves(move);
        descriptionTextArea.setText(moveAndDescription[1]);
    }

    //ListViews seleccionats

    /**
     * Canvia la info del Pokemon seleccionat al List View
     * @param event
     */
    public void onClickPokemonList(Event event){
        pokemonListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        int id = idsSelectedPokemon.get(pokemonListView.getSelectionModel().getSelectedIndex());
                        insertInfo(id);
                    }
                }
        );
    }

    /**
     * Canvia la descripció del moviment seleccionat.
     * @param event
     */
    public void onClickMovesList (Event event){
        movesListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        String move = movesListView.getSelectionModel().getSelectedItem().toString();
                        showDescriptionMove(move);
                    }
                }
        );
    }

    /**
     * Intruccions de la Pokedex.
     * @param actionEvent
     */
    public void howTo(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to...");
        alert.setHeaderText("Catch a Pokemon!");
        alert.setContentText("Just fill the text field with the Pokemon's ID!");
        alert.showAndWait();
    }

    /**
     * Info de l'aplicació
     * @param actionEvent
     */
    public void aboutPokedex(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Pokemon v1.0");
        alert.setContentText("Create your own Pokemon's database!");
        alert.setContentText("Leonardo Martínez - 2016");
        alert.showAndWait();
    }

    /**
     * Tanca programa.
     * @param actionEvent
     */
    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }
}
