package com.flyeralarm.tools.tvi10gen;

import com.flyeralarm.tools.tvi10gen.model.Patch;
import com.flyeralarm.tools.tvi10gen.model.Tvi10Stip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Encodes a number to a color patches sequence.
 */
public class ColorEncoder {

    /**
     * Default constructor.
     */
    public ColorEncoder() {
    }

    /**
     * Encodes a long value to a patch panel sequence.
     * @param value Value to be encoded.
     * @return List of encoded patches..
     */
    public Patch[] encode(long value) {
        return radix(value, Tvi10Stip.getPatches().toArray(new Patch[]{}));
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
