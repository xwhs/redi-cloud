package net.suqatri.commands;

import net.suqatri.commands.locales.MessageKey;
import net.suqatri.commands.locales.MessageKeyProvider;

import java.util.Locale;

public enum MinecraftMessageKeys implements MessageKeyProvider {
    USERNAME_TOO_SHORT,
    IS_NOT_A_VALID_NAME,
    MULTIPLE_PLAYERS_MATCH,
    NO_PLAYER_FOUND_SERVER,
    NO_PLAYER_FOUND;

    private final MessageKey key = MessageKey.of("acf-minecraft." + this.name().toLowerCase(Locale.ENGLISH));

    public MessageKey getMessageKey() {
        return key;
    }
}
