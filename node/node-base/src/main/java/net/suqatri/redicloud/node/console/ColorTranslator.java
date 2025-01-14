package net.suqatri.redicloud.node.console;

import lombok.AllArgsConstructor;
import org.fusesource.jansi.Ansi;

@AllArgsConstructor
public enum ColorTranslator {

    RESET('r', Ansi.ansi().reset().fg(Ansi.Color.DEFAULT).boldOff().toString()),
    WHITE('f', Ansi.ansi().reset().fg(Ansi.Color.WHITE).bold().toString()),
    BLACK('0', Ansi.ansi().reset().fg(Ansi.Color.BLACK).bold().toString()),
    RED('c', Ansi.ansi().reset().fg(Ansi.Color.RED).bold().toString()),
    PINK('d', Ansi.ansi().reset().fg(Ansi.Color.MAGENTA).bold().toString()),
    YELLOW('e', Ansi.ansi().reset().fg(Ansi.Color.YELLOW).bold().toString()),
    BLUE('9', Ansi.ansi().reset().fg(Ansi.Color.BLUE).bold().toString()),
    GREEN('a', Ansi.ansi().reset().fg(Ansi.Color.GREEN).bold().toString()),
    PURPLE('5', Ansi.ansi().reset().fg(Ansi.Color.MAGENTA).boldOff().toString()),
    ORANGE('6', Ansi.ansi().reset().fg(Ansi.Color.YELLOW).boldOff().toString()),
    GRAY('7', Ansi.ansi().reset().fg(Ansi.Color.WHITE).boldOff().toString()),
    DARK_RED('4', Ansi.ansi().reset().fg(Ansi.Color.RED).boldOff().toString()),
    DARK_GRAY('8', Ansi.ansi().reset().fg(Ansi.Color.BLACK).bold().toString()),
    DARK_BLUE('1', Ansi.ansi().reset().fg(Ansi.Color.BLUE).boldOff().toString()),
    DARK_GREEN('2', Ansi.ansi().reset().fg(Ansi.Color.GREEN).boldOff().toString()),
    LIGHT_BLUE('b', Ansi.ansi().reset().fg(Ansi.Color.CYAN).bold().toString()),
    CYAN('3', Ansi.ansi().reset().fg(Ansi.Color.CYAN).boldOff().toString());

    private final char index;
    private final String code;

    public static String translate(String value) {
        String message = value;
        for (ColorTranslator ansiColorFactory : values()) {
            for (char replacement : new char[]{'§', '&'}) {
                message = message.replace(replacement + "" + ansiColorFactory.index, ansiColorFactory.code);
            }
        }
        return message;
    }

    public static String removeColorCodes(String value) {
        String message = value;
        for (ColorTranslator ansiColorFactory : values()) {
            for (char replacement : new char[]{'§', '&'}) {
                message = message.replace(replacement + "" + ansiColorFactory.index, "");
            }
        }
        return message;
    }

}
