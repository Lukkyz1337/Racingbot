package de.lukkyz.racingbot.commands.impl;

import de.lukkyz.racingbot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class UpdatePbCommand implements Command {

    //TODO either manual submission or fetch from srdc api

    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    public void action(@NotNull String[] args, MessageReceivedEvent event) {

        if (args.length > 1 && checkIfTime(args[1])) {

            if (args[0].equalsIgnoreCase("glitchless")) {

                //send pb to mysql database
                sendSuccessfulUpdate(event, "Glitchless", formattedTime(args[1]));

            } else if (args[0].equalsIgnoreCase("inbounds")) {

                //send pb to mysql database
                sendSuccessfulUpdate(event, "Inbounds", formattedTime(args[1]));

            } else if (args[0].equalsIgnoreCase("nosla")) {

                //send pb to mysql database
                sendSuccessfulUpdate(event, "Inbounds No SLA", formattedTime(args[1]));

            } else if (args[0].equalsIgnoreCase("oob")) {

                //send pb to mysql database
                sendSuccessfulUpdate(event, "Out of Bounds", formattedTime(args[1]));

            } else {

                sendEmbed(event, "Invalid Category. Valid Categories are ``glitchless``, ``inbounds``, ``nosla``, ``oob``", "Unknown Category", false);

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

            if (seconds > 60 || seconds < 1 && mins < 1) {
                return false;
            } else {
                return true;
            }

        } catch (NumberFormatException nfe) {
            return false;
        }

    }

    public String formattedTime(String input) {
        if (input == null && !checkIfTime(input)) return "joe";

        String[] time = input.split(":");
        String mins = time[0], sec = time[1];

        return mins + "min " + sec + "sec";

    }

}
