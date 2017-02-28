package net.ricebean.tools.colorstrip.util;

import net.ricebean.tools.colorstrip.model.Patch;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class for creating predefined standard patches.
 */
public class PatchGroup {

    /**
     * Returns the patches of a tvi10 color strip.
     * @return The TVI 10 panels as List.
     */
    public static Patch[] tvi10() {
        List<Patch> patches = new ArrayList<>(45);

        patches.add(new Patch(100, 100, 100, 0));
        patches.add(new Patch(100, 0, 100, 0));
        patches.add(new Patch(100, 100, 0, 0));
        patches.add(new Patch(0, 100, 100, 0));
        patches.add(new Patch(0, 0, 0, 0));
        patches.add(new Patch(0, 100, 0, 0));
        patches.add(new Patch(0, 0, 10, 0));
        patches.add(new Patch(0, 90, 0, 0));
        patches.add(new Patch(0, 0, 20, 0));
        patches.add(new Patch(0, 80, 0, 0));
        patches.add(new Patch(0, 0, 30, 0));
        patches.add(new Patch(0, 70, 0, 0));
        patches.add(new Patch(0, 0, 40, 0));
        patches.add(new Patch(0, 60, 0, 0));
        patches.add(new Patch(0, 0, 50, 0));
        patches.add(new Patch(0, 50, 0, 0));
        patches.add(new Patch(0, 0, 60, 0));
        patches.add(new Patch(0, 40, 0, 0));
        patches.add(new Patch(0, 0, 70, 0));
        patches.add(new Patch(0, 30, 0, 0));
        patches.add(new Patch(0, 0, 80, 0));
        patches.add(new Patch(0, 20, 0, 0));
        patches.add(new Patch(0, 0, 90, 0));
        patches.add(new Patch(0, 10, 0, 0));
        patches.add(new Patch(0, 0, 100, 0));
        patches.add(new Patch(0, 0, 0, 100));
        patches.add(new Patch(10, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 90));
        patches.add(new Patch(20, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 80));
        patches.add(new Patch(30, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 70));
        patches.add(new Patch(40, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 60));
        patches.add(new Patch(50, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 50));
        patches.add(new Patch(60, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 40));
        patches.add(new Patch(70, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 30));
        patches.add(new Patch(80, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 20));
        patches.add(new Patch(90, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 10));
        patches.add(new Patch(100, 0, 0, 0));

        return patches.toArray(new Patch[]{});
    }

    /**
     * Returns the patches of a tvi10 color strip.
     * @return The TVI 10 panels as List.
     */
    public static Patch[] grayConM() {
        List<Patch> patches = new ArrayList<>(45);

        patches.add(new Patch(70, 70, 70, 0));
        patches.add(new Patch(0, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 70));
        patches.add(new Patch(0, 100, 100, 0));
        patches.add(new Patch(50, 50, 50, 0));
        patches.add(new Patch(100, 100, 0, 0));
        patches.add(new Patch(0, 0, 0, 50));
        patches.add(new Patch(100, 0, 100, 0));
        patches.add(new Patch(30, 30, 30, 0));
        patches.add(new Patch(100, 100, 100, 0));
        patches.add(new Patch(0, 0, 0, 30));
        patches.add(new Patch(0, 100, 0, 0));
        patches.add(new Patch(0, 0, 20, 0));
        patches.add(new Patch(0, 80, 0, 0));
        patches.add(new Patch(0, 0, 40, 0));
        patches.add(new Patch(0, 60, 0, 0));
        patches.add(new Patch(0, 0, 60, 0));
        patches.add(new Patch(0, 40, 0, 0));
        patches.add(new Patch(0, 0, 80, 0));
        patches.add(new Patch(0, 20, 0, 0));
        patches.add(new Patch(0, 0, 100, 0));
        patches.add(new Patch(0, 0, 0, 100));
        patches.add(new Patch(20, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 80));
        patches.add(new Patch(40, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 60));
        patches.add(new Patch(60, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 40));
        patches.add(new Patch(80, 0, 0, 0));
        patches.add(new Patch(0, 0, 0, 20));
        patches.add(new Patch(100, 0, 0, 0));

        return patches.toArray(new Patch[]{});
    }
}
