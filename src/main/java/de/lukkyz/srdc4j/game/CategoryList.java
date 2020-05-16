package de.lukkyz.srdc4j.game;

import com.google.gson.Gson;
import de.lukkyz.srdc4j.SpeedrunDotCom4J;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CategoryList {

    private Category[] data;

    public static CategoryList forGame(Game g) throws IOException {
        Gson gson = new Gson();
        URL u = new URL(SpeedrunDotCom4J.API_ROOT + "games/" + g.getId() + "/categories");
        HttpURLConnection c = (HttpURLConnection) u.openConnection();
        c.setRequestProperty("User-Agent", SpeedrunDotCom4J.USER_AGENT);
        InputStreamReader r = new InputStreamReader(c.getInputStream());
        CategoryList cl = gson.fromJson(r, CategoryList.class);
        r.close();
        return cl;
    }

    public Category[] getCategories() {
        return data;
    }

}
