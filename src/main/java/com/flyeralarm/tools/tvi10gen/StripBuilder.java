package com.flyeralarm.tools.tvi10gen;

import com.flyeralarm.tools.tvi10gen.model.Patch;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.bouncycastle.crypto.tls.ByteQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Creates a PDF Strip for a list of patches.
 */
public class StripBuilder {

    private final float FONT_SIZE = 4;

    private final float BORDER_TOP = mm2dtp(2);

    private final float BORDER_BOTTOM = mm2dtp(2);

    private final float PATCH_WIDTH = mm2dtp(5);

    private final float PATCH_HEIGHT = mm2dtp(5);

    /**
     * Default constructor.
     */
    public StripBuilder() {
    }


    public void build(Patch[] targets, Patch[] code, String codeValue, File targetFile) throws IOException, DocumentException {
        float stripHeight = PATCH_HEIGHT + BORDER_BOTTOM + BORDER_TOP;
        float stripWidth = 0;

        // create page
        Document document = new Document(PageSize.A3);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(targetFile));
        document.open();

        PdfContentByte cb = writer.getDirectContent();

        // draw target part
        PdfTemplate tplTarget = drawTarget(targets, cb);
        stripWidth += tplTarget.getWidth();

        // draw code part
        PdfTemplate tplCode = drawCode(code, codeValue, cb);
        stripWidth += tplCode.getWidth();

        // create final strip
        PdfTemplate tplStrip = cb.createTemplate(stripWidth * 100, stripHeight * 100);

        Rectangle rectBg = new Rectangle(0, 0, stripWidth, stripHeight);
        rectBg.setBackgroundColor(new CMYKColor(0f, 0f, 0f, 0.05f));
        tplStrip.rectangle(rectBg);

        tplStrip.addTemplate(tplTarget, 0, 0);
        tplStrip.addTemplate(tplCode, tplTarget.getWidth(), 0);

        // close document
        cb.addTemplate(tplStrip, 20, 1000);
        document.close();
    }

    /**
     * Create the code part of the strip.
     * @param codes The code patches to be drawn.
     * @param codeValue The code value as String.
     * @param cb The PdfContentByte.
     * @return The PdfTemplate containing the codes part.
     */
    private PdfTemplate drawCode(Patch[] codes, String codeValue, PdfContentByte cb) throws IOException, DocumentException {
        float stripWidth = PATCH_WIDTH * codes.length;
        float stripHeight = PATCH_HEIGHT + BORDER_BOTTOM + BORDER_TOP;

        PdfTemplate tplCode = cb.createTemplate(stripWidth, stripHeight);

        // draw patches
        for(int i = 0; i < codes.length; i ++) {
            Patch patch = codes[i];

            // draw patch
            CMYKColor cmykColor = new CMYKColor(
                    (float) patch.getCyan() / 100f,
                    (float) patch.getMagenta() / 100f,
                    (float) patch.getYellow() / 100f,
                    (float) patch.getBlack() / 100f
            );

            Rectangle rectPatch = new Rectangle(
                    PATCH_WIDTH * i,
                    BORDER_BOTTOM,
                    PATCH_WIDTH * (i + 1),
                    PATCH_HEIGHT + BORDER_BOTTOM
            );
            rectPatch.setBackgroundColor(cmykColor);
            tplCode.rectangle(rectPatch);
        }

        // value
        tplCode.beginText();
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        tplCode.setFontAndSize(bf, FONT_SIZE);
        tplCode.showTextAligned(
                PdfContentByte.ALIGN_LEFT,
                "Code: " + codeValue,
                0,
                0 - bf.getDescentPoint("WgKyJ", FONT_SIZE),
                0
        );
        tplCode.endText();

        // return code part
        return tplCode;
    }

    /**
     * Create the target part of the strip.
     * @param targets The target patches to be drawn.
     * @param cb The PdfcontentByte.
     * @return The PdfTemplate containing the target part
     */
    private PdfTemplate drawTarget(Patch[] targets, PdfContentByte cb) throws IOException, DocumentException {
        float stripWidth = PATCH_WIDTH * (targets.length + 2);
        float stripHeight = PATCH_HEIGHT + BORDER_BOTTOM + BORDER_TOP;

        PdfTemplate tplTarget = cb.createTemplate(stripWidth, stripHeight);

        // draw patches
        for(int i = 0; i < targets.length; i ++) {
            Patch patch = targets[i];

            // draw patch
            CMYKColor cmykColor = new CMYKColor(
                    (float) patch.getCyan() / 100f,
                    (float) patch.getMagenta() / 100f,
                    (float) patch.getYellow() / 100f,
                    (float) patch.getBlack() / 100f
            );

            Rectangle rectPatch = new Rectangle(
                    PATCH_WIDTH * (i + 1),
                    BORDER_BOTTOM,
                    PATCH_WIDTH * (i + 2),
                    PATCH_HEIGHT + BORDER_BOTTOM
            );
            rectPatch.setBackgroundColor(cmykColor);
            tplTarget.rectangle(rectPatch);

            // draw name
            tplTarget.beginText();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            tplTarget.setFontAndSize(bf, FONT_SIZE);
            tplTarget.showTextAligned(
                    PdfContentByte.ALIGN_CENTER,
                    patch.getName(),
                    PATCH_WIDTH * (i + 1) + PATCH_WIDTH * 0.5f,
                    PATCH_HEIGHT + BORDER_BOTTOM - bf.getDescentPoint("WgKyJ", FONT_SIZE),
                    0
            );
            tplTarget.endText();
        }

        // author
        tplTarget.beginText();
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        tplTarget.setFontAndSize(bf, FONT_SIZE);
        tplTarget.showTextAligned(
                PdfContentByte.ALIGN_LEFT,
                "ricebean.net/tvi10",
                0,
                0 - bf.getDescentPoint("WgKyJ", FONT_SIZE),
                0
        );
        tplTarget.endText();

        // return measurement
        return tplTarget;
    }

    /**
     * Helper method for converting millimeter into dtp point.
     * @param mm The value in millimeter to be converted.
     * @return The converted value in dtp points.
     */
    private float mm2dtp(float mm) {
        return mm / 25.4f * 72f;
    }
}
