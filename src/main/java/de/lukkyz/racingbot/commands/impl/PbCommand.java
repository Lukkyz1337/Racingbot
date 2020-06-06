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

        if (args.length == 1) {
            try {
                sendPbs(event, event.getAuthor().getIdLong(), args[0]);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            sendEmbed(event, "Invalid arguments. Use ``-pb <speedrun.com user>", "", true);
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

}
