package gg.projecteden.parchment.event.sound;

import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.sound.Sound;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.bukkit.Location;

import java.util.Optional;
import java.util.function.Function;

public class ParchmentSoundEvent {
    public static final Function<Sound, Double> DISTANCE_FUNCTION = sound -> {
        Optional<SoundEvent> soundEvent = PaperAdventure.asVanillaSound(sound.name());
        if (soundEvent.isPresent())
            return Double.valueOf(soundEvent.get().getRange(sound.volume()));
        return gg.projecteden.parchment.event.sound.SoundEvent.DEFAULT_DISTANCE_FUNCTION.apply(sound);
    };

    public static gg.projecteden.parchment.event.sound.SoundEvent.Emitter createEmitter(Level level, double x, double y, double z) {
        return new gg.projecteden.parchment.event.sound.SoundEvent.LocationEmitter(new Location(level.getWorld(), x, y, z));
    }

    public static gg.projecteden.parchment.event.sound.SoundEvent.Emitter createEmitter(Entity entity) {
        return new gg.projecteden.parchment.event.sound.SoundEvent.EntityEmitter(entity.getBukkitEntity());
    }
}