package net.ricebean.tools.colorstrip;

import net.ricebean.tools.colorstrip.model.Patch;
import net.ricebean.tools.colorstrip.model.Tvi10Strip;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

/**
 * JUnit test case for StripBuilder
 */
public class Tvi10BuilderTest {

    @Test
    public void buildTvi10() throws Exception {

        File file = Paths.get(
                "/Users/stefan/Desktop/", "tvi-10-" + System.currentTimeMillis() + ".pdf"
        ).toFile();

        // arrange
        Tvi10Builder builder = new Tvi10Builder();

        final long code = 3907916902L;
        Patch[] codePatches = new ColorEncoder().encode(code);

        // act
        builder.build(Tvi10Strip.getPatches(), codePatches, Long.toString(code), file, "ricebean.net");

        // assert
        System.out.println("File: " + file.getAbsolutePath());
    }

}