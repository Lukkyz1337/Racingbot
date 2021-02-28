package de.lukkyz.racingbot.commands.impl;

import de.lukkyz.racingbot.Util;
import de.lukkyz.racingbot.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

public class RegisterCommand implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        AuditableRestAction<Void> voidAuditableRestAction = event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("718192130803105862"));
        switch(args.length){
            case 1:
                Util.registerUser(event.getAuthor().getId(), 4, args[0]);
                Util.sendEmbed(event, "You have been registered in the next tournament. And your speedrun.com account has been linked.", "Tournament registration", false);
                voidAuditableRestAction.queue();
                break;
            case 0:
                Util.registerUser(event.getAuthor().getId(), 4);
                Util.sendEmbed(event, "You have been registered in the next tournament.", "Tournament registration", false);
                voidAuditableRestAction.queue();
                break;
            default:
                Util.sendEmbed(event, "Invalid arguments. Use ``-register`` or ``-register <speedrun.com user>``", "Invalid arguments!", true);
                break;
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
