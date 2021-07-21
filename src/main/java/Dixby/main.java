package Dixby;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

import javax.security.auth.login.LoginException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

import static Dixby.fifaDBConnect.*;
import static Dixby.corona.*;
import static Dixby.fifaHighRank.*;
import static Dixby.fifaUserSearch.*;
import static Dixby.Functions.*;
import static Dixby.Project_Datas.*;
import static Dixby.naverNews.*;

public class main extends ListenerAdapter {
    public static String COMMAND;
    public static String cleanTitle;
    public static String cleanDes;
    Functions Functions = new Functions();
    fifaDBConnect fifaDBConnect = new fifaDBConnect();
    public static void main(String[] Args) throws LoginException {
        JDA jda= JDABuilder.createDefault(Discord_Keys).build(); //기본 jda

        jda.addEventListener(new main()); //jda에 이벤트를 감지하는 리스너를 넣는다.
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        COMMAND = event.getMessage().getContentRaw();
        if (COMMAND.contains("!명령어")) {
            event.getChannel().sendMessage("현재 사용 가능한 명령어 종류입니다. ~").queue();
        } else if(COMMAND.contains("!피파 ")){
            try {
                fifaUserSearch.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            event.getChannel().sendMessage(
                    "엑세스 아이디: "+accessID+"\n"+
                    "닉네임: "+nickname+"\n"+
                    "레벨: "+level+"\n"
            ).queue();

        } else if (COMMAND.contains("!최고등급 ")) {
            try {
                fifaDBConnect.UserHighRank();
                fifaHighRank.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            Functions.matchType(matchType);
            Functions.matchDivision(division);

            if (matchType == 0 || achieveDate == null) {
                event.getChannel().sendMessage(
                        ":x: "+nick+"님의 최고등급 기록이 없습니다."
                ).queue();
            } else {
                event.getChannel().sendMessage(
                        nick+"님의 최고등급 기록입니다.\n"+
                                "경기 타입: "+matchName+"\n"+
                                "최고 등급: "+divisionName+"\n"+
                                "달성 일자: "+achieveDate
                ).queue();
            }

        } else if (COMMAND.contains("!롤 ")){

        } else if (COMMAND.contains("!코로나")) {
            try {
                corona.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            event.getChannel().sendMessage(
                    ":calendar: 기준 날짜: "+createDt+"\n"+
                    ":arrow_up: 전일 대비 확진자 "+decideCnt+"명 증가\n"+
                    ":mask: 총 확진자 수: "+totalCnt+"명\n"+
                    ":sneezing_face: 치료중인 확진자 수: "+careCnt+"명\n"+
                    ":white_check_mark: 완치된 확진자 수: "+clearCnt+"명\n"+
                    ":pray: 사망한 확진자 수: "+deathCnt+"명"
                    ).queue();
        } else if (COMMAND.contains("!뉴스 ")){
            naverNews.main(null);
            for(int i=0 ; i<newsArray.size() ; i++){
                JSONObject tempObj = (JSONObject) newsArray.get(i);
                cleanTitle = tempObj.get("title").toString();
                cleanDes = tempObj.get("description").toString();
                String replaceTitle = cleanTitle
                        .replace("<b>","")
                        .replace("</b>","")
                        .replace("&quot;","");
                String replaceDes = cleanDes
                        .replace("<b>","")
                        .replace("</b>","")
                        .replace("&quot;","");

                event.getChannel().sendMessage(
                           (i+1)+"번째 기사 제목: "+replaceTitle+"\n"+
                                (i+1)+"번째 기사 요약: "+replaceDes+"\n"+
                                (i+1)+"번째 기사 날짜: "+tempObj.get("pubDate")+"\n"+
                                (i+1)+"번째 기사 더보기: "+tempObj.get("originallink")+"\n\n\n"
                ).queue();
            }
        }
    }

}