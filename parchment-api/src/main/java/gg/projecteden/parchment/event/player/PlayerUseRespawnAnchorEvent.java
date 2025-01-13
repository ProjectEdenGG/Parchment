package gg.projecteden.parchment.event.player;

import com.google.common.base.Preconditions;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * This event is fired after determining what action should result from a player interacting with
 * a respawn anchor.
 */
public class PlayerUseRespawnAnchorEvent extends PlayerEvent implements Cancellable {

	/**
	 * Represents the default possible outcomes of this event.
	 */
	public enum RespawnAnchorResult {
		/**
		 * The player's spawn point will be set
		 */
		SET_SPAWN,
		/**
		 * The respawn anchor will explode due to being used outside the nether
		 */
		EXPLODE,
		/**
		 * The player will charge the respawn anchor
		 */
		CHARGE,
		/**
		 * The respawn anchor will do nothing
		 */
		NOTHING
	}

	private static final HandlerList handlers = new HandlerList();
	private final Block respawnAnchor;
	private RespawnAnchorResult respawnAnchorResult;
	private boolean cancelled = false;

	public PlayerUseRespawnAnchorEvent(@NotNull Player who, @NotNull Block respawnAnchor, @NotNull RespawnAnchorResult respawnAnchorResult) {
		super(who);
		this.respawnAnchor = respawnAnchor;
		this.respawnAnchorResult = respawnAnchorResult;
	}

	/**
	 * Returns the respawn anchor block involved in this event.
	 *
	 * @return the respawn anchor block involved in this event
	 */
	@NotNull
	public Block getRespawnAnchor() {
		return this.respawnAnchor;
	}

	/**
	 * Describes the outcome of the event.
	 *
	 * @return the respawn anchor result for the outcome of the event
	 */
	@NotNull
	public RespawnAnchorResult getResult() {
		return this.respawnAnchorResult;
	}

	/**
	* Sets the outcome of the event.
   *
	* @param result event to set
   */
	public void setResult(@NotNull RespawnAnchorResult result) {
		this.respawnAnchorResult = Preconditions.checkNotNull(result, "result");
	}

	/**
	 * Gets the cancellation state of this event. A cancelled event will not
	 * be executed in the server, but will still pass to other plugins.
	 * <p>
	 * A positive value means the respawn anchor will not take any action, as
	 * if it had not been clicked at all.
	 *
	 * @return true if this event is cancelled
	 */
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Sets the cancellation state of this event. A canceled event will not be
	 * executed in the server, but will still pass to other plugins.
	 * <p>
	 * Canceling this event will prevent use of the respawn anchor, leaving it
	 * as thought it hadn't been clicked at all.
	 *
	 * @param cancel true if you wish to cancel this event
	 */
	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return handlers;
	}

	@NotNull
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
