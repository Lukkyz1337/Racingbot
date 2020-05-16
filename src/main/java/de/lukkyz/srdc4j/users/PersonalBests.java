package de.lukkyz.srdc4j.users;

import com.google.gson.Gson;
import de.lukkyz.srdc4j.SpeedrunDotCom4J;
import de.lukkyz.srdc4j.game.run.PlacedRun;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PersonalBests {

    private PlacedRun[] data;

    public static PersonalBests forUser(User user, String game) throws IOException {
        Gson g = new Gson();
        URL u = new URL(SpeedrunDotCom4J.API_ROOT + "users/" + user.getId() + "/personal-bests" + (game != null ? "?game=" + game : ""));
        HttpURLConnection c = (HttpURLConnection) u.openConnection();
        c.setRequestProperty("User-Agent", SpeedrunDotCom4J.USER_AGENT);
        InputStreamReader r = new InputStreamReader(c.getInputStream());
        PersonalBests pb = g.fromJson(r, PersonalBests.class);
        r.close();
        return pb;
    }

    public PlacedRun[] getData() {
        return data;
    }

}
