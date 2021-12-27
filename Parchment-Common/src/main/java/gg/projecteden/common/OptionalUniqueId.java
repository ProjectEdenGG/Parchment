package gg.projecteden.common;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents an object that may have a {@link UUID}
 */
public interface OptionalUniqueId {
	/**
	 * Returns a unique and persistent id for this object which may be null
	 *
	 * @return unique id or null
	 */
	@Nullable UUID getUniqueId(); // named getUniqueId to maintain compatibility with Bukkit
}
