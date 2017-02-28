package net.ricebean.tools.colorstrip;

import net.ricebean.tools.colorstrip.ColorEncoder;
import net.ricebean.tools.colorstrip.model.Patch;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * JUnit test case for ColorEncoder.
 */
public class ColorEncoderTest {

    @Test
    public void encode() throws Exception {

        // arrange
        ColorEncoder encoder = new ColorEncoder();
        final long value = 100000000;

        // act
        Patch[] result = encoder.encode(value);

        // assert
        assertEquals("Color Encoding is wrong.", "[y 100, m 40, m 40, c 40, y 30]", Arrays.toString(result));
    }

    @Test
    public void encodeFun() throws Exception {

        // arrange
        ColorEncoder encoder = new ColorEncoder();

        // act
        for(long i = 0; i < 50000; i ++) {
            Patch[] result = encoder.encode(i);
            System.out.println(Arrays.toString(result) + " = " + i);
        }
    }

    @Test
    public void radixHex() throws  Exception {

        // arrange
        final long value = 100000000;
        Object[] base = "0123456789abcdef".split("(?!^)");
        ColorEncoder colorEncoder = new ColorEncoder();

        // act
        Method method = ColorEncoder.class.getDeclaredMethod("radix", long.class, Object[].class);
        method.setAccessible(true);
        Object[] result = (Object[]) method.invoke(colorEncoder, value, base);

        // assert
        String number = "";

        for (Object aResult : result) {
            number += aResult.toString();
        }

        assertEquals(Long.toString(value, 16), number);
    }

    @Test
    public void radix5() throws  Exception {

        // arrange
        final long value = 100000000;
        Object[] base = "01234".split("(?!^)");
        ColorEncoder colorEncoder = new ColorEncoder();

        // act
        Method method = ColorEncoder.class.getDeclaredMethod("radix", long.class, Object[].class);
        method.setAccessible(true);
        Object[] result = (Object[]) method.invoke(colorEncoder, value, base);

        // assert
        String number = "";

        for (Object aResult : result) {
            number += aResult.toString();
        }

        assertEquals(Long.toString(value, 5), number);
    }

    @Test
    public void radix45() throws  Exception {

        // arrange
        final long value = 100000000;
        Object[] base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".split("(?!^)");
        ColorEncoder colorEncoder = new ColorEncoder();

        // act
        Method method = ColorEncoder.class.getDeclaredMethod("radix", long.class, Object[].class);
        method.setAccessible(true);
        Object[] result = (Object[]) method.invoke(colorEncoder, value, base);

        // assert
        String number = "";

        for (Object aResult : result) {
            number += aResult.toString();
        }

        assertEquals("6LAze", number);
        assertArrayEquals("6LAze".split("(?!^)"), result);
    }
}