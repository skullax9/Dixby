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
import static Dixby.Project_Datas.*;

public class LoL {
    static JSONParser jsonParser;
    static JSONObject jsonObj;
    static String accessID;
    static String nickname;
    static Long level;
    static URL url;
    static StringBuilder urlBuilder;
    static String fifaData;
    public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {
        urlBuilder = new StringBuilder("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/%EC%9D%B8%EA%B0%84%EC%82%AC%EB%83%A5?api_key=RGAPI-f8016a1c-07c1-40bf-8a17-76b146b69c32"); /*URL*/

        url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
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

        System.out.println(fifaData);

//        accessID = (String) jsonObj.get("accessId");
//        nickname = (String) jsonObj.get("nickname");
//        level = (Long) jsonObj.get("level");
//
//        FifaDBConnect fifaDBConnect = new FifaDBConnect();
//        fifaDBConnect.DBConnect();


    }
}
