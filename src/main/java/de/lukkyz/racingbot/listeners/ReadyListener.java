package de.lukkyz.racingbot.listeners;

import de.lukkyz.racingbot.Secrets;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ReadyListener extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent event) {

        TextChannel botcommands = event.getJDA().getGuildById(Secrets.DISCORD_SERVER_ID).getTextChannelById(Secrets.DISCORD_BOT_COMMANDS);
        botcommands.sendMessage("Bot is up and running again!").queue();
        //TODO this might get annoying depending on how this gets hosted

    }
}
