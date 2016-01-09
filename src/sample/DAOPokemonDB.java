package sample;

import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import java.io.File;
import java.sql.*;

/**
 * Created by leosss on 5/01/16.
 */
public class DAOPokemonDB {

    /**
     * Aprofitem bases de dades creades a M06 per tal de poder guardar els pokemons que es vagin sol·licitant.
     */

    public static void createPokemonDb() {

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:pokemon.db");
            System.out.println("DB created successfully");

            stmt = c.createStatement();

            String create_table_pokemon =
                    "CREATE TABLE POKEMONS " +
                            "(ID        TEXT PRIMARY KEY     NOT NULL," +
                            " NAME      TEXT                 NOT NULL)";

            stmt.executeUpdate(create_table_pokemon);

            String create_table_pokemon_types =
                    "CREATE TABLE POKEMON_TYPES " +
                            "(ID_POKEMON    TEXT    NOT NULL," +
                            " ID_TYPE       TEXT    NOT NULL," +
                            " PRIMARY KEY(ID_POKEMON,ID_TYPE))";

            stmt.executeUpdate(create_table_pokemon_types);

            String create_table_types =
                    "CREATE TABLE TYPES " +
                            "(ID    TEXT  PRIMARY KEY   NOT NULL," +
                            " NAME  TEXT                NOT NULL)";

            stmt.executeUpdate(create_table_types);

            String create_table_pokemon_moves =
                    "CREATE TABLE POKEMON_MOVES " +
                            "(ID_POKEMON    TEXT    NOT NULL," +
                            " ID_MOVES   TEXT    NOT NULL," +
                            " PRIMARY KEY(ID_MOVES,ID_POKEMON))";

            stmt.executeUpdate(create_table_pokemon_moves);

            String create_table_moves =
                    "CREATE TABLE MOVES " +
                            "(ID            TEXT   PRIMARY KEY  NOT NULL," +
                            " DESCRIPTION   TEXT                NOT NULL," +
                            " NAME          TEXT                NOT NULL)";

            stmt.executeUpdate(create_table_moves);


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Tables created successfully.");
    }

    /**
     * Aprofitem de M06 els mètodes ja creats per insertar info a la DB, els pokemons.
     * @param id
     * @param name
     */
    public static void insertPokemon(String id, String name) {

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:pokemon.db");

            String sql_insert =
                    "INSERT INTO POKEMONS " +
                            "(ID, NAME) VALUES (?, ?);";

            PreparedStatement preparedStatement = c.prepareStatement(sql_insert);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {

        }
    }

    /**
     * Aprofitem de M06 els mètodes ja creats per insertar info a la DB, els types.
     * @param id_types
     * @param name
     */
    public static void insertTypes(String id_types, String name) {

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:pokemon.db");

            String sql_insert =
                    "INSERT INTO TYPES" +
                    " (ID, NAME) VALUES (?, ?);";

            PreparedStatement preparedStatement = c.prepareStatement(sql_insert);
            preparedStatement.setString(1, id_types);
            preparedStatement.setString(2, name);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {

        }
    }

    /**
     * Aprofitem de M06 els mètodes ja creats per insertar info a la DB, els moves.
     * @param id_moves
     * @param description
     * @param name
     */
    public static void insertMoves(String id_moves, String description, String name) {

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:pokemon.db");

            String sql_insert =
                    "INSERT INTO MOVES" +
                    " (ID, DESCRIPTION, NAME) VALUES(?, ?,?);";

            PreparedStatement preparedStatement = c.prepareStatement(sql_insert);
            preparedStatement.setString(1, id_moves);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, name);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {

        }
    }

    /**
     * Aprofitem de M06 els mètodes ja creats per insertar info a la DB, en aquest cas la relació Pokemon-Types
     * @param id_pokemon
     * @param id_type
     */
    public static void insertPokemonTypes(String id_pokemon, String id_type) {

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:pokemon.db");

            String sql_insert =
                    "INSERT INTO POKEMON_TYPES" +
                    " (ID_POKEMON, ID_TYPE) VALUES (?, ?);";

            PreparedStatement preparedStatement = c.prepareStatement(sql_insert);
            preparedStatement.setString(1, id_pokemon);
            preparedStatement.setString(2, id_type);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {

        }
    }

    /**
     * Aprofitem de M06 els mètodes ja creats per insertar info a la DB, en aquest cas la relació Pokemon-Moves.
     * @param id_pokemon
     * @param id_moves
     */
    public static void insertPokemonMoves(String id_pokemon,String id_moves){

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:pokemon.db");

            String sql_insert =
                    "INSERT INTO POKEMON_MOVES" +
                    " (ID_POKEMON, ID_MOVES) VALUES (?, ?);";

            PreparedStatement preparedStatement = c.prepareStatement(sql_insert);
            preparedStatement.setString(1, id_pokemon);
            preparedStatement.setString(2, id_moves);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.print("");
        }
    }




    /**
     * Extrau la llista de Pokemons des de la DB i la envia al listview.
     * @param list
     */
    public static void listPokemon(ObservableList list, Text text, int id){

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:pokemon.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM POKEMONS;");

            while (rs.next()) {
                String pokemonName = rs.getString("NAME");
                list.add(pokemonName);
                text.setText(pokemonName);
            }
            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /**
     * Mostra els Tipus del Pokemon triat per ID al ListView de Types.
     * @param list
     * @param id
     */
    public static void listType(ObservableList list,int id){

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:pokemon.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM  TYPES " +
                    "INNER JOIN POKEMON_TYPES ON TYPES.ID = POKEMON_TYPES.ID_TYPE" +
                    " WHERE POKEMON_TYPES.ID_POKEMON = '"+id+"'" );
            while ( rs.next() ) {
                String typeName = rs.getString("NAME");
                list.add(typeName);
            }
            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /**
     * Mostra els Moviments del Pokemon triat per ID al ListView de Moves.
     * @param list
     * @param id
     */
    public static void listMoves(ObservableList list, int id){

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:pokemon.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT MOVES.NAME FROM MOVES\n" +
                    "INNER JOIN POKEMON_MOVES " +
                    "ON MOVES.ID = POKEMON_MOVES.ID_MOVES " +
                    "WHERE POKEMON_MOVES.ID_POKEMON = '"+id+"'"
            );
            while (rs.next()) {
                String movesName = rs.getString("NAME");
                list.add(movesName);
            }
            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static String[] infoMoves(String move){
        String moveName = "";
        String moveDescription = "";

        String arrayMoves[]= new String [2];

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:pokemon.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM MOVES " +
                    "WHERE NAME = '"+move+"'");
            while (rs.next()) {
                moveName = rs.getString("NAME");
                moveDescription=rs.getString("DESCRIPTION");
            }
            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        arrayMoves[0]=moveName;
        arrayMoves[1]=moveDescription;

        return arrayMoves;
    }

    /**
     * Esborra la base de dades.
     */
    public static void deletePokemonDb(){
        File dbPokemon = new File("pokemon.db");
        dbPokemon.delete();
    }

}
