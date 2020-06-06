package de.lukkyz.racingbot;

import de.lukkyz.srdc4j.game.run.PlacedRun;
import de.lukkyz.srdc4j.users.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Util {

    public static void sendEmbed(MessageReceivedEvent event, String text, String title, boolean error) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setFooter(event.getAuthor().getName(), event.getAuthor().getAvatarUrl());
        embedBuilder.setColor(error ? 0xFF0000 : 0x1B1B1B);
        embedBuilder.setTitle(error ? "An Error Occurred" : title);
        embedBuilder.setDescription(text + "\n");

        event.getTextChannel().sendMessage(embedBuilder.build()).queue();

    }

    public static String fetchPbs(String user) throws IOException {
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

    public static String fetchPbs(String user, String category) throws IOException {
        String pbs = "";

        User sr_user = User.fromID(user);
        PlacedRun[] runs;

        runs = sr_user.getPBs("Portal").getData();

        for (PlacedRun run : runs) {

            if (run.getRun().getLevel() == null && run.getRun().getCategory().getName().endsWith(category)) {
                pbs += run.getRun().getTimes().getPrimary().replace("M", ":").replace("PT", "").replace("S", "");
            }

        }

        return pbs;

    }

    public static void registerUser(String discordId, int eventId){
        registerUser(discordId, eventId, "");
    }

    public static void registerUser(String discordId, int eventId, String srcomUser){
        Connection conn = Secrets.getDatabaseConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (`discordId`, `speedrunComUser`, `registered`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE registered=?" + (!srcomUser.isEmpty() ? ", speedrunComUser=?" : ""));
            stmt.setString(1, discordId);
            stmt.setString(2, srcomUser);
            stmt.setInt(3, eventId);
            stmt.setInt(4, eventId);
            if(!srcomUser.isEmpty()) stmt.setString(5, srcomUser);
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
