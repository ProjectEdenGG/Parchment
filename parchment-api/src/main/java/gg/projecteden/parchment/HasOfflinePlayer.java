package gg.projecteden.parchment;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an object that has a {@link OfflinePlayer}
 */
@FunctionalInterface
public interface HasOfflinePlayer {
    /**
     * Gets an {@link OfflinePlayer} object that this represents
     *
     * @return offline player
     */
    @NotNull OfflinePlayer getOfflinePlayer();
}