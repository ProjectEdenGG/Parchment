package gg.projecteden.parchment;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an object that has a {@link Player}
 * @see gg.projecteden.parchment.OptionalPlayer
 */
@FunctionalInterface
public interface HasPlayer extends OptionalPlayer, HasHumanEntity {
    /**
     * Gets a {@link Player} object that this represents
     *
     * @return player
     */
    @Override
    @NotNull Player getPlayer();
}