package gg.projecteden.parchment;

import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an object that may have a {@link Location}
 * @see HasLocation
 */
@FunctionalInterface
public interface OptionalLocation {
    /**
     * Gets a {@link Location} attached to this object if present     *
     * @return attached location
     */
    @Nullable Location getLocation();
}