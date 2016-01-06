package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by leosss on 18/12/15.
 * He decidit que finalment no utilitzarem classes de Pokemons sino que portarem a terme la feina feta a classe de M06
 * i fer la crida a la API, per després anar guardant a la DB SQLite que crearem.
 */

public class PokemonJSON {

    private static String Pokemons;
    private static String Moves;
    private static String urlBase ="http://pokeapi.co";

    /**
     *
     * @param urlToRead
     * @return
     * @throws Exception
     */
    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        conn.disconnect();
        return result.toString();
    }

    /**
     * L'usuari tria la ID del Pokemon a l'aplicació, que es passa per aquest mètode, i es fa la crida a la API
     * extraient tota la informació del Pokemon i guardant-la a la DB.
     * @param idPokemon
     * @throws Exception
     */
    public static void getPokemonFromJson (int idPokemon) throws Exception {

        String getPokemonApi = urlBase+"/api/v1/pokemon/" + idPokemon;
        try {
            //Extraure Pokemons
            Pokemons = getHTML(getPokemonApi);
            Object object = JSONValue.parse(Pokemons);
            JSONObject objectJSimple = (JSONObject) object;

            String idPokemonStr = String.valueOf(idPokemon);
            String namePokemon = (String) objectJSimple.get("name");

            //Guarda en DB el Pokemon.
            DAOPokemonDB.insertPokemon(idPokemonStr,namePokemon);

            //Extraure Types.
            JSONArray jsonArrayTypes = (JSONArray) objectJSimple.get("types");
            String type;
            String idType;
            for (int j = 0; j < jsonArrayTypes.size(); j++) {
                JSONObject jsonObject2= (JSONObject) jsonArrayTypes.get(j);
                idType = ((String) jsonObject2.get("resource_uri"));
                type = ((String) jsonObject2.get("name"));

                //Guarda a la DB els Types i la relació Pokemon-Types
                DAOPokemonDB.insertTypes(idType, type);
                DAOPokemonDB.insertPokemonTypes(idPokemonStr, idType);
            }

            //Extraure Moves.
            JSONArray arrayJsonMoves = (JSONArray) objectJSimple.get("moves");
            for(int k=0; k<arrayJsonMoves.size();k++){

                JSONObject jom = (JSONObject)arrayJsonMoves.get(k);

                String idMoves = ((String)jom.get("resource_uri"));
                String nameMoves = ((String)jom.get("name"));

                Moves=getHTML(urlBase+idMoves);
                Object objmov = JSONValue.parse(Moves);
                JSONObject objSimpleMov = (JSONObject) objmov;
                String description = (String)objSimpleMov.get("description");

                //Guarda a la DB els Moves i la relació Pokemon-Moves
                DAOPokemonDB.insertMoves(idMoves, description, nameMoves);
                DAOPokemonDB.insertPokemonMoves(idPokemonStr,idMoves);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



