package los.internos.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Controller {
    private String token;
    private String uuid;

    public Controller() {
        Unirest.setTimeouts(0, 0);
        try{
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
                    .header("Authorization", "Bearer "+token)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("side", "WHITE")
                    .field("againstComputer", "false")
                    .field("observers", "false")
                    .asJson();

            this.uuid = responseUuid.getBody().getObject().getString("response");
            System.out.println("Match UUID:");
            System.out.println(uuid);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void getGamePositions(){
        try{
            HttpResponse<String> responseGetGamePositions = Unirest.get("http://localhost:8080/api/v1/game/pieces?uuid=" + uuid
                    )
                    .header("Authorization", "Bearer " + token)
                    .asString();


            String responseBody = responseGetGamePositions.getBody();
            JSONArray jsonArray = new JSONArray(responseBody);
            HashMap<String, Integer> positions = new HashMap<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String rawPosition = jsonObject.getString("rawPosition");
                Integer sides = jsonObject.getInt("side");
                positions.put(rawPosition, sides);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
