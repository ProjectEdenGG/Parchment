package gg.projecteden.parchment;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Class that may be like a {@link org.bukkit.entity.Player} in that it has a {@link java.util.UUID}, {@link org.bukkit.OfflinePlayer}, {@link Identity}, and a nullable Player.
 * @see gg.projecteden.parchment.PlayerLike
 */
public interface OptionalPlayerLike extends OptionalPlayer, gg.projecteden.api.interfaces.HasUniqueId, HasOfflinePlayer, OptionalLocation, Identified, ForwardingAudience.Single {
    /**
     * Gets the identity associated with this object
     *
     * @return associated identity
     */
    @Override
    default @NotNull Identity identity() {
        return Identity.identity(getUniqueId());
    }

    /**
     * Returns if the {@link Player} associated with this object is online.
     *
     * @return if the player is online
     */
    default boolean isOnline() {
        return getPlayer() != null;
    }

    @Override
    default @NotNull Audience audience() {
        return Objects.requireNonNullElse(getPlayer(), Audience.empty());
    }

    @Override
    default @Nullable Location getLocation() {
        final Player player = getPlayer();
        return player == null ? null : player.getLocation();
    }
}