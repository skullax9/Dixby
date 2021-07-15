package Dixby;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.xml.sax.SAXException;

import javax.security.auth.login.LoginException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

import static Dixby.corona.*;
import static Dixby.fifaHighRank.*;
import static Dixby.fifaUserSearch.*;
import static Dixby.fifaFunctions.*;

public class main extends ListenerAdapter {
    public static String COMMAND;
    fifaFunctions fifaFunctions = new fifaFunctions();
    public static void main(String[] Args) throws LoginException {
        JDA jda= JDABuilder.createDefault("DISCORD BOT TOKENS").build(); //기본 jda

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
                fifaHighRank.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            fifaFunctions.matchType(matchType);
            fifaFunctions.matchDivision(division);

            event.getChannel().sendMessage(
                    "경기 타입: "+matchName+"\n"+
                            "최고 등급: "+divisionName+"\n"
            ).queue();

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
                    ":calendar:기준 날짜: "+createDt+"\n"+
                    "전일 대비 확진자 "+decideCnt+"명 증가\n"+
                    "총 확진자 수: "+totalCnt+"명\n"+
                    "치료중인 확진자 수: "+careCnt+"명\n"+
                    "완치된 확진자 수: "+clearCnt+"명\n"+
                    "사망한 확진자 수: "+deathCnt+"명"
                    ).queue();
        }
    }

}