package net.ricebean.tools.colorstrip.model;

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
        assertEquals("c 100", patch.getName());

        patch = new Patch(90,0,0,0);
        assertEquals("c 90", patch.getName());

        patch = new Patch(10,0,0,0);
        assertEquals("c 10", patch.getName());

        patch = new Patch(0,100,0,0);
        assertEquals("m 100", patch.getName());

        patch = new Patch(0,10,0,0);
        assertEquals("m 10", patch.getName());

        patch = new Patch(0,0,80,0);
        assertEquals("y 80", patch.getName());

        patch = new Patch(0,0,0,40);
        assertEquals("k 40", patch.getName());

        patch = new Patch(100,100,0,0);
        assertEquals("cm", patch.getName());

        patch = new Patch(100,0,100,0);
        assertEquals("cy", patch.getName());

        patch = new Patch(0,100,100,0);
        assertEquals("my", patch.getName());

        patch = new Patch(100,100,100,0);
        assertEquals("cmy", patch.getName());

        patch = new Patch(90,90,90,0);
        assertEquals("cmy 90", patch.getName());

        patch = new Patch(90,90,90,1);
        assertEquals("n.a.", patch.getName());

        patch = new Patch(100,100,100,100);
        assertEquals("n.a.", patch.getName());

        patch = new Patch(100,88,0,0);
        assertEquals("n.a.", patch.getName());
    }

}