package de.lukkyz.racingbot.commands.impl;

import de.lukkyz.racingbot.User;
import de.lukkyz.racingbot.Util;
import de.lukkyz.racingbot.commands.Command;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class GroupCommand implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        System.out.println(event.getMessage().getMentionedRoles().size());
        if(args.length > 1 || event.getMessage().getMentionedRoles().size() > 0){
            List<Role> roles = event.getMessage().getMentionedRoles();
            String embedText = "";
            User[][] grouping = Util.getGroupsFromDatabase(Integer.parseInt(args[0]), event.getJDA());
            if(roles.size() < grouping.length){
                Util.sendEmbed(event, "You need to specify at least " + grouping.length + " groups", "Grouping error!", true);
                return;
            }
            for(int i = 0; i < grouping.length; i++){
                User[] group = grouping[i];
                embedText += "Group " + (i+1) + ": \n";
                for(User participant : group){
                    if(participant == null || participant.getDiscordUser() == null) continue;
                    event.getGuild().addRoleToMember(participant.getDiscordUser().getId(), roles.get(i)).complete();
                    embedText += participant.getDiscordUser().getName() + " -- **" + participant.getTime()[0] + ":" + participant.getTime()[1] + "**\n";
                }
                embedText += "-------------\n";
            }
            Util.sendEmbed(event, embedText, "Grouping for your event", false);
        }else if(args.length == 1){
            String embedText = "";
            User[][] grouping = Util.getGroupsFromDatabase(Integer.parseInt(args[0]), event.getJDA());
            for(int i = 0; i < grouping.length; i++){
                User[] group = grouping[i];
                embedText += "Group " + (i+1) + ": \n";
                for(User participant : group){
                    if(participant == null) continue;
                    embedText += participant.getDiscordUser().getName() + " -- **" + participant.getTime()[0] + ":" + participant.getTime()[1] + "**\n";
                }
                embedText += "-------------\n";
            }
            Util.sendEmbed(event, embedText, "Grouping for your event", false);
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
