package de.lukkyz.racingbot;

import de.lukkyz.racingbot.commands.CommandHandler;
import de.lukkyz.racingbot.commands.impl.PbCommand;
import de.lukkyz.racingbot.commands.impl.RegisterCommand;
import de.lukkyz.racingbot.commands.impl.UpdatePbCommand;
import de.lukkyz.racingbot.listeners.MessageListener;
import de.lukkyz.racingbot.listeners.ReadyListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;

import javax.security.auth.login.LoginException;

public class Racingbot {

    public static final String PREFIX = "-";

    public static void main(String[] args) throws LoginException {

        JDABuilder builder = new JDABuilder(AccountType.BOT);

        builder.setToken(Secrets.BOT_TOKEN);
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);

        /* Register Listeners */
        builder.addEventListeners(new ReadyListener());
        builder.addEventListeners(new MessageListener());

        /* Register Commands */
        CommandHandler.commands.put("update", new UpdatePbCommand());
        CommandHandler.commands.put("pb", new PbCommand());
        CommandHandler.commands.put("register", new RegisterCommand());

        builder.build();

    }
}
