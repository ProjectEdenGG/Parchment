package gg.projecteden.parchment.sidebar;

import gg.projecteden.parchment.util.StringUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.BlankFormat;
import net.minecraft.network.chat.numbers.FixedFormat;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Optional;

public class SidebarBufferImpl extends SidebarBuffer {

    private static final int NAME_LIMIT = 38;
    private static final int AFFIX_LIMIT = 16;
    private static final int SIZE = 15;

    static final int SIDEBAR_SLOT = 1;
    private final Objective objective = new Objective(DedicatedServer.getServer().getScoreboard(), this.name,
                                                      ObjectiveCriteria.DUMMY, Component.literal(this.stagedTitle), ObjectiveCriteria.RenderType.INTEGER,
                                                        false, null);

    private Connection connection;

    SidebarBufferImpl(String name) {
        super(name, SidebarBufferImpl.SIZE);
    }

    protected void setActive() {
        objective.setDisplayName(Component.literal(StringUtils.colorize(this.stagedTitle)));
        ClientboundSetObjectivePacket packet = new ClientboundSetObjectivePacket(this.objective, ClientboundSetObjectivePacket.METHOD_CHANGE);
        ClientboundSetDisplayObjectivePacket packet2 = new ClientboundSetDisplayObjectivePacket(DisplaySlot.SIDEBAR, this.objective);

        this.connection.send(packet);
        this.connection.send(packet2);
    }

    protected void pushChanges() {
        if (!Objects.equals(this.liveTitle, this.stagedTitle)) {
            this.liveTitle = this.stagedTitle;

            ClientboundSetObjectivePacket packet = new ClientboundSetObjectivePacket(this.objective, ClientboundSetObjectivePacket.METHOD_CHANGE);

            this.connection.send(packet);
        }

        int liveEnd = this.size;
        for (int i = 0; i < this.size; i++) {
            if (this.liveLines[i] == null) {
                liveEnd = i;
                break;
            }
        }

        int stagedEnd = this.size;
        for (int i = 0; i < this.size; i++) {
            if (this.stagedLines[i] == null) {
                stagedEnd = i;
                break;
            }
        }

        int maxEnd = Math.max(liveEnd, stagedEnd);
        int liveIdx = liveEnd - maxEnd;
        int stagedIdx = stagedEnd - maxEnd;

        for (int i = 0; i < this.size; i++) {
            String live = liveIdx >= 0 ? this.liveLines[liveIdx] : null;
            String staged = stagedIdx >= 0 ? this.stagedLines[stagedIdx] : null;

            String liveDisplay = liveIdx >= 0 ? this.liveDisplays[liveIdx] : null;
            String stagedDisplay = stagedIdx >= 0 ? this.stagedDisplays[stagedIdx] : null;

            if (!Objects.equals(live, staged) || !Objects.equals(liveDisplay, stagedDisplay)) {
                if (live != null) {
                    this.sendDelete(live, liveEnd - liveIdx);
                }

                if (staged != null) {
                    this.sendCreate(staged, stagedEnd - stagedIdx, this.stagedDisplays[stagedIdx]);
                }
            }

            liveIdx++;
            stagedIdx++;
        }

        System.arraycopy(this.stagedLines, 0, this.liveLines, 0, this.size);
        System.arraycopy(this.stagedDisplays, 0, this.liveDisplays, 0, this.size);
    }

    @Override
    protected void setOwner(Player player) {
        this.connection = ((CraftPlayer) player).getHandle().connection.connection;

        ClientboundSetObjectivePacket packet = new ClientboundSetObjectivePacket(this.objective, ClientboundSetObjectivePacket.METHOD_ADD);
        this.connection.send(packet);

        for (int i = 0; i < this.size; i++) {
            String live = this.liveLines[i];

            if (live != null) {
                this.sendCreate(live, i, stagedDisplays[i]);
            }
        }
    }

    @Override
    protected boolean checkTitle(String line) {
        return line.length() <= 32;
    }

    @Override
    protected boolean checkLine(String line) {
        return line.length() <= SidebarBufferImpl.NAME_LIMIT + SidebarBufferImpl.AFFIX_LIMIT * 2;
    }

    private void sendCreate(String value, int line, String display) {
        String id = "\u00a7" + (char) ('\u0080' + line);
        value = StringUtils.colorize(value);

        if (value.length() > SidebarBufferImpl.NAME_LIMIT) {
            String prefix = value.substring(0, SidebarBufferImpl.AFFIX_LIMIT);
            String suffix = "";

            value = value.substring(SidebarBufferImpl.AFFIX_LIMIT);

            if (value.length() > SidebarBufferImpl.NAME_LIMIT) {
                suffix = value.substring(SidebarBufferImpl.NAME_LIMIT);
                value = value.substring(0, SidebarBufferImpl.NAME_LIMIT);
            }

            PlayerTeam team = new PlayerTeam(DedicatedServer.getServer().getScoreboard(), this.getTeamName(line));
            team.setPlayerPrefix(Component.literal(prefix));
            team.setPlayerSuffix(Component.literal(suffix));
            team.setNameTagVisibility(Team.Visibility.NEVER);
            team.setCollisionRule(Team.CollisionRule.NEVER);
            team.getPlayers().add(id + value);
            ClientboundSetPlayerTeamPacket packet = ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(team, true);

            this.connection.send(packet);
        }

        java.util.Optional<NumberFormat> numberFormat = java.util.Optional.of((display == null || display.isEmpty() || display.isBlank()) ? BlankFormat.INSTANCE : new FixedFormat(Component.literal(StringUtils.colorize(display))));
        ClientboundSetScorePacket packet = new ClientboundSetScorePacket(id + value, this.name, line, Optional.empty(), numberFormat);

        this.connection.send(packet);
    }

    private void sendDelete(String value, int line) {
        String id = "\u00a7" + (char) ('\u0080' + line);

        value = StringUtils.colorize(value);

        if (value.length() > SidebarBufferImpl.NAME_LIMIT) {
            value = value.substring(
                SidebarBufferImpl.AFFIX_LIMIT,
                Math.min(value.length(), SidebarBufferImpl.AFFIX_LIMIT + SidebarBufferImpl.NAME_LIMIT)
            );

            ClientboundSetPlayerTeamPacket packet = ClientboundSetPlayerTeamPacket.createRemovePacket(DedicatedServer.getServer().getScoreboard().getPlayerTeam(this.getTeamName(line)));
            this.connection.send(packet);
        }

        ClientboundResetScorePacket packet = new ClientboundResetScorePacket(id + value, this.name);
        this.connection.send(packet);
    }

    private String getTeamName(int line) {
        return this.name + "/" + Integer.toUnsignedString(line, 32);
    }
}