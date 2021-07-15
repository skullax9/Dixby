import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class main extends ListenerAdapter {
    static String COMMAND;
    public static void main(String[] Args) throws LoginException {
        JDA jda= JDABuilder.createDefault("ODMxMDI2OTk0MzQ2NTkwMjEw.YHPQMw.OlIjlXfACXdj6IvGtwbUlpLxurc").build(); //기본 jda

        jda.addEventListener(new main()); //jda에 이벤트를 감지하는 리스너를 넣는다.
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        COMMAND = event.getMessage().getContentRaw();
        if (COMMAND.contains("!명령어")) {

        } else if(COMMAND.contains("!피파 ")){
            String FIFAID = COMMAND.substring(4);
            event.getChannel().sendMessage(FIFAID).queue();
        } else if (COMMAND.contains("!코로나")) {

        }
    }
}