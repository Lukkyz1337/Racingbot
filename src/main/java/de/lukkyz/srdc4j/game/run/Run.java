package de.lukkyz.srdc4j.game.run;

import de.lukkyz.srdc4j.data.Link;
import de.lukkyz.srdc4j.data.Videos;
import de.lukkyz.srdc4j.game.Category;

import java.io.IOException;
import java.util.Map;

public class Run {

    private String id;
    private String weblink;
    private String game;
    private String level;
    private String category;
    private Videos videos;
    private String comment;
    private Status status;
    private Player[] players;
    private String date;
    private String submitted;
    private Timeset times;
    private System system;
    private Link splits;
    private Map<String, String> values;
    private Link[] links;

    public String getId() {
        return id;
    }

    public String getWeblink() {
        return weblink;
    }

    public Videos getVideos() {
        return videos;
    }

    public String getComment() {
        return comment;
    }

    public Status getStatus() {
        return status;
    }

    public Player[] getPlayers() {
        return players;
    }

    public String getDate() {
        return date;
    }

    public String getSubmitted() {
        return submitted;
    }

    public String getLevel() {
        return level;
    }

    public Timeset getTimes() {
        return times;
    }

    public System getSystem() {
        return system;
    }

    public Link getSplits() {
        return splits;
    }

    public Link[] getLinks() {
        return links;
    }

    public Category getCategory() throws IOException {
        return Category.fromID(category);
    }

}
