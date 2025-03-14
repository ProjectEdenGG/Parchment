package gg.projecteden.parchment.event.sound;

import gg.projecteden.parchment.HasLocation;
import gg.projecteden.parchment.OptionalHumanEntity;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Called when a sound is sent to a player.
 * Cancelling this event will prevent the packet from sending.
 */
public final class SoundEvent extends Event implements Cancellable {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SoundEvent.class);

    private static final org.bukkit.event.HandlerList handlers = new org.bukkit.event.HandlerList();
    public static final @NotNull Function<Sound, Double> DEFAULT_DISTANCE_FUNCTION = event -> event.volume() > 1.0F ? (double) (16.0F * event.volume()) : 16.0D;
    public static final @NotNull Function<SoundEvent, List<Player>> DEFAULT_RECIPIENTS_FUNCTION = new WrappedRecipientsFunction(event -> {
        final double distance = event.calculateDistance();
        final Location loc = event.getEmitter().location();
        return loc.getWorld().getPlayers().stream()
            .filter(player -> {
                Location pl = player.getLocation();
                double x = loc.getX() - pl.getX();
                double y = loc.getY() - pl.getY();
                double z = loc.getZ() - pl.getZ();
                return x * x + y * y + z * z < distance * distance;
            })
            .toList();
    });

    private @Nullable HumanEntity except;
    private @NotNull Function<@NotNull Sound, @NotNull Double> distanceFunction;
    private @NotNull Function<@NotNull SoundEvent, @NotNull List<@NotNull Player>> recipientsFunction;
    private @NotNull Sound sound;
    private @NotNull Emitter emitter;
    private boolean cancelled;
    private @Nullable BiFunction<@NotNull SoundEvent, @NotNull Player, @Nullable Sound> soundOverrideFunction;
    private @Nullable BiFunction<@NotNull SoundEvent, @NotNull Player, @Nullable Emitter> emitterOverrideFunction;

    public SoundEvent(@Nullable HumanEntity except, @NotNull Sound sound, @NotNull Emitter emitter, @Nullable Function<Sound, Double> distanceFunction, @Nullable Function<SoundEvent, List<Player>> recipientsFunction) {
        super(true);
        this.except = except;
        this.sound = Objects.requireNonNull(sound, "sound cannot be null");
        this.emitter = Objects.requireNonNull(emitter, "emitter cannot be null");
        this.distanceFunction = Objects.requireNonNullElse(distanceFunction, DEFAULT_DISTANCE_FUNCTION);
        this.recipientsFunction = wrapRecipientsFunction(Objects.requireNonNullElse(recipientsFunction, DEFAULT_RECIPIENTS_FUNCTION));
    }

    /**
     * Gets the player that <b>won't</b> be receiving this sound.
     *
     * @return player excluded from receiving this sound
     */
    public @Nullable HumanEntity getException() {
        return except;
    }

    /**
     * Sets the player that <b>won't</b> be receiving this sound.
     *
     * @param except player excluded from receiving this sound
     */
    public void setException(@Nullable HumanEntity except) {
        this.except = except;
    }

    /**
     * Gets the sound that will be sent.
     *
     * @return sound that will be sent
     */
    public @NotNull Sound getSound() {
        return sound;
    }

    /**
     * Sets the sound that will be sent.
     *
     * @param sound sound that will be sent
     */
    public void setSound(@NotNull Sound sound) {
        this.sound = Objects.requireNonNull(sound, "sound cannot be null");
    }

    /**
     * Gets the emitter which determines how and where the sound will be played from.
     *
     * @return emitter which determines how and where the sound will be played from
     */
    public @NotNull Emitter getEmitter() {
        return emitter;
    }

    /**
     * Sets the emitter which determines how and where the sound will be played from.
     *
     * @param emitter emitter which determines how and where the sound will be played from
     */
    public void setEmitter(@NotNull Emitter emitter) {
        this.emitter = Objects.requireNonNull(emitter, "emitter cannot be null");
    }

    /**
     * Calculates the distance of the sound.
     * <p>
     * The distance value is dynamically calculated using a
     * {@link Function Function&lt;SoundEvent, Double&gt;}.
     * In vanilla Minecraft, the default function is {@link #DEFAULT_DISTANCE_FUNCTION}
     * ({@code event -> event.getVolume() > 1.0F ? (double) (16.0F * event.getVolume()) : 16.0D}).
     * </p>
     * This is used by the vanilla implementation of {@link #calculateRecipients()}, though custom
     * implementations won't always use this method.
     *
     * @return calculated distance
     * @see #getDistanceFunction()
     * @see #setDistanceFunction(Function)
     */
    public double calculateDistance() {
        return distanceFunction.apply(sound);
    }

    /**
     * Gets the function that calculates the distance of the sound.
     *
     * @return distance function
     * @see #calculateDistance()
     * @see #setDistanceFunction(Function)
     */
    public @NotNull Function<@NotNull Sound, @NotNull Double> getDistanceFunction() {
        return distanceFunction;
    }

    /**
     * Sets the function that calculates the distance of the sound.
     *
     * @param distanceFunction distance function
     * @see #calculateDistance()
     * @see #getDistanceFunction()
     */
    public void setDistanceFunction(@NotNull Function<@NotNull Sound, @NotNull Double> distanceFunction) {
        this.distanceFunction = Objects.requireNonNull(distanceFunction, "distanceFunction cannot be null");
    }

    /**
     * Determines which players will receive this sound packet.
     *
     * @return immutable list of players
     * @see #getRecipientsFunction()
     * @see #setRecipientsFunction(Function)
     */
    public @NotNull List<Player> calculateRecipients() {
        return recipientsFunction.apply(this);
    }

    /**
     * Gets the function that determines which players will receive the sound packet.
     *
     * @return recipients function
     * @see #calculateRecipients()
     * @see #setRecipientsFunction(Function)
     */
    public @NotNull Function<@NotNull SoundEvent, @NotNull List<@NotNull Player>> getRecipientsFunction() {
        return recipientsFunction;
    }

    /**
     * Sets the function that determines which players will receive the sound packet.
     * <p>
     * This function does not need to query {@link #getException()} as this is done automatically.
     *
     * @param recipientsFunction recipients function
     * @see #calculateRecipients()
     * @see #getRecipientsFunction()
     */
    public void setRecipientsFunction(@NotNull Function<@NotNull SoundEvent, @NotNull List<@NotNull Player>> recipientsFunction) {
        this.recipientsFunction = wrapRecipientsFunction(Objects.requireNonNull(recipientsFunction, "recipientsFunction cannot be null"));
    }

    /**
     * Gets the function that overrides what {@link Sound} is sent to a {@link Player}.
     *
     * @return sound override function (or {@code null} if not overridden)
     */
    public @Nullable BiFunction<@NotNull SoundEvent, @NotNull Player, @Nullable Sound> getSoundOverrideFunction() {
        return soundOverrideFunction;
    }

    /**
     * Sets the function that overrides what {@link Sound} is sent to a {@link Player}.
     *
     * @param soundOverrideFunction function which accepts a sound event and a player and returns
     *                              a sound (or {@code null} if the default sound should be used)
     */
    public void setSoundOverrideFunction(@Nullable BiFunction<@NotNull SoundEvent, @NotNull Player, @Nullable Sound> soundOverrideFunction) {
        this.soundOverrideFunction = soundOverrideFunction;
    }

    /**
     * Calculates the sound that will be sent to a {@link Player}.
     *
     * @param player player to calculate the sound for
     * @return sound that will be sent to the player
     */
    public @NotNull Sound calculateSound(@NotNull Player player) {
        if (soundOverrideFunction != null) {
            try {
                Sound override = soundOverrideFunction.apply(this, player);
                if (override != null) {
                    return override;
                }
            } catch (Throwable e) {
                LOGGER.error("Error while overriding sound for player " + player.getName(), e);
            }
        }
        return sound;
    }

    /**
     * Gets the function that overrides what {@link Emitter} is used when playing this sound to a
     * {@link Player}.
     *
     * @return emitter override function (or {@code null} if not overridden)
     */
    public @Nullable BiFunction<@NotNull SoundEvent, @NotNull Player, @Nullable Emitter> getEmitterOverrideFunction() {
        return emitterOverrideFunction;
    }

    /**
     * Sets the function that overrides what {@link Emitter} is used when playing this sound to a
     * {@link Player}.
     *
     * @param emitterOverrideFunction function which accepts a sound event and a player and returns
     *                                an emitter
     *                                (or {@code null} if the default emitter should be used)
     */
    public void setEmitterOverrideFunction(@Nullable BiFunction<@NotNull SoundEvent, @NotNull Player, @Nullable Emitter> emitterOverrideFunction) {
        this.emitterOverrideFunction = emitterOverrideFunction;
    }

    /**
     * Calculates the emitter that will be used when playing this sound to a {@link Player}.
     *
     * @param player player to calculate the emitter for
     * @return emitter that will be used when playing the sound to the player
     */
    public @NotNull Emitter calculateEmitter(@NotNull Player player) {
        if (emitterOverrideFunction != null) {
            try {
                Emitter override = emitterOverrideFunction.apply(this, player);
                if (override != null) {
                    return override;
                }
            } catch (Throwable e) {
                LOGGER.error("Error while overriding emitter for player " + player.getName(), e);
            }
        }
        return emitter;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @NotNull
    public static org.bukkit.event.HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull org.bukkit.event.HandlerList getHandlers() {
        return handlers;
    }

    private record WrappedRecipientsFunction(@NotNull Function<SoundEvent, List<Player>> wrapped) implements Function<SoundEvent, List<Player>> {
        @Override
        public @NotNull List<Player> apply(@NotNull SoundEvent event) {
            List<Player> recipients = wrapped.apply(event);
            HumanEntity except = event.getException();
            if (except != null) {
                List<Player> filteredRecipients = new ArrayList<>(recipients.size());
                for (Player player : recipients) {
                    if (!player.getUniqueId().equals(except.getUniqueId()))
                        filteredRecipients.add(player);
                }
                return filteredRecipients;
            }
            return recipients;
        }
    }

    @NotNull
    private static Function<SoundEvent, List<Player>> wrapRecipientsFunction(@NotNull Function<SoundEvent, List<Player>> recipientsFunction) {
        if (recipientsFunction instanceof WrappedRecipientsFunction)
            return recipientsFunction;
        else
            return new WrappedRecipientsFunction(recipientsFunction);
    }

    /**
     * The class which determines where a sound will emit from.
     */
    public sealed interface Emitter extends HasLocation permits EntityEmitter, LocationEmitter {
        /**
         * Gets the location at which the sound will be played.
         *
         * @return sound's location
         * @deprecated use {@link #getLocation()} instead
         */
        @NotNull
        @Deprecated
        default Location location() {
            return getLocation();
        }
    }

    /**
     * An emitter which plays a sound from an entity.
     *
     * @param entity the entity from which the sound will be played
     */
    public record EntityEmitter(@NotNull Entity entity) implements Emitter {
        @Override
        public @NotNull Location getLocation() {
            return entity.getLocation();
        }
    }

    /**
     * An emitter which plays a sound from a location.
     *
     * @param location the location from which the sound will be played
     */
    public record LocationEmitter(@NotNull Location location) implements Emitter {
        @Override
        public @NotNull Location getLocation() {
            return location;
        }
    }
}