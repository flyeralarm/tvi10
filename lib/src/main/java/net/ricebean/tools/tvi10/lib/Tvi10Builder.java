package net.ricebean.tools.tvi10.lib;

import net.ricebean.tools.tvi10.lib.model.Patch;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Creates a PDF Strip for a list of patches.
 */
class Tvi10Builder {

    private static final String RES_FONT_REGULAR = "/net/ricebean/tools/tvi10/lib/OpenSans-Regular.ttf";

    private static final String RES_FONT_BOLD = "/net/ricebean/tools/tvi10/lib/OpenSans-Bold.ttf";

    private static final float FONT_SIZE = 4;

    private static final float BORDER_TOP = mm2dtp(2);

    private static final float BORDER_BOTTOM = mm2dtp(2);

    private static final float PATCH_WIDTH = mm2dtp(5);

    private static final float PATCH_HEIGHT = mm2dtp(6);

    private final BaseFont fontRegular;

    private final BaseFont fontBold;

    /**
     * Default constructor.
     */
    Tvi10Builder() throws IOException, DocumentException {
        byte[] bytes;

        bytes = IOUtils.toByteArray(Tvi10Builder.class.getResourceAsStream(RES_FONT_REGULAR));
        fontRegular = BaseFont.createFont("regular.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED, true, bytes, null);

        bytes = IOUtils.toByteArray(Tvi10Builder.class.getResourceAsStream(RES_FONT_BOLD));
        fontBold = BaseFont.createFont("bold.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED, true, bytes, null);
    }

    /**
     * Build the TVI Strip PDF.
     *
     * @param targets    The targets as list of Patch objects.
     * @param code       The code as list of Patch objects.
     * @param codeValue  The code as String.
     * @param targetFile The file where the PDF has to be written.
     */
    void build(Patch[] targets, Patch[] code, String codeValue, File targetFile, String companyName) throws IOException, DocumentException {
        float stripHeight = PATCH_HEIGHT + BORDER_BOTTOM + BORDER_TOP;
        float stripWidth = 0;

        // create page
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(targetFile));
        document.open();

        PdfContentByte cb = writer.getDirectContent();
        List<PdfTemplate> tplParts = new ArrayList<>(10);

        // draw target part
        tplParts.add(drawStart(cb));
        tplParts.add(drawTarget(targets, cb, companyName));
        tplParts.add(drawCode(code, codeValue, cb));
        tplParts.add(drawStop(cb));
        tplParts.add(drawSplash(cb, companyName));

        // create final strip
        for (PdfTemplate tplPart : tplParts) {
            stripWidth += tplPart.getWidth();
        }

        PdfTemplate tplStrip = cb.createTemplate(stripWidth, stripHeight);

        Rectangle rectBg = new Rectangle(0, 0, stripWidth, stripHeight);
        rectBg.setBackgroundColor(new CMYKColor(0f, 0f, 0f, 0f));
        tplStrip.rectangle(rectBg);

        float pntPosition = 0;

        for (PdfTemplate tplPart : tplParts) {
            tplStrip.addTemplate(tplPart, pntPosition, 0);
            pntPosition += tplPart.getWidth();
        }

        document.setPageSize(new Rectangle(0, 0, stripWidth, stripHeight));
        document.newPage();

        cb.addTemplate(tplStrip, 0, 0);

        // close document
        document.close();
    }

    /**
     * Draw the splash part.
     * @param cb The PdfContentByte.
     * @return The PdfTemplate containing the splash part.
     */
    private PdfTemplate drawSplash(PdfContentByte cb, String companyName) {
        float stripWidth = mm2dtp(25);
        float stripHeight = PATCH_HEIGHT + BORDER_BOTTOM + BORDER_TOP;

        PdfTemplate tplSplash = cb.createTemplate(stripWidth, stripHeight);

        float refLine = BORDER_BOTTOM + PATCH_HEIGHT * 0.5f;

        // strip name
        tplSplash.beginText();
        tplSplash.setFontAndSize(fontBold, 6);
        tplSplash.showTextAligned(
                PdfContentByte.ALIGN_LEFT,
                companyName + " tvi 10",
                mm2dtp(2),
                refLine,
                0
        );
        tplSplash.endText();

        // eci note
        tplSplash.beginText();
        tplSplash.setFontAndSize(fontRegular, 4);
        tplSplash.showTextAligned(
                PdfContentByte.ALIGN_LEFT,
                "ECI/bvdm tvi 10 (i1) compatible",
                mm2dtp(2),
                refLine - mm2dtp(2f),
                0
        );
        tplSplash.endText();


        return tplSplash;
    }

    /**
     * Create the start part.
     *
     * @param cb The PdfContentByte
     * @return The PdfTemplate containing the start part.
     */
    private PdfTemplate drawStart(PdfContentByte cb) {
        float stripWidth = mm2dtp(2);
        float stripHeight = PATCH_HEIGHT + BORDER_BOTTOM + BORDER_TOP;

        PdfTemplate tplLeftBorder = cb.createTemplate(stripWidth, stripHeight);

        // draw line
        tplLeftBorder.setCMYKColorStrokeF(0f, 0f, 0f, 1.0f);
        tplLeftBorder.moveTo(stripWidth, BORDER_BOTTOM);
        tplLeftBorder.lineTo(stripWidth, BORDER_BOTTOM + PATCH_HEIGHT);
        tplLeftBorder.stroke();

        // draw "Start"
        tplLeftBorder.beginText();
        tplLeftBorder.setCMYKColorFillF(0f, 1f, 1f, 0f);
        tplLeftBorder.setFontAndSize(fontBold, FONT_SIZE);
        tplLeftBorder.showTextAligned(
                PdfContentByte.ALIGN_CENTER,
                "Start",
                stripWidth - 4.5f,
                BORDER_BOTTOM + PATCH_HEIGHT * 0.5f,
                -90
        );
        tplLeftBorder.endText();

        return tplLeftBorder;
    }

    /**
     * Create the stop part.
     *
     * @param cb The PdfContentByte
     * @return The PdfTemplate containing the stop part.
     */
    private PdfTemplate drawStop(PdfContentByte cb) {
        float stripWidth = mm2dtp(2);
        float stripHeight = PATCH_HEIGHT + BORDER_BOTTOM + BORDER_TOP;

        PdfTemplate tplLeftBorder = cb.createTemplate(stripWidth, stripHeight);

        // draw line
        tplLeftBorder.setCMYKColorStrokeF(0f, 0f, 0f, 1.0f);
        tplLeftBorder.moveTo(0, BORDER_BOTTOM);
        tplLeftBorder.lineTo(0, BORDER_BOTTOM + PATCH_HEIGHT);
        tplLeftBorder.stroke();

        // draw "Stop"
        tplLeftBorder.beginText();
        tplLeftBorder.setCMYKColorFillF(0f, 1f, 1f, 0f);
        tplLeftBorder.setFontAndSize(fontBold, FONT_SIZE);
        tplLeftBorder.showTextAligned(
                PdfContentByte.ALIGN_CENTER,
                "Stop",
                2.5f,
                BORDER_BOTTOM + PATCH_HEIGHT * 0.5f,
                -90
        );
        tplLeftBorder.endText();

        return tplLeftBorder;
    }

    /**
     * Create the code part of the strip.
     *
     * @param codes     The code patches to be drawn.
     * @param codeValue The code value as String.
     * @param cb        The PdfContentByte.
     * @return The PdfTemplate containing the codes part.
     */
    private PdfTemplate drawCode(Patch[] codes, String codeValue, PdfContentByte cb) throws IOException, DocumentException {
        List<Patch> lstCodes = new ArrayList<>(Arrays.asList(codes));
        lstCodes.add(new Patch(0, 0, 0, 0));

        float stripWidth = PATCH_WIDTH * lstCodes.size();
        float stripHeight = PATCH_HEIGHT + BORDER_BOTTOM + BORDER_TOP;

        PdfTemplate tplCode = cb.createTemplate(stripWidth, stripHeight);

        // draw patches
        for (int i = 0; i < lstCodes.size(); i++) {
            Patch patch = lstCodes.get(i);

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
        tplCode.setFontAndSize(fontRegular, FONT_SIZE);
        tplCode.showTextAligned(
                PdfContentByte.ALIGN_LEFT,
                "Code: " + codeValue,
                0,
                BORDER_BOTTOM + PATCH_HEIGHT + 1.5f,
                0
        );
        tplCode.endText();

        // draw lines
        tplCode.setCMYKColorStrokeF(0f, 0f, 0f, 1.0f);
        tplCode.moveTo(0, BORDER_BOTTOM);
        tplCode.lineTo(0, BORDER_BOTTOM + PATCH_HEIGHT);
        tplCode.stroke();

        tplCode.setCMYKColorStrokeF(0f, 0f, 0f, 1.0f);
        tplCode.moveTo(stripWidth, BORDER_BOTTOM);
        tplCode.lineTo(stripWidth, BORDER_BOTTOM + PATCH_HEIGHT);
        tplCode.stroke();

        // return code part
        return tplCode;
    }

    /**
     * Create the target part of the strip.
     *
     * @param targets The target patches to be drawn.
     * @param cb      The PdfContentByte.
     * @return The PdfTemplate containing the target part
     */
    private PdfTemplate drawTarget(Patch[] targets, PdfContentByte cb, String companyName) throws IOException, DocumentException {
        List<Patch> lstTargets = new ArrayList<>(Arrays.asList(targets));
        lstTargets.add(0, new Patch(0, 0, 0, 0));
        lstTargets.add(new Patch(0, 0, 0, 0));

        float stripWidth = PATCH_WIDTH * lstTargets.size();
        float stripHeight = PATCH_HEIGHT + BORDER_BOTTOM + BORDER_TOP;

        PdfTemplate tplTarget = cb.createTemplate(stripWidth, stripHeight);

        // draw patches
        for (int i = 0; i < lstTargets.size(); i++) {
            Patch patch = lstTargets.get(i);

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
            tplTarget.rectangle(rectPatch);

            // draw name
            if (i > 0 && i < lstTargets.size() - 1) {
                tplTarget.beginText();
                tplTarget.setFontAndSize(fontRegular, FONT_SIZE);
                tplTarget.showTextAligned(
                        PdfContentByte.ALIGN_CENTER,
                        patch.getName(),
                        PATCH_WIDTH * i + PATCH_WIDTH * 0.5f,
                        PATCH_HEIGHT + BORDER_BOTTOM + 1.5f,
                        0
                );
                tplTarget.endText();
            }
        }

        // draw lines
        tplTarget.setCMYKColorStrokeF(0f, 0f, 0f, 1.0f);
        tplTarget.moveTo(0, BORDER_BOTTOM);
        tplTarget.lineTo(0, BORDER_BOTTOM + PATCH_HEIGHT);
        tplTarget.stroke();

        tplTarget.setCMYKColorStrokeF(0f, 0f, 0f, 1.0f);
        tplTarget.moveTo(stripWidth, BORDER_BOTTOM);
        tplTarget.lineTo(stripWidth, BORDER_BOTTOM + PATCH_HEIGHT);
        tplTarget.stroke();

        // strip name
        String name = companyName + " tvi 10 (v 1.0)  •  ";

        tplTarget.beginText();
        tplTarget.setFontAndSize(fontBold, FONT_SIZE);
        tplTarget.showTextAligned(
                PdfContentByte.ALIGN_LEFT,
                name,
                PATCH_WIDTH,
                1.5f,
                0
        );
        tplTarget.endText();

        // strip text
        String text = "Print Control Strip containing the process colour solids as defined in" +
                " ISO 12647-2 as well as panels describing the tone value increase curve in 10 percent steps.";

        tplTarget.beginText();
        tplTarget.setFontAndSize(fontRegular, FONT_SIZE);
        tplTarget.showTextAligned(
                PdfContentByte.ALIGN_LEFT,
                text,
                fontBold.getWidthPoint(name, FONT_SIZE) + PATCH_WIDTH,
                1.5f,
                0
        );
        tplTarget.endText();

        // tvi 10 end text
        tplTarget.beginText();
        tplTarget.setCMYKColorFillF(0f, 1f, 1f, 0f);
        tplTarget.setFontAndSize(fontBold, FONT_SIZE);
        tplTarget.showTextAligned(
                PdfContentByte.ALIGN_RIGHT,
                "tvi 10 END",
                stripWidth - 2,
                1.5f,
                0
        );
        tplTarget.endText();

        tplTarget.setLineWidth(0.5f);
        tplTarget.setCMYKColorStrokeF(0f, 1f, 1f, 0f);
        tplTarget.moveTo(stripWidth - 0.25f, BORDER_BOTTOM - 1f);
        tplTarget.lineTo(stripWidth - 0.25f, 1f);
        tplTarget.lineTo(stripWidth - 2f, 1f);
        tplTarget.stroke();


        // return measurement
        return tplTarget;
    }

    /**
     * Helper method for converting millimeter into dtp point.
     *
     * @param mm The value in millimeter to be converted.
     * @return The converted value in dtp points.
     */
    private static float mm2dtp(float mm) {
        return mm / 25.4f * 72f;
    }
}
