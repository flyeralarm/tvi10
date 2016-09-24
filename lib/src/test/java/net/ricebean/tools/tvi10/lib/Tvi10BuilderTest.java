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

        Patch[] code = new ColorEncoder().encode(3907916902L);

        // act
        builder.build(Tvi10Strip.getPatches(), code, "3907916902", file);

        // assert
        System.out.println("File: " + file.getAbsolutePath());
    }

}