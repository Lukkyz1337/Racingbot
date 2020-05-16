package de.lukkyz.srdc4j.users;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import de.lukkyz.srdc4j.SpeedrunDotCom4J;
import de.lukkyz.srdc4j.data.Link;
import de.lukkyz.srdc4j.data.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class User {

    public String id;
    private Map<String, String> names;
    private String weblink;
    @SerializedName("name-sytle")
    private NameStyle nameStyle;
    private String role;
    private String signup;
    private Location location;
    private Resource twitch;
    private Resource hitbox;
    private Resource youtube;
    private Resource twitter;
    @SerializedName("speedrunslive")
    private Resource srl;
    private Link[] links;

    public static User fromID(String id) throws IOException {
        Gson g = new Gson();
        URL u = new URL(SpeedrunDotCom4J.API_ROOT + "users/" + id);
        HttpURLConnection c = (HttpURLConnection) u.openConnection();
        c.addRequestProperty("User-Agent", SpeedrunDotCom4J.USER_AGENT);
        InputStreamReader r = new InputStreamReader(c.getInputStream());
        UserData d = g.fromJson(r, UserData.class);
        r.close();
        return d.data;
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getNames() {
        return names;
    }

    public String getWeblink() {
        return weblink;
    }

    public NameStyle getNameStyle() {
        return nameStyle;
    }

    public String getRole() {
        return role;
    }

    public String getSignup() {
        return signup;
    }

    public Location getLocation() {
        return location;
    }

    public Resource getTwitch() {
        return twitch;
    }

    public Resource getHitbox() {
        return hitbox;
    }

    public Resource getYoutube() {
        return youtube;
    }

    public Resource getTwitter() {
        return twitter;
    }

    public Resource getSpeedrunslive() {
        return srl;
    }

    public Link[] getLinks() {
        return links;
    }

    public PersonalBests getPBs(String game) throws IOException {
        return PersonalBests.forUser(this, game);
    }

    private static class UserData {
        User data;
    }

}
