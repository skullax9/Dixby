package Dixby;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class corona {

        static int yesCnt;
        static int todayCnt;
        static int decideCnt;
        static String createDt;
        static int careCnt;
        static int clearCnt;
        static int deathCnt;
        static int totalCnt;
        static Element element;

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, ParseException {

        // 날짜 포맷
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

        // 오늘 날짜
        Calendar todayCal = Calendar.getInstance();
        String today = ymd.format(todayCal.getTime());
        // 어제 날짜
        Calendar yesCal = Calendar.getInstance();
        yesCal.add(Calendar.DATE,-1);
        String yesterday = ymd.format(yesCal.getTime());

        NodeList nList = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=SERVICE KEYS"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(yesterday, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*검색할 생성일 범위의 종료*/

        URL url = new URL(urlBuilder.toString());
        Document document = builder.parse(urlBuilder.toString());
        document.normalize();

        nList = document.getElementsByTagName("item");

        for(int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);

            //실질적으로 Element를 조작하기 위해 값을 넣어준다.
            element = (Element) node;

            String eleDate = getTagValue("createDt",element);
            String extDate= eleDate.substring(0,10);
            String ymdDate = extDate.replace("-","");

            if (today.equals(ymdDate)) {
                createDt = getTagValue("createDt",element); // 기준 날짜
                careCnt = Integer.parseInt(getTagValue("careCnt",element)); // 치료중인 확진자 수
                clearCnt = Integer.parseInt(getTagValue("clearCnt",element)); // 완치된 확진자 수
                deathCnt = Integer.parseInt(getTagValue("deathCnt",element)); // 사망한 확진자 수
                totalCnt = Integer.parseInt(getTagValue("decideCnt",element)); // 전체 확진자 수
            } else {
                yesCnt = Integer.parseInt(getTagValue("decideCnt",element));
            }

            decideCnt = totalCnt - yesCnt;
        }

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //System.out.println("Response code: " + conn.getResponseCode());
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
        //System.out.println(sb.toString());
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node value = (Node) nList.item(0);
        if(value == null)
            return null;
        return value.getNodeValue();
    }
}