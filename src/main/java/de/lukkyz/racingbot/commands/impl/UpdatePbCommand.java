package de.lukkyz.racingbot.commands.impl;

import de.lukkyz.racingbot.Secrets;
import de.lukkyz.racingbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class UpdatePbCommand implements Command {

    //TODO either manual submission or fetch from srdc api

    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    public void action(@NotNull String[] args, MessageReceivedEvent event) {

        if (args.length > 1 && checkIfTime(args[1])) {
            int[] time = timeArray(args[1]);
            try {
                AuditableRestAction<Void> voidAuditableRestAction = event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("718192130803105862"));
                voidAuditableRestAction.queue();
                if (args[0].equalsIgnoreCase("glitchless")) {

                    Connection conn = Secrets.getDatabaseConnection();
                    PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO glitchless (`userid`, `min`, `sec`, `dateUpdated`) VALUES (?, ?, ?, NOW())");
                    PreparedStatement getUser = conn.prepareStatement("SELECT id FROM users WHERE discordId=?");
                    getUser.setString(1, event.getAuthor().getId());
                    ResultSet res = getUser.executeQuery();
                    if(res.next()) {
                        insertStmt.setInt(1, res.getInt("id"));
                    }else{
                        PreparedStatement addUser = conn.prepareStatement("INSERT INTO users (`discordId`) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                        addUser.setString(1, event.getAuthor().getId());
                        addUser.executeUpdate();
                        res = addUser.getGeneratedKeys();
                        if(res.next()){
                            insertStmt.setInt(1, res.getInt(1));
                        }else throw new SQLException("Could not find user!");
                        addUser.close();
                    }
                    insertStmt.setInt(2, time[1]);//minutes
                    insertStmt.setInt(3, time[0]);//seconds
                    insertStmt.execute();
                    getUser.close();
                    insertStmt.close();
                    conn.close();
                    sendSuccessfulUpdate(event, "Glitchless", formattedTime(args[1]));
                } else if (args[0].equalsIgnoreCase("inbounds")) {


                    Connection conn = Secrets.getDatabaseConnection();
                    PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO inbounds (`userid`, `min`, `sec`, `dateUpdated`) VALUES (?, ?, ?, NOW())");
                    PreparedStatement getUser = conn.prepareStatement("SELECT id FROM users WHERE discordId=?");
                    getUser.setString(1, event.getAuthor().getId());
                    ResultSet res = getUser.executeQuery();
                    if(res.next()) {
                        insertStmt.setInt(1, res.getInt("id"));
                    }else{
                        PreparedStatement addUser = conn.prepareStatement("INSERT INTO users (`discordId`) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                        addUser.setString(1, event.getAuthor().getId());
                        addUser.executeUpdate();
                        res = addUser.getGeneratedKeys();
                        if(res.next()){
                            insertStmt.setInt(1, res.getInt(1));
                        }else throw new SQLException("Could not find user!");
                        addUser.close();
                    }
                    insertStmt.setInt(2, time[1]);//minutes
                    insertStmt.setInt(3, time[0]);//seconds
                    insertStmt.execute();
                    getUser.close();
                    insertStmt.close();
                    conn.close();
                    sendSuccessfulUpdate(event, "Inbounds", formattedTime(args[1]));
                } else if (args[0].equalsIgnoreCase("nosla")) {


                    Connection conn = Secrets.getDatabaseConnection();
                    PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO nosla (`userid`, `min`, `sec`, `dateUpdated`) VALUES (?, ?, ?, NOW())");
                    PreparedStatement getUser = conn.prepareStatement("SELECT id FROM users WHERE discordId=?");
                    getUser.setString(1, event.getAuthor().getId());
                    ResultSet res = getUser.executeQuery();
                    if(res.next()) {
                        insertStmt.setInt(1, res.getInt("id"));
                    }else{
                        PreparedStatement addUser = conn.prepareStatement("INSERT INTO users (`discordId`) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                        addUser.setString(1, event.getAuthor().getId());
                        addUser.executeUpdate();
                        res = addUser.getGeneratedKeys();
                        if(res.next()){
                            insertStmt.setInt(1, res.getInt(1));
                        }else throw new SQLException("Could not find user!");
                        addUser.close();
                    }
                    insertStmt.setInt(2, time[1]);//minutes
                    insertStmt.setInt(3, time[0]);//seconds
                    insertStmt.execute();
                    getUser.close();
                    insertStmt.close();
                    conn.close();
                    sendSuccessfulUpdate(event, "Inbounds No SLA", formattedTime(args[1]));
                } else if (args[0].equalsIgnoreCase("oob")) {


                    Connection conn = Secrets.getDatabaseConnection();
                    PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO oob (`userid`, `min`, `sec`, `dateUpdated`) VALUES (?, ?, ?, NOW())");
                    PreparedStatement getUser = conn.prepareStatement("SELECT id FROM users WHERE discordId=?");
                    getUser.setString(1, event.getAuthor().getId());
                    ResultSet res = getUser.executeQuery();
                    if(res.next()) {
                        insertStmt.setInt(1, res.getInt("id"));
                    }else{
                        PreparedStatement addUser = conn.prepareStatement("INSERT INTO users (`discordId`) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                        addUser.setString(1, event.getAuthor().getId());
                        addUser.executeUpdate();
                        res = addUser.getGeneratedKeys();
                        if(res.next()){
                            insertStmt.setInt(1, res.getInt(1));
                        }else throw new SQLException("Could not find user!");
                        addUser.close();
                    }
                    insertStmt.setInt(2, time[1]);//minutes
                    insertStmt.setInt(3, time[0]);//seconds
                    insertStmt.execute();
                    getUser.close();
                    insertStmt.close();
                    conn.close();
                    sendSuccessfulUpdate(event, "Out of Bounds", formattedTime(args[1]));
                } else {

                    sendEmbed(event, "Invalid Category. Valid Categories are ``glitchless``, ``inbounds``, ``nosla``, ``oob``", "Unknown Category", false);

                }
            }catch(SQLException e){
                sendEmbed(event, e.getMessage(), "An error occured while updating your PB!",true);
            }
        } else {

            sendEmbed(event, "Invalid arguments. Make sure you entered enough arguments ``-updatepb <category> <time>``\nMake sure you put in a valid time.", "", true);

        }

    }

    public void executed(boolean success, MessageReceivedEvent event) {

    }

    private void sendSuccessfulUpdate(MessageReceivedEvent event, String category, String time) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setFooter(event.getAuthor().getName(), event.getAuthor().getAvatarUrl());
        embedBuilder.setColor(0x1b1b1b);
        embedBuilder.setTitle("Updated " + category + " PB");
        embedBuilder.setDescription("Updated your **" + category + "** Personal Best to **" + time + "**.\n");

        event.getTextChannel().sendMessage(embedBuilder.build()).queue();

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
        if (input == null && !checkIfTime(input)) return "null";

        String[] time = input.split(":");
        String mins = time[0], sec = time[1];

        return mins + "min " + sec + "sec";

    }

    public int[] timeArray(String input){
        String[] time = input.split(":");
        int[] times = new int[time.length];
        for(int i = 0; i < time.length; i++){
            times[times.length-1-i] = Integer.parseInt(time[i]);//Order array from finest to coarsest unit
        }
        return times;
    }

}
