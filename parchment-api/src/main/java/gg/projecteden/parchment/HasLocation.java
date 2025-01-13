package gg.projecteden.parchment;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an object that has a {@link Location}
 * @see OptionalLocation
 */
public interface HasLocation extends OptionalLocation {
    /**
     * Gets a {@link Location} attached to this object
     *
     * @return attached location
     */
    @Override
    @NotNull Location getLocation();
}