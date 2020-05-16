package de.lukkyz.racingbot.commands.impl;

import de.lukkyz.racingbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class PbCommand implements Command {

    String pb = "4:20";

    public void action(@NotNull String[] args, MessageReceivedEvent event) {

        if (args.length < 1) {

            sendPbs(event, event.getAuthor().getIdLong());

        } else {

            User user = event.getJDA().getUserByTag("@" + args[0]);
            sendEmbed(event, "", user.getName(), false);

        }

    }

    public void executed(boolean success, MessageReceivedEvent event) {

    }

    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    private void sendPbs(MessageReceivedEvent event, long discordId) {

        sendEmbed(event, "**Glitchless**: " + formattedTime(pb) + "\n**Inbounds**: " + formattedTime(pb) +
                "\n**Inbounds No SLA**: " + formattedTime(pb) + "\n**Out of Bounds**: " + formattedTime(pb),
                "**" + event.getJDA().getUserById(discordId).getName() + "'s Personal Bests:**", false);

    }

    private void sendEmbed(MessageReceivedEvent event, String text, String title, boolean error) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setFooter(event.getAuthor().getName(), event.getAuthor().getAvatarUrl());
        embedBuilder.setColor(error ? 0xFF0000 : 0x1B1B1B);
        embedBuilder.setTitle(error ? "An Error Occurred" : title);
        embedBuilder.setDescription(text + "\n");

        event.getTextChannel().sendMessage(embedBuilder.build()).queue();

    }

    public boolean checkIfTime(@NotNull String input) {

        if (input == null) return false;
        String[] time = input.split(":");

        try {

            int mins = Integer.parseInt(time[0]);
            int seconds = Integer.parseInt(time[1]);

            return seconds <= 60 && (seconds >= 1 || mins >= 1);

        } catch (NumberFormatException nfe) {
            return false;
        }

    }

    public String formattedTime(String input) {

        if (input == null && !checkIfTime(input)) return "NaN";

        String[] time = input.split(":");

        return time[0] + "min " + time[1] + "sec";

    }

}
