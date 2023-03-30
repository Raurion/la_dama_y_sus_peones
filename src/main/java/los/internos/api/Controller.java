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

public class Controller {
    private String token;
    private String uuid;
    private List<String> whites;
    private List<String> freeWhites;

    private List<String> freeWhiteMoves;
    private List<String> freeBlackMoves;

    //HACER ESTO MÁS TARDE:
    private HashMap<String, List<String>> freeWhitesMoves;
    private HashMap<String, List<String>> freeBlacksMoves;
    private List<String> blacks;
    private List<String> freeBlacks;
    private HashMap<String, Integer> board;
    private String from;
    private String to;
    private String color;

    public Controller() {
        board = new HashMap<>();
        whites = new ArrayList<>();
        freeWhites = new ArrayList<>();
    }

    public void setColorBlack() {
        this.color = "BLACK";
    }

    public void setColorWhite() {
        this.color = "WHITE";
    }


    public void setToken() {
        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> responseToken = null;
        try {
            responseToken = Unirest.post("http://localhost:8080/oauth/token?username=admin&password=admin&grant_type=password")
                    .header("Authorization", "Basic Y2xpZW50SWQ6c2VjcmV0")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("username", "admin")
                    .field("password", "admin")
                    .field("grant_type", "password").asJson();
            this.token = responseToken.getBody().getObject().getString("access_token");
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUuid() {
        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> responseToken = null;
        try {
            HttpResponse<JsonNode> responseUuid = Unirest.post("http://localhost:8080/api/v1/game/create")
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("side", this.color)
                    .field("againstComputer", "false")
                    .field("observers", "false")
                    .asJson();

            if (responseUuid != null) {
                this.uuid = responseUuid.getBody().getObject().getString("response");
            } else {
                System.out.println("NO SE PUDO CREAR LA PARTIDA");
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    /*public Controller() {
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
    }*/

    public void setBoard() {
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
                this.board.put(rawPosition, sides);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setWhites() {
        this.board.forEach((pieza, raza) -> {
            if (raza == 1) {
                whites.add(pieza);
                //System.out.println("key: " + pieza + " value:" + raza);
            }
        });
    }

    public void setBlacks() {
        this.board.forEach((pieza, raza) -> {
            if (raza == 0) {
                blacks.add(pieza);
               // System.out.println("key: " + pieza + " value:" + raza);
            }
        });
    }

    public List<String> getWhites() {
        return whites;
    }

    public List<String> getBlacks() {
        return blacks;
    }

    public HashMap<String, Integer> getBoard() {
        return board;
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

    public void setFreeWhites() {
        try {
            for (int i = 0; i < whites.size(); i++) {
                HttpResponse<String> responseGetAvailableMoves = Unirest.get("http://localhost:8080/api/v1/game/available-moves?from=" + whites.get(i) + "&uuid=" + this.uuid)
                        .header("Authorization", "Bearer " + this.token)
                        .asString();
                String resultado = responseGetAvailableMoves.getBody();
                if (!resultado.equals("[]")) {
                    freeWhites.add(whites.get(i));
                }
            }
        } catch (UnirestException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setFreeBlacks() {
        try {
            for (int i = 0; i < blacks.size(); i++) {
                HttpResponse<String> responseGetAvailableMoves = Unirest.get("http://localhost:8080/api/v1/game/available-moves?from=" + blacks.get(i) + "&uuid=" + this.uuid)
                        .header("Authorization", "Bearer " + this.token)
                        .asString();
                String resultado = responseGetAvailableMoves.getBody();
                if (!resultado.equals("[]")) {
                    freeBlacks.add(blacks.get(i));
                }
            }
        } catch (UnirestException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setRandomFreeWhite() {
        int random = (int) (Math.random() * (freeWhites.size() - 0));
        from = freeWhites.get(random);
    }

    public void setRandomFreeBlack() {
        int random = (int) (Math.random() * (freeBlacks.size() - 0));
        from = freeBlacks.get(random);
    }

    public void setRandomToFromFrom() {
        try {
            HttpResponse<String> responseGetAvailableMoves = Unirest.get("http://localhost:8080/api/v1/game/available-moves?from=" + from + "&uuid=" + this.uuid)
                    .header("Authorization", "Bearer " + this.token)
                    .asString();
            String resultado = responseGetAvailableMoves.getBody();
            String responseBody = responseGetAvailableMoves.getBody();
            JSONArray jsonArray = new JSONArray(responseBody);
            String[] array = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int row = jsonObject.getInt("row");
                String col = jsonObject.getString("col");
                array[i] = col.toUpperCase() + row;
            }
            int random = (int) (Math.random() * (array.length - 0));
            to = array[random];

        } catch (UnirestException ex) {
            throw new RuntimeException(ex);
        }
    }


    /*public String getAvailableMoves() {
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

            //random para elegir una blanca
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
*/

    public void move() {
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

    public String getToken() {
        return this.token;
    }

    public String getUuid() {
        return this.uuid;
    }
}
