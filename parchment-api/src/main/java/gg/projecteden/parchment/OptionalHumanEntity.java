package gg.projecteden.parchment;

import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an object that may have a {@link HumanEntity}
 * @see HasHumanEntity
 */
@FunctionalInterface
public interface OptionalHumanEntity {
    /**
     * Gets a {@link HumanEntity} object that this represents, if there is one
     *
     * @return human entity or null
     */
    @Nullable HumanEntity getPlayer();
}