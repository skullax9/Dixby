package Dixby;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static Dixby.main.COMMAND;


public class fifaHighRank{
    static String API_KEY = "FIFA API KEYS";
    static JSONObject jsonObj;
    static JSONParser jsonParser;
    static int matchType;
    static int division;
    static String achieveDate;
    static URL url;
    static StringBuilder urlBuilder;
    static String fifaData;
    public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {

        urlBuilder = new StringBuilder("https://api.nexon.co.kr/fifaonline4/v1.0/users/"+COMMAND.substring(6)+"/maxdivision"); /*URL*/

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
        JSONArray memberArray = (JSONArray) jsonObj.get(fifaData);

        for(int i=0 ; i<memberArray.size() ; i++){
            JSONObject tempObj = (JSONObject) memberArray.get(i);
            matchType = (int) tempObj.get("matchType");
            division = (int) tempObj.get("division");
            achieveDate = (String) tempObj.get("achievementDate");
        }
    }
}
