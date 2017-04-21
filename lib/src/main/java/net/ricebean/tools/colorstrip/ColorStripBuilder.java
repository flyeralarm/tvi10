package net.ricebean.tools.colorstrip;

import com.lowagie.text.DocumentException;
import net.ricebean.tools.colorstrip.model.Patch;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by stefan on 2/28/17.
 */
public interface ColorStripBuilder {

    /**
     * Set color strips font size.
     * @param fontSize The font size of the color strip.
     */
    void setFontSize(float fontSize);

    /**
     * Set the distance of the border on the top in millimeter.
     * @param borderTop Distance of the top border in millimeter.
     */
    void setBorderTop(float borderTop);

    /**
     * Set the distance of the border on the bottom in millimeter.
     * @param borderBottom Distance of the bottom border in millimeter.
     */
    void setBorderBottom(float borderBottom);

    /**
     * Set the width of a patch in millimeter.
     * @param patchWidth Patch width in millimeter.
     */
    void setPatchWidth(float patchWidth);

    /**
     * Set the width of the spacer patch in millimeter.
     * @param patchWidthSpacer Spacer patch width in millimeter.
     */
    void setPatchWidthSpacer(float patchWidthSpacer);

    /**
     * Set the width of the start / stop patch in millimeter.
     * @param patchWidthStartStop Start / stop patch width in millimeter.
     */
    void setPatchWidthStartStop(float patchWidthStartStop);

    /**
     * Set the height of the patch in millimeter.
     * @param patchHeight The height of the patch in  millimeter.
     */
    void setPatchHeight(float patchHeight);

    /**
     * Set the tile of the strip.
     * @param title The strips title.
     */
    void setTitle(String title);

    /**
     * Set the descipriton of the strip.
     * @param description The strips description.
     */
    void setDescription(String description);

    /**
     * Adds a patch group to the strip to be rendered.
     * @param groupName The name of the patch group as string.
     * @param patchGroup The patches of the patch group.
     */
    void addPatchGroup(String groupName, Patch[] patchGroup);

    /**
     * Builds a new PDF Color strip.
     * @param targetFile The target file where the strip has to be created.
     */
    void build(File targetFile) throws FileNotFoundException, DocumentException;

    /**
     * Builds a new PDF Color strip.
     * @param targetFile The target file where the strip has to be created.
     * @param code The code to be included.
     */
    void build(File targetFile, Long code) throws FileNotFoundException, DocumentException;
}
