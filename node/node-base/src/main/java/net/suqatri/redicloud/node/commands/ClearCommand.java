package net.suqatri.redicloud.node.commands;

import net.suqatri.commands.ConsoleCommand;
import net.suqatri.commands.annotation.CommandAlias;
import net.suqatri.commands.annotation.Default;
import net.suqatri.redicloud.node.NodeLauncher;

@CommandAlias("clear")
public class ClearCommand extends ConsoleCommand {

    @Default
    public void onClear() {
        NodeLauncher.getInstance().getConsole().getLineEntries().clear();
        NodeLauncher.getInstance().getConsole().clearScreen();
    }

}
