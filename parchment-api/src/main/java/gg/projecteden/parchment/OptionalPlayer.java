package gg.projecteden.parchment;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an object that may have a {@link Player}
 * @see HasPlayer
 */
@FunctionalInterface
public interface OptionalPlayer extends OptionalHumanEntity {
    /**
     * Gets a {@link Player} object that this represents, if there is one. This may be null if the
     * player is online or the player object being optional.
     *
     * @return player or null
     */
    @Nullable Player getPlayer();
}