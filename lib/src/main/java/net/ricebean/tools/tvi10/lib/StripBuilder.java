package net.ricebean.tools.tvi10.lib;

import com.lowagie.text.DocumentException;
import net.ricebean.tools.tvi10.lib.model.Patch;
import net.ricebean.tools.tvi10.lib.model.Tvi10Strip;

import java.io.File;
import java.io.IOException;

/**
 * Class for creating a PDF Strip.
 */
public class StripBuilder {

    /**
     * Creates a TVI 10 Strip plus code.
     * @param value The value to be encoded.
     * @param targetFile The target file.
     */
    public static void createTvi10Strip(final long value, File targetFile) throws IOException, DocumentException {
        Patch[] code = new ColorEncoder().encode(value);

        Tvi10Builder tvi10Builder = new Tvi10Builder();
        tvi10Builder.build(Tvi10Strip.getPatches(), code, Long.toString(value), targetFile);
    }
}
