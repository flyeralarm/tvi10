package net.ricebean.tools.colorstrip;

import com.lowagie.text.DocumentException;
import net.ricebean.tools.colorstrip.util.PatchGroup;

import java.io.File;
import java.io.IOException;

/**
 * Class for creating a PDF Strip.
 */
public class ColorStripFactory {

    /**
     * Creates a TVI 10 Strip plus code.
     *
     * @param value      The value to be encoded.
     * @param targetFile The target file.
     */
    public static void createTvi10Strip(final long value, File targetFile) throws IOException, DocumentException {
        createTvi10Strip(value, targetFile, "ricebean.net");
    }

    /**
     * Creates a TVI 10 Strip plus code.
     *
     * @param code       The code to be encoded.
     * @param targetFile  The target file.
     * @param companyName The companies name.
     */
    public static void createTvi10Strip(final long code, File targetFile, String companyName) throws IOException, DocumentException {
        ColorStripBuilder colorStripBuilder = new ColorStripBuilderImpl();
        colorStripBuilder.addPatchGroup("ECI/bvdm TVI 10 i1", PatchGroup.tvi10());
        colorStripBuilder.setTitle(companyName + " TVI 10");
        colorStripBuilder.setDescription("compatible to ECI/bvdm TVI 10 i1");
        colorStripBuilder.build(targetFile, code);
    }

    /**
     * Creates a GrayCon M Strip plus code.
     *
     * @param value      The value to be encoded.
     * @param targetFile The target file.
     */
    public static void createGrayConMi1(final long value, File targetFile) throws IOException, DocumentException {
        createGrayConMi1(value, targetFile, "ricebean.net");
    }

    /**
     * Creates a GrayCon M Strip plus code.
     *
     * @param code       The code to be encoded.
     * @param targetFile  The target file.
     * @param companyName The companies name.
     */
    public static void createGrayConMi1(final long code, File targetFile, String companyName) throws IOException, DocumentException {
        ColorStripBuilder colorStripBuilder = new ColorStripBuilderImpl();
        colorStripBuilder.addPatchGroup("ECI/bvdm GrayCon M i1", PatchGroup.grayConM());
        colorStripBuilder.setTitle(companyName + " GrayCon M i1");
        colorStripBuilder.setDescription("compatible to ECI/bvdm GrayCon M i1");
        colorStripBuilder.build(targetFile, code);
    }

    /**
     * Returns a color strip builder instance.
     * @return A new color strip builder instance.
     */
    public static ColorStripBuilder newColorStripBuilder() throws IOException, DocumentException {
        return new ColorStripBuilderImpl();
    }
}