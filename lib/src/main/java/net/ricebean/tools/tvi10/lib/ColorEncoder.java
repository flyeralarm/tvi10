package net.ricebean.tools.tvi10.lib;

import net.ricebean.tools.tvi10.lib.model.Patch;
import net.ricebean.tools.tvi10.lib.model.Tvi10Strip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Encodes a number to a color patches sequence.
 */
class ColorEncoder {

    /**
     * Default constructor.
     */
    ColorEncoder() {
    }

    /**
     * Encodes a long value to a patch panel sequence.
     * @param value Value to be encoded.
     * @return List of encoded patches..
     */
    Patch[] encode(long value) {
        return radix(value, Tvi10Strip.getPatches());
    }

    /**
     * Converts a value to a fixed radix.
     * @param value The value to be computed.
     * @param radix The radix as array of objects.
     * @return The result as object array.
     */
    private <T> T[] radix(long value, T[] radix) {
        List<T> result = new ArrayList<>(100);

        while(value > 0) {
            long rem = value % radix.length;
            result.add(radix[(int)rem]);
            value = value / radix.length;
        }

        Collections.reverse(result);
        return result.toArray(Arrays.copyOfRange(radix, 0, 0));
    }
}
