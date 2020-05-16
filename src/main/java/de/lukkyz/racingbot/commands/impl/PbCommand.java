package de.lukkyz.racingbot.commands.impl;

import de.lukkyz.racingbot.commands.Command;
import de.lukkyz.srdc4j.game.run.PlacedRun;
import de.lukkyz.srdc4j.users.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PbCommand implements Command {

    public void action(@NotNull String[] args, MessageReceivedEvent event) {

        try {
            sendPbs(event, event.getAuthor().getIdLong(), args[0]);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void executed(boolean success, MessageReceivedEvent event) {

    }

    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    private void sendPbs(MessageReceivedEvent event, long discordId, String profile) throws IOException {

        sendEmbed(event, fetchPbs(profile), profile + "'s Portal PB's:", false);

    }

    private void sendEmbed(MessageReceivedEvent event, String text, String title, boolean error) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setFooter(event.getAuthor().getName(), event.getAuthor().getAvatarUrl());
        embedBuilder.setColor(error ? 0xFF0000 : 0x1B1B1B);
        embedBuilder.setTitle(error ? "An Error Occurred" : title);
        embedBuilder.setDescription(text + "\n");

        event.getTextChannel().sendMessage(embedBuilder.build()).queue();

    }

    public String fetchPbs(String user) throws IOException {
        String pbs = "";

        User sr_user = User.fromID(user);
        PlacedRun[] runs;

        runs = sr_user.getPBs("Portal").getData();

        for (int i = 0; i < runs.length; i++) {

            if (runs[i].getRun().getLevel() == null) {
                pbs += "" + runs[i].getRun().getCategory().getName() + ": **" + runs[i].getRun().getTimes().getPrimary().replace("M", ":").replace("PT", "").replace("S", "") + "** (#" + runs[i].getPlace() + ")\n";
            }

        }

        return pbs;

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
