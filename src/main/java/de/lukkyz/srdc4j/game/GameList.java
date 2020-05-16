package de.lukkyz.srdc4j.game;

import com.google.gson.Gson;
import de.lukkyz.srdc4j.SpeedrunDotCom4J;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GameList {

    private Game[] data;

    public static GameList withName(String name) throws IOException {
        Gson g = new Gson();
        URL u = new URL(SpeedrunDotCom4J.API_ROOT + "games?name=" + name.replaceAll(" ", "_"));
        HttpURLConnection c = (HttpURLConnection) u.openConnection();
        c.addRequestProperty("User-Agent", SpeedrunDotCom4J.USER_AGENT);
        InputStreamReader r = new InputStreamReader(c.getInputStream());
        GameList l = g.fromJson(r, GameList.class);
        r.close();
        return l;
    }

    public Game[] getGames() {
        return data;
    }

}
