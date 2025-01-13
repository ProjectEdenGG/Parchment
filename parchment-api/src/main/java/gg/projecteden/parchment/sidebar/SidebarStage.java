package gg.projecteden.parchment.sidebar;

/**
 * An abstracted sidebar stage.
 */
public interface SidebarStage {
    /**
     * Stages a new title.
     *
     * @param title The new title.
     */
    void setTitle(String title);

    /**
     * Stages a new line at the provided index.
     *
     * @param line  The line index.
     * @param value The new line.
     */
    void setLine(int line, String value);

    /**
     * Stages a new line at the provided index
     * Uses the display as the right aligned text
     *
     * @param line The line index
     * @param value The new line
     * @param display The right aligned text
     */
    void setLine(int line, String value, String display);

}