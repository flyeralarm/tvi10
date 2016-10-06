package net.ricebean.tools.tvi10.lib;

import net.ricebean.tools.tvi10.lib.model.Patch;
import net.ricebean.tools.tvi10.lib.model.Tvi10Strip;
import org.junit.Test;

import java.io.File;

/**
 * JUnit test case for StripBuilder
 */
public class Tvi10BuilderTest {

    @Test
    public void buildTvi10() throws Exception {

        File file = File.createTempFile("strip",".pdf");

        // arrange
        Tvi10Builder builder = new Tvi10Builder();

        final long code = 3907916902L;
        Patch[] codePatches = new ColorEncoder().encode(code);

        // act
        builder.build(Tvi10Strip.getPatches(), codePatches, Long.toString(code), file, "FLYERALARM");

        // assert
        System.out.println("File: " + file.getAbsolutePath());
    }

}