package los.internos.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Controller {
    private String token;
    private String uuid;
    private List<String> whites;
    private HashMap<String, Integer> positions;
    private String from;
    private String to;

    public Controller() {
        Unirest.setTimeouts(0, 0);
        try {
            //token
            HttpResponse<JsonNode> responseToken = Unirest.post("http://localhost:8080/oauth/token?username=admin&password=admin&grant_type=password")
                    .header("Authorization", "Basic Y2xpZW50SWQ6c2VjcmV0")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("username", "admin")
                    .field("password", "admin")
                    .field("grant_type", "password").asJson();
            this.token = responseToken.getBody().getObject().getString("access_token");

            //uuid
            HttpResponse<JsonNode> responseUuid = Unirest.post("http://localhost:8080/api/v1/game/create")
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("side", "WHITE")
                    .field("againstComputer", "false")
                    .field("observers", "false")
                    .asJson();

            this.uuid = responseUuid.getBody().getObject().getString("response");
            System.out.println("Match UUID:");
            System.out.println(uuid);

            positions = new HashMap<>();
            setGamePositions();

            whites = new ArrayList<>();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setGamePositions() {
        try {
            HttpResponse<String> responseGetGamePositions = Unirest.get("http://localhost:8080/api/v1/game/pieces?uuid=" + uuid
                    )
                    .header("Authorization", "Bearer " + token)
                    .asString();


            String responseBody = responseGetGamePositions.getBody();
            JSONArray jsonArray = new JSONArray(responseBody);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String rawPosition = jsonObject.getString("rawPosition");
                Integer sides = jsonObject.getInt("side");
                this.positions.put(rawPosition, sides);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setWhites() {
        this.positions.forEach((pieza, raza) -> {
            if (raza == 1) {
                whites.add(pieza);
                System.out.println("key: " + pieza + " value:" + raza);
            }
        });
    }

    public List<String> getWhites() {
        return whites;
    }

    public HashMap<String, Integer> getPositions() {
        return positions;
    }

    /*public boolean getIfAvailableMoves(String from){
        try {
            HttpResponse<String> responseGetAvailableMoves = Unirest.get("http://localhost:8080/api/v1/game/available-moves?from="+ from +"&uuid="+this.uuid)
                    .header("Authorization", "Bearer "+this.token)
                    .asString();
            String resultado = responseGetAvailableMoves.getBody();
            if(resultado.equals("[]")){
                return true;
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }*/

    public String getAvailableMoves() {
        try {
            ArrayList<String> whitesCanMove = new ArrayList<>();
            for (int i = 0; i < whites.size(); i++) {
                HttpResponse<String> responseGetAvailableMoves = Unirest.get("http://localhost:8080/api/v1/game/available-moves?from=" + whites.get(i) + "&uuid=" + this.uuid)
                        .header("Authorization", "Bearer " + this.token)
                        .asString();
                String resultado = responseGetAvailableMoves.getBody();
                if (!resultado.equals("[]")) {
                    whitesCanMove.add(whites.get(i));
                }
            }

            //ramdon para elegir una blanca
            int random = (int) (Math.random() * (whites.size() - 0));
            HttpResponse<String> responseGetAvailableMoves = Unirest.get("http://localhost:8080/api/v1/game/available-moves?from=" + whites.get(random) + "&uuid=" + this.uuid)
                    .header("Authorization", "Bearer " + this.token)
                    .asString();
            String resultado = responseGetAvailableMoves.getBody();
            //aqui pongo el from que se va a usar
            from = whites.get(random);

            //aqui voy a coger las posiciones
            String responseBody = responseGetAvailableMoves.getBody();
            JSONArray jsonArray = new JSONArray(responseBody);
            //random = (int)(Math.random()*(jsonArray.length()-0));
            // System.out.println(random);

            //  int i = random;
            String[] array = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int row = jsonObject.getInt("row");
                String col = jsonObject.getString("col");
                array[i] = col.toUpperCase() + row;
            }

            random = (int) (Math.random() * (array.length - 0));
            System.out.println(random);

            to = array[random];

            return whitesCanMove.toString();
        } catch (UnirestException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void getMovablePieces() {

    }

    public void moveToFrom() {
        try {
            HttpResponse<String> responseMoveTo = Unirest.post("http://localhost:8080/api/v1/game/move?uuid=" + uuid
                            + "&from=" + this.from + "&to=" + this.to)
                    .header("Authorization", "Bearer " + token)
                    .asString();

        } catch (Exception e) {
        }
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
