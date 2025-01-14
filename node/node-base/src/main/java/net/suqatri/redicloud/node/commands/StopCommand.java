package net.suqatri.redicloud.node.commands;

import net.suqatri.commands.CommandSender;
import net.suqatri.commands.ConsoleCommand;
import net.suqatri.commands.annotation.CommandAlias;
import net.suqatri.commands.annotation.Default;
import net.suqatri.redicloud.node.NodeLauncher;

import java.util.concurrent.TimeUnit;

@CommandAlias("stop|shutdown")
public class StopCommand extends ConsoleCommand {

    private long lastTime = 0L;

    @Default
    public void onStop(CommandSender commandSender) {
        if ((System.currentTimeMillis() - this.lastTime) < TimeUnit.SECONDS.toMillis(5)) {
            commandSender.sendMessage("§cShutdown node by command...");
            NodeLauncher.getInstance().shutdown(false);
            return;
        }
        this.lastTime = System.currentTimeMillis();
        commandSender.sendMessage("Enter %hc'stop'%tc in the next 10 seconds again to shutdown the node.");
    }

}
