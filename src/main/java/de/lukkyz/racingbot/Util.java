package de.lukkyz.racingbot;

import de.lukkyz.srdc4j.game.run.PlacedRun;
import de.lukkyz.srdc4j.users.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

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

            if (run.getRun().getLevel() == null && run.getRun().getCategory().getName().toLowerCase().endsWith(category.toLowerCase().equals("nosla") ? "no sla" : category.toLowerCase())) {
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

    public static de.lukkyz.racingbot.User[][] getGroupsFromDatabase(int eventId, JDA jda){
        Connection conn = Secrets.getDatabaseConnection();
        if(conn != null)
        try{
            PreparedStatement categoryStmt = conn.prepareStatement("SELECT category FROM events where id=?");
            categoryStmt.setInt(1, eventId);
            ResultSet rs = categoryStmt.executeQuery();
            if(rs.next()){
                //Users who have a time in database
                PreparedStatement stmt = conn.prepareStatement("SELECT discordId, MIN(min) as min, MIN(sec) as sec FROM " + rs.getString(1) + ", users WHERE (userid, min) IN (SELECT userid, MIN(`min`) FROM " + rs.getString(1) + " WHERE `userid` IN (SELECT id FROM users WHERE registered=?)  GROUP BY `userid`) AND users.id = userid GROUP BY userid ORDER BY min, sec;");
                stmt.setInt(1, eventId);
                ResultSet rs2 = stmt.executeQuery();
                ArrayList<de.lukkyz.racingbot.User> participantList = new ArrayList<>();
                while(rs2.next()){
                    net.dv8tion.jda.api.entities.User user = null;
                    try{
                        user = jda.retrieveUserById(rs2.getString(1)).complete();
                    }catch(Exception e){
                        user = null;
                    }
                    int[] time = new int[]{Integer.parseInt(rs2.getString(2)), Integer.parseInt(rs2.getString(3))};
                    de.lukkyz.racingbot.User participant = new de.lukkyz.racingbot.User(user, time);
                    participantList.add(participant);
                }
                //Users who have srdc linked but no time in database
                PreparedStatement stmt2 = conn.prepareStatement("SELECT discordId, speedrunComUser from users where registered = ? and id not in (SELECT userid FROM " + rs.getString(1) + " GROUP BY userid) and NOT ISNULL(speedrunComUser)");
                stmt2.setInt(1, eventId);
                ResultSet rs3 = stmt2.executeQuery();
                while(rs3.next()){
                    net.dv8tion.jda.api.entities.User user = null;
                    try{
                        user = jda.retrieveUserById(rs3.getString(1)).complete();
                    }catch(Exception e){
                        user = null;
                    }
                    try {
                        String tString = fetchPbs(rs3.getString(2), rs.getString(1));
                        de.lukkyz.racingbot.User participant = new de.lukkyz.racingbot.User(user, new int[]{Integer.parseInt(tString.split(":")[0]), Math.round(Float.parseFloat(tString.split(":")[1]))});
                        participantList.add(participant);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                participantList.sort(de.lukkyz.racingbot.User::compareTo);
                de.lukkyz.racingbot.User[][] groups = new de.lukkyz.racingbot.User[(int) Math.ceil(participantList.size() / 5d)][];
                for(int i = 0; i < Math.ceil(participantList.size() / 5d); i++){
                    groups[i] = new de.lukkyz.racingbot.User[5];
                    for(int j = 0; j < 5 && i * 5 + j < participantList.size(); j++){
                        groups[i][j] = participantList.get(i*5+j);
                    }
                }
                return groups;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateTwitch(String url, String discordId) {
        Connection conn = Secrets.getDatabaseConnection();
        if(conn == null) return false;
        try{
            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET `twitch`=? WHERE discordId=?");
            stmt.setString(1, url);
            stmt.setString(2, discordId);
            stmt.execute();
            return stmt.getUpdateCount()==1;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static String[] getTwitchForUsers(net.dv8tion.jda.api.entities.User[] users){
        String[] twitchLinks = new String[users.length];
        try(Connection conn = Secrets.getDatabaseConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT twitch FROM users WHERE discordId=?")){
                for (int i = 0; i < users.length; i++) {
                    stmt.setString(1, users[i].getId());

                    ResultSet set = stmt.executeQuery();
                    if (set.next())
                        twitchLinks[i] = set.getString(1);
                    else
                        twitchLinks[i] = "Twitch link not found!";
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return twitchLinks;
    }
}
