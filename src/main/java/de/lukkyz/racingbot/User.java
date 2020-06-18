package de.lukkyz.racingbot;

import org.jetbrains.annotations.NotNull;

public class User implements Comparable<User>{
    net.dv8tion.jda.api.entities.User discordUser;
    int[] time;
    public User(net.dv8tion.jda.api.entities.User discordUser, int[] time){
        this.discordUser = discordUser;
        this.time = time;
    }

    public void setDiscordUser(net.dv8tion.jda.api.entities.User discordUser){
        this.discordUser = discordUser;
    }

    public net.dv8tion.jda.api.entities.User getDiscordUser(){
        return discordUser;
    }

    public void setTime(int[] time){
        this.time = time;
    }

    public int[] getTime(){
        return this.time;
    }

    @Override
    public int compareTo(@NotNull User o) {
        return  (time[0]*60+time[1]) - (o.getTime()[0]*60+o.getTime()[1]);
    }
}
