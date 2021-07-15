package Dixby;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static Dixby.main.COMMAND;


public class fifaUserSearch {
    static String API_KEY = "FIFA API KEYS";
    static JSONParser jsonParser;
    static String accessID;
    static String nickname;
    static Long level;
    static URL url;
    static StringBuilder urlBuilder;
    static String fifaData;
    public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {

        urlBuilder = new StringBuilder("https://api.nexon.co.kr/fifaonline4/v1.0/users?nickname="+URLEncoder.encode(COMMAND.substring(4),"UTF-8")); /*URL*/

        url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", API_KEY);
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        fifaData = sb.toString();

        jsonParser = new JSONParser();
        jsonObj = (JSONObject) jsonParser.parse(fifaData);

        accessID = (String) jsonObj.get("accessId");
        nickname = (String) jsonObj.get("nickname");
        level = (Long) jsonObj.get("level");

    }
}
