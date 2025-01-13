package gg.projecteden.parchment;

import net.kyori.adventure.audience.Audience;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Class that is like a {@link org.bukkit.entity.Player} in that it has a Player, {@link java.util.UUID}, {@link org.bukkit.OfflinePlayer}, and an {@link net.kyori.adventure.identity.Identity}.
 * @see gg.projecteden.parchment.OptionalPlayerLike
 */
public interface PlayerLike extends HasPlayer, HasLocation, OptionalPlayerLike {

    // reduce nullability checks by re-implementing the methods from OptionalPlayerLike
    // (but without the null checks)

    @Override
    default @NotNull Audience audience() {
        return getPlayer();
    }

    @Override
    default boolean isOnline() {
        return true;
    }

    @Override
    default @NotNull Location getLocation() {
        return getPlayer().getLocation();
    }
}