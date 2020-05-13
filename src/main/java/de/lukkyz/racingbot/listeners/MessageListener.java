package de.lukkyz.racingbot.listeners;

import de.lukkyz.racingbot.Racingbot;
import de.lukkyz.racingbot.Secrets;
import de.lukkyz.racingbot.commands.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().startsWith(Racingbot.PREFIX) &&
                event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId() &&
                event.getTextChannel() == event.getJDA().getGuildById(Secrets.DISCORD_SERVER_ID)
                        .getTextChannelById(Secrets.DISCORD_BOT_COMMANDS)) {
            CommandHandler.handleCommand(CommandHandler.parser.parse(event.getMessage().getContentRaw(), event));
        }

    }
}
