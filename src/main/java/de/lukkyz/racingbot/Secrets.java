package de.lukkyz.racingbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Secrets {

    /* Bot Token */
    public static final String BOT_TOKEN = "-";
    public static final String SPEEDRUN_COM_API_KEY = "-";
    public static final String connectionString = "";
    public static final String databaseUser = "";
    public static final String databasePassword = "";

    public static Connection getDatabaseConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(connectionString, databaseUser, databasePassword);
        } catch (ClassNotFoundException| SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*Racing with Portals Discord Statics*/
    public static final long DISCORD_SERVER_ID = 692620174209187840L;
    public static final long DISCORD_BOT_COMMANDS = 698683596604571738L;
    public static final long DISCORD_PERSONAL_BESTS = 696127160225890347L;
    public static final long DISCORD_TWITCH_LINKS = 721130169829556314L;

    //event.getJDA().getGuildById(Secrets.DISCORD_SERVER_ID).getTextChannelById(Secrets.DISCORD_BOT_COMMANDS);

}
