package com.flyeralarm.tools.tvi10gen.model;

/**
 * Model object for a single color patch.
 */
public class Patch {

    private final int cyan;

    private final int magenta;

    private final int yellow;

    private final int black;

    /**
     * Custom constructor.
     * @param cyan The cyan value as Integer
     * @param magenta The magenta value as Integer.
     * @param yellow The yellow value as Integer.
     * @param black The black value as Integer.
     */
    public Patch(int cyan, int magenta, int yellow, int black) {
        this.cyan = cyan;
        this.magenta = magenta;
        this.yellow = yellow;
        this.black = black;
    }

    /**
     * The getter for the attribute name.
     * @return The name as String.
     */
    public String getName() {

        String name;

        if(cyan == 0 && magenta == 0 && yellow == 0 && black == 0) {
            name = "Paper";

        } else if(cyan > 0 && magenta == 0 && yellow == 0 && black == 0) {
            name = "C " + cyan;

        } else if(cyan == 0 && magenta > 0 && yellow == 0 && black == 0) {
            name = "M " + magenta;

        } else if(cyan == 0 && magenta == 0 && yellow > 0 && black == 0) {
            name = "Y " + yellow;

        } else if(cyan == 0 && magenta == 0 && yellow == 0 && black > 0) {
            name = "K " + black;

        } else if(cyan == 0 && magenta == 100 && yellow == 100 && black == 0) {
            name = "M Y";

        } else if(cyan == 100 && magenta == 100 && yellow == 0 && black == 0) {
            name = "C M";

        } else if(cyan == 100 && magenta == 0 && yellow == 100 && black == 0) {
            name = "C Y";

        } else if(cyan == 100 && magenta == 100 && yellow == 100 && black == 0) {
            name = "C M Y";

        } else {
            name = "unknown";

        }

        return name;
    }

    /**
     * The getter for the attribute cyan.
     * @return The cyan as String.
     */
    public int getCyan() {
        return cyan;
    }

    /**
     * The getter for the attribute magenta.
     * @return The magenta as String.
     */
    public int getMagenta() {
        return magenta;
    }

    /**
     * The getter for the attribute yellow.
     * @return The yellow as String.
     */
    public int getYellow() {
        return yellow;
    }

    /**
     * The getter for the attribute black.
     * @return The black as String.
     */
    public int getBlack() {
        return black;
    }

    @Override
    public String toString() {
        return getName();
    }
}
