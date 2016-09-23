package com.flyeralarm.tools.tvi10gen;

import com.flyeralarm.tools.tvi10gen.model.Patch;
import com.flyeralarm.tools.tvi10gen.model.Tvi10Stip;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * JUnit test case for StripBuilder
 */
public class StripBuilderTest {

    @Test
    public void buildTvi10() throws Exception {

        File file = File.createTempFile("strip",".pdf");

        // arrange
        StripBuilder builder = new StripBuilder();

        Patch[] code = new ColorEncoder().encode(3907916902L);

        // act
        builder.build(Tvi10Stip.getPatches(), code, "3907916902", file);

        // assert
        System.out.println("File: " + file.getAbsolutePath());
    }

}