package de.lukkyz.racingbot.commands.impl;

import de.lukkyz.racingbot.Util;
import de.lukkyz.racingbot.commands.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class TwitchCommand implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length == 1 && event.getMessage().getMentionedRoles().size() == 0){
            if(Util.updateTwitch(args[0], event.getAuthor().getId())){
                Util.sendEmbed(event, "Twitch url successfully updated.", "Twitch url update", false);
            }else{
                Util.sendEmbed(event, "Failed to update twitch url!", "Twitch url update failed", true);
            }
        }else if(event.getMessage().getMentionedRoles().size() == 1) {
            List<Member> members = event.getGuild().getMembersWithRoles(event.getMessage().getMentionedRoles());
            User[] users = new User[members.size()];
            for(int i = 0; i < members.size(); i++){
                users[i] = members.get(i).getUser();
            }
            String[] twitchLinks = Util.getTwitchForUsers((User[])users);
            String embed = "";
            for (int i = 0; i < twitchLinks.length; i++) {
                embed += users[i].getName() + ": " + twitchLinks[i] + "\n";
            }
            Util.sendEmbed(event, embed, "Twitch links for group", false);
        }else{
            Util.sendEmbed(event, "Invalid arguments. Make sure you entered enough arguments ``-twitch <twitch url>``", "", true);
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
