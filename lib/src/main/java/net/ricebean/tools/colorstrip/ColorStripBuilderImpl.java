package net.ricebean.tools.colorstrip;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import net.ricebean.tools.colorstrip.model.Patch;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Creates a Strip PDF based on fields.
 */
class ColorStripBuilderImpl implements ColorStripBuilder {

    private static final String RES_FONT_REGULAR = "/net/ricebean/tools/colorstrip/OpenSans-Regular.ttf";

    private static final String RES_FONT_BOLD = "/net/ricebean/tools/colorstrip/OpenSans-Bold.ttf";

    private float fontSize = 3;

    private float borderTop = mm2dtp(2);

    private float borderBottom = mm2dtp(2);

    private float patchWidth = mm2dtp(5);

    private float patchWidthSpacer = mm2dtp(6);

    private float patchWidthStartStop = mm2dtp(6);

    private float patchHeight = mm2dtp(6);

    private BaseFont fontRegular;

    private BaseFont fontBold;

    private Map<String, Patch[]> patchGroups = new LinkedHashMap<>();

    private String title = "ricebean.net Strip";

    private String description = "Generic Color Strip";

    /**
     * Default constructor.
     */
    ColorStripBuilderImpl() throws IOException, DocumentException {
        byte[] bytes;

        bytes = IOUtils.toByteArray(ColorStripBuilderImpl.class.getResourceAsStream(RES_FONT_REGULAR));
        fontRegular = BaseFont.createFont("regular.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED, true, bytes, null);

        bytes = IOUtils.toByteArray(ColorStripBuilderImpl.class.getResourceAsStream(RES_FONT_BOLD));
        fontBold = BaseFont.createFont("bold.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED, true, bytes, null);
    }

    @Override
    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    public void setBorderTop(float borderTop) {
        this.borderTop = mm2dtp(borderTop);
    }

    @Override
    public void setBorderBottom(float borderBottom) {
        this.borderBottom = mm2dtp(borderBottom);
    }

    @Override
    public void setPatchWidth(float patchWidth) {
        this.patchWidth = mm2dtp(patchWidth);
    }

    @Override
    public void setPatchWidthSpacer(float patchWidthSpacer) {
        this.patchWidthSpacer = mm2dtp(patchWidthSpacer);
    }

    @Override
    public void setPatchWidthStartStop(float patchWidthStartStop) {
        this.patchWidthStartStop = mm2dtp(patchWidthStartStop);
    }

    @Override
    public void setPatchHeight(float patchHeight) {
        this.patchHeight = mm2dtp(patchHeight);
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Add a patch group to the strip.
     *
     * @param groupName  The name of the patch group.
     * @param patchGroup Patches of the group.
     */
    @Override
    public void addPatchGroup(String groupName, Patch[] patchGroup) {
        patchGroups.put(groupName, patchGroup);
    }

    /**
     * Build the strip based on all settings.
     *
     * @param targetFile The target file where the strip has to be built.
     */
    @Override
    public void build(File targetFile) throws FileNotFoundException, DocumentException {
        build(targetFile, null);
    }

    /**
     * Build the strip based on all settings.
     *
     * @param targetFile The target file where the strip has to be built.
     */
    @Override
    public void build(File targetFile, Long code) throws FileNotFoundException, DocumentException {
        float stripHeight = patchHeight + borderBottom + borderTop;
        float stripWidth = 0;

        // create page
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(targetFile));
        document.open();

        PdfContentByte cb = writer.getDirectContent();
        List<PdfTemplate> tplParts = new ArrayList<>(10);

        // compute code
        if (code != null) {
            patchGroups.put("Code: " + Long.toString(code), computeCode(patchGroups, code));
        }

        // draw target part
        int cnt = 0;
        tplParts.add(drawStart(cb));

        for (String name : patchGroups.keySet()) {
            if(cnt == 0) {
                tplParts.add(drawSpacer(cb, patchWidthStartStop));
            } else if(patchWidthSpacer > 0) {
                tplParts.add(drawSpacer(cb, patchWidthSpacer));
            }

            tplParts.add(drawPatchGroup(cb, name, patchGroups.get(name)));
            cnt ++;
        }

        tplParts.add(drawSpacer(cb, patchWidthStartStop));
        tplParts.add(drawStop(cb));
        tplParts.add(drawSplash(cb));

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
     * Compute color code.
     *
     * @param patchGroups The patches to be used for encoding.
     * @param value       The value to be encoded.
     * @return PatchGroup which represents the code.
     */
    private Patch[] computeCode(Map<String, Patch[]> patchGroups, long value) {
        int cntPatches = 0;

        // count patches
        for (String name : patchGroups.keySet()) {
            cntPatches += patchGroups.get(name).length;
        }

        // aggregate patches
        Patch[] patches = new Patch[cntPatches];
        cntPatches = 0;

        for (String name : patchGroups.keySet()) {
            for (int i = 0; i < patchGroups.get(name).length; i++) {
                patches[cntPatches] = patchGroups.get(name)[i];
                cntPatches++;
            }
        }

        // return code
        return new ColorEncoder().encode(value, patches);
    }

    /**
     * Create the stop part.
     *
     * @param cb The PdfContentByte
     * @return The PdfTemplate containing the stop part.
     */
    private PdfTemplate drawStop(PdfContentByte cb) {
        float stripWidth = mm2dtp(2);
        float stripHeight = patchHeight + borderBottom + borderTop;

        PdfTemplate tpl = cb.createTemplate(stripWidth, stripHeight);

        // draw line
        tpl.setCMYKColorStrokeF(0f, 0f, 0f, 1.0f);
        tpl.setLineWidth(1f);
        tpl.moveTo(0, borderBottom);
        tpl.lineTo(0, borderBottom + patchHeight);
        tpl.stroke();

        // draw "Stop"
        tpl.beginText();
        tpl.setCMYKColorFillF(0f, 1f, 1f, 0f);
        tpl.setFontAndSize(fontBold, fontSize);
        tpl.showTextAligned(
                PdfContentByte.ALIGN_CENTER,
                "Stop",
                2.5f,
                borderBottom + patchHeight * 0.5f,
                -90
        );
        tpl.endText();

        return tpl;
    }

    /**
     * Draw the splash part.
     *
     * @param cb The PdfContentByte.
     * @return The PdfTemplate containing the splash part.
     */
    private PdfTemplate drawSplash(PdfContentByte cb) {

        float widthTitle = fontBold.getWidthPoint(title, 6);
        float widthDesc = fontRegular.getWidthPoint(description, 4);


        float stripWidth = widthTitle > widthDesc ? widthTitle : widthDesc;
        stripWidth = stripWidth + mm2dtp(4);
        float stripHeight = patchHeight + borderBottom + borderTop;

        PdfTemplate tplSplash = cb.createTemplate(stripWidth, stripHeight);

        float refLine = borderBottom + patchHeight * 0.5f;

        // strip name
        tplSplash.beginText();
        tplSplash.setFontAndSize(fontBold, 6);
        tplSplash.showTextAligned(
                PdfContentByte.ALIGN_LEFT,
                title,
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
                description,
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
        float stripHeight = patchHeight + borderBottom + borderTop;

        PdfTemplate tpl = cb.createTemplate(stripWidth, stripHeight);

        // draw line
        tpl.setCMYKColorStrokeF(0f, 0f, 0f, 1.0f);
        tpl.setLineWidth(1f);
        tpl.moveTo(stripWidth, borderBottom);
        tpl.lineTo(stripWidth, borderBottom + patchHeight);
        tpl.stroke();

        // draw "Start"
        tpl.beginText();
        tpl.setCMYKColorFillF(0f, 1f, 1f, 0f);
        tpl.setFontAndSize(fontBold, fontSize);
        tpl.showTextAligned(
                PdfContentByte.ALIGN_CENTER,
                "Start",
                stripWidth - 4.5f,
                borderBottom + patchHeight * 0.5f,
                -90
        );
        tpl.endText();

        return tpl;
    }

    private PdfTemplate drawSpacer(PdfContentByte cb, float stripWidth) {
        float stripHeight = patchHeight + borderBottom + borderTop;

        PdfTemplate tpl = cb.createTemplate(stripWidth, stripHeight);

        tpl.setCMYKColorStrokeF(0f, 0f, 0f, 1.0f);
        tpl.setLineWidth(0.5f);
        tpl.setLineDash(1f, 1f);
        tpl.moveTo(0, borderBottom + 0.25f);
        tpl.lineTo(stripWidth, borderBottom + 0.25f);
        tpl.moveTo(0, borderBottom + patchHeight - 0.25f);
        tpl.lineTo(stripWidth, borderBottom + patchHeight - 0.25f);
        tpl.stroke();


        // return result
        return tpl;
    }

    /**
     * Draw all patches of a patch group..
     *
     * @param cb The PdfContentByte
     * @return The PdfTemplate containing the start part.
     */
    private PdfTemplate drawPatchGroup(PdfContentByte cb, String groupName, Patch[] patchGroup) {
        List<Patch> patches = new ArrayList<>(Arrays.asList(patchGroup));
        float stripWidth = patchWidth * patches.size();
        float stripHeight = patchHeight + borderBottom + borderTop;

        PdfTemplate tpl = cb.createTemplate(stripWidth, stripHeight);

        // draw patches
        for (int i = 0; i < patches.size(); i++) {
            Patch patch = patches.get(i);

            // draw patch
            CMYKColor cmykColor = new CMYKColor(
                    (float) patch.getCyan() / 100f,
                    (float) patch.getMagenta() / 100f,
                    (float) patch.getYellow() / 100f,
                    (float) patch.getBlack() / 100f
            );

            Rectangle rectPatch = new Rectangle(
                    patchWidth * i,
                    borderBottom,
                    patchWidth * (i + 1),
                    patchHeight + borderBottom
            );
            rectPatch.setBackgroundColor(cmykColor);
            tpl.rectangle(rectPatch);

            // draw name
            tpl.beginText();
            tpl.setFontAndSize(fontRegular, fontSize);
            tpl.showTextAligned(
                    PdfContentByte.ALIGN_CENTER,
                    patch.getName(),
                    patchWidth * i + patchWidth * 0.5f,
                    2f,
                    0
            );
            tpl.endText();
        }

        // draw line
        float y = borderBottom + patchHeight + borderTop / 2f;
        float x1 = patchWidth * 0.2f;
        float x2 = stripWidth - patchWidth * 0.2f;

        tpl.setCMYKColorStrokeF(0f, 0f, 0f, 1.0f);
        tpl.setLineWidth(0.5f);
        tpl.moveTo(x1, y - borderTop / 4f);
        tpl.lineTo(x1, y);
        tpl.lineTo(x2, y);
        tpl.lineTo(x2, y - borderTop / 4f);
        tpl.stroke();

        // group name background
        if (groupName != null && !Objects.equals(groupName, "")) {
            float textWidth = fontBold.getWidthPoint(groupName, fontSize);

            CMYKColor cmykColor = new CMYKColor(0f, 0f, 0f, 0f);
            Rectangle rectPatch = new Rectangle(
                    stripWidth / 2f - textWidth / 2f - 5f,
                    borderBottom + patchHeight + 0.5f,
                    stripWidth / 2f + textWidth / 2f + 5f,
                    borderBottom + patchHeight + borderTop
            );
            rectPatch.setBackgroundColor(cmykColor);
            tpl.rectangle(rectPatch);


            // group name
            tpl.beginText();
            tpl.setFontAndSize(fontBold, fontSize + 1);
            tpl.showTextAligned(
                    PdfContentByte.ALIGN_CENTER,
                    groupName,
                    stripWidth * 0.5f,
                    y - 1f,
                    0
            );
            tpl.endText();
        }

        // return patches
        return tpl;
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
