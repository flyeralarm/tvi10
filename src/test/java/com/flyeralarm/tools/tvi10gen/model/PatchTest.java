package com.flyeralarm.tools.tvi10gen.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test case for Patch model.
 */
public class PatchTest {

    @Test
    public void getName() throws Exception {

        // arrange
        Patch patch;

        // act / assert
        patch = new Patch(0,0,0,0);
        assertEquals("Paper", patch.getName());

        patch = new Patch(100,0,0,0);
        assertEquals("C 100", patch.getName());

        patch = new Patch(90,0,0,0);
        assertEquals("C 90", patch.getName());

        patch = new Patch(10,0,0,0);
        assertEquals("C 10", patch.getName());

        patch = new Patch(0,100,0,0);
        assertEquals("M 100", patch.getName());

        patch = new Patch(0,10,0,0);
        assertEquals("M 10", patch.getName());

        patch = new Patch(0,0,80,0);
        assertEquals("Y 80", patch.getName());

        patch = new Patch(0,0,0,40);
        assertEquals("K 40", patch.getName());

        patch = new Patch(100,100,0,0);
        assertEquals("C M", patch.getName());

        patch = new Patch(100,0,100,0);
        assertEquals("C Y", patch.getName());

        patch = new Patch(0,100,100,0);
        assertEquals("M Y", patch.getName());

        patch = new Patch(100,100,100,0);
        assertEquals("C M Y", patch.getName());

        patch = new Patch(100,100,100,100);
        assertEquals("unknown", patch.getName());

        patch = new Patch(100,88,0,0);
        assertEquals("unknown", patch.getName());
    }

}