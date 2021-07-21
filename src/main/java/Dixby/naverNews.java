package Dixby;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import Dixby.Project_Datas.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static Dixby.Project_Datas.*;
import static Dixby.main.*;

public class naverNews {
    static String news;
    static JSONArray newsArray;
    static JSONObject jsonObj;
    public static void main(String[] args) {
        try {
            String text = URLEncoder.encode(COMMAND.substring(4), "UTF-8"); //검색어";
            String apiURL = "https://openapi.naver.com/v1/search/news.json?query="+ text + "&display=5&start=1&sort=date"; // 뉴스의 json 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", Naver_ClientID);
            con.setRequestProperty("X-Naver-Client-Secret", Naver_ClientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            news = response.toString();

            JSONParser jsonParser = new JSONParser();
            jsonObj = (JSONObject) jsonParser.parse(news);
            newsArray = (JSONArray) jsonObj.get("items");

            System.out.println("----------------------------");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

