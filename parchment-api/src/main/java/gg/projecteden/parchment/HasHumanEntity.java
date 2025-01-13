package gg.projecteden.parchment;

import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an object that has a {@link HumanEntity}
 * @see gg.projecteden.parchment.OptionalHumanEntity
 */
@FunctionalInterface
public interface HasHumanEntity extends OptionalHumanEntity {
    /**
     * Gets a {@link HumanEntity} object that this represents
     *
     * @return human entity
     */
    @Override
    @NotNull HumanEntity getPlayer();
}