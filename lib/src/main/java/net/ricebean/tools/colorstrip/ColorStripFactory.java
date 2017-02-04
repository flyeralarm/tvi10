package net.ricebean.tools.colorstrip;

import com.lowagie.text.DocumentException;
import net.ricebean.tools.colorstrip.model.Patch;
import net.ricebean.tools.colorstrip.model.Tvi10Strip;

import java.io.File;
import java.io.IOException;

/**
 * Class for creating a PDF Strip.
 */
public class ColorStripFactory {

    /**
     * Creates a TVI 10 Strip plus code.
     * @param value The value to be encoded.
     * @param targetFile The target file.
     */
    public static void createTvi10Strip(final long value, File targetFile) throws IOException, DocumentException {
        createTvi10Strip(value, targetFile, "ricebean.net");
    }

    /**
     * Creates a TVI 10 Strip plus code.
     * @param value The value to be encoded.
     * @param targetFile The target file.
     * @param companyName The companies name.
     */
    public static void createTvi10Strip(final long value, File targetFile, String companyName) throws IOException, DocumentException {
        Patch[] code = new ColorEncoder().encode(value);

        Tvi10Builder tvi10Builder = new Tvi10Builder();
        tvi10Builder.build(Tvi10Strip.getPatches(), code, Long.toString(value), targetFile, companyName);
    }
}
