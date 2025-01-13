package gg.projecteden.parchment.sidebar;

import java.util.HashSet;
import java.util.Set;

/**
 * A sidebar layout. Subclasses can describe custom layouts by using the
 * {@link #setup(SidebarStage)} and {@link #update(SidebarStage)} hooks.
 */
public abstract class SidebarLayout {
    private final Set<Runnable> subscribers = new HashSet<>();

    /**
     * Pushes an update to all subscribers.
     */
    public final void refresh() {
        synchronized (this.subscribers) {
            for (Runnable s : this.subscribers) {
                s.run();
            }
        }
    }

    /**
     * Runs when the layout is first applied.
     *
     * <p> The provided sidebar stage may be used to create the first
     * frame of sidebar data, which will be pushed to the client upon
     * exit of this method.
     *
     * @param stage The sidebar stage. Using it outside this method will
     *              result in undefined behavior.
     */
    protected void setup(SidebarStage stage) {
    }

    /**
     * Runs when a refresh is requested.
     *
     * <p> The provided sidebar stage may be used to update the existing
     * sidebar data, which will be pushed to the client upon exit of
     * this method. Sidebar data from previous hook calls will stay the
     * same unless explicitly changed here.
     *
     * @param stage The sidebar stage. Using it outside this method will
     *              result in undefined behavior.
     */
    protected void update(SidebarStage stage) {
    }

    final void subscribe(Runnable listener) {
        this.subscribers.add(listener);
    }

    final void unsubscribe(Runnable listener) {
        this.subscribers.remove(listener);
    }
}