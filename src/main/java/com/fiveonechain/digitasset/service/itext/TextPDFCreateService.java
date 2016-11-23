package com.fiveonechain.digitasset.service.itext;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fanjl on 2016/11/22.
 */
public class TextPDFCreateService {

    public static final String DEST = "results/objects/chapter_title.pdf";
    public static final String DEST1 = "results/objects/chunk_background.pdf";
    public static final String DEST2 = "results/objects/ordinal_numbers.pdf";
    public static final String DEST3 = "results/objects/standard_deviation.pdf";
    public static final String DEST4 = "results/objects/bullets.pdf";
    public static final String[] ITEMS = {
            "Insurance system", "Agent", "Agency", "Agent Enrollment", "Agent Settings",
            "Appointment", "Continuing Education", "Hierarchy", "Recruiting", "Contract",
            "Message", "Correspondence", "Licensing", "Party"
    };
    public static final String DEST5 = "results/objects/indentation_options.pdf";
    public static final String LABEL = "A list of stuff: ";
    public static final String CONTENT = "test A, test B, coconut, coconut, watermelons, apple, oranges, many more " +
            "fruites, carshow, monstertrucks thing, everything is startting on the " +
            "same point in the line now";

    public static final String SRC = "results/objects/chapter_title.pdf";
    public static final String DEST6 = "results/objects/chapter_title_with_image_id.pdf";
    public static final String IMG = "resources/image/111.png";

    public static final String IMAGE = "resources/image/111.png";
    public static final String MASK = "resources/image/1.png";
    public static final String DEST7 = "results/objects/jpg_mask.pdf";



    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST7);
        file.getParentFile().mkdirs();
        new TextPDFCreateService().createmaskImagePdf(DEST7);
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        Chunk chunk = new Chunk("This is the title", chapterFont);
        Chapter chapter = new Chapter(new Paragraph(chunk), 1);
        chapter.setNumberDepth(0);
        chapter.add(new Paragraph("This is the paragraph", paragraphFont));
        document.add(chapter);
        document.close();
    }
    public void createRedPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Font f = new Font(FontFamily.TIMES_ROMAN, 25.0f, Font.BOLD, BaseColor.WHITE);
        Chunk c = new Chunk("White text on red background", f);
        c.setBackground(BaseColor.RED);
        Paragraph p = new Paragraph(c);
        document.add(p);
        document.close();
    }


    public void createNumPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Font small = new Font(FontFamily.HELVETICA, 6);
        Chunk st = new Chunk("st", small);
        st.setTextRise(7);
        Chunk nd = new Chunk("nd", small);
        nd.setTextRise(7);
        Chunk rd = new Chunk("rd", small);
        rd.setTextRise(7);
        Chunk th = new Chunk("th", small);
        th.setTextRise(7);
        Paragraph first = new Paragraph();
        first.add("The 1");
        first.add(st);
        first.add(" of May");
        document.add(first);
        Paragraph second = new Paragraph();
        second.add("The 2");
        second.add(nd);
        second.add(" and the 3");
        second.add(rd);
        second.add(" of June");
        document.add(second);
        Paragraph fourth = new Paragraph();
        fourth.add("The 4");
        fourth.add(rd);
        fourth.add(" of July");
        document.add(fourth);
        document.close();
    }

    public void createstandardPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        document.add(new Paragraph("The standard deviation symbol doesn't exist in Helvetica."));
        Font symbol = new Font(FontFamily.SYMBOL);
        Paragraph p = new Paragraph("So we use the Symbol font: ");
        p.add(new Chunk("s", symbol));
        document.add(p);
        document.close();
    }

    public void createbulletPdf(String dest) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Font zapfdingbats = new Font(FontFamily.ZAPFDINGBATS, 8);
        Font font = new Font();
        Chunk bullet = new Chunk(String.valueOf((char) 108), zapfdingbats);

        Paragraph p = new Paragraph("Items can be split if they don't fit at the end: ", font);
        for (String item: ITEMS) {
            p.add(bullet);
            p.add(new Phrase(" " + item + " ", font));
        }
        document.add(p);
        document.add(Chunk.NEWLINE);

        p = new Paragraph("Items can't be split if they don't fit at the end: ", font);
        for (String item: ITEMS) {
            p.add(bullet);
            p.add(new Phrase("\u00a0" + item.replace(' ', '\u00a0') + " ", font));
        }
        document.add(p);
        document.add(Chunk.NEWLINE);

        Font f = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        p = new Paragraph("Items can't be split if they don't fit at the end: ", f);
        for (String item: ITEMS) {
            p.add(new Phrase("\u2022\u00a0" + item.replace(' ', '\u00a0') + " ", f));
        }
        document.add(p);

        document.close();
    }
    public void createindentation_optionsPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        List list = new List();
        list.setListSymbol(new Chunk(LABEL));
        list.add(CONTENT);
        document.add(list);
        document.add(Chunk.NEWLINE);

        BaseFont bf = BaseFont.createFont();
        Paragraph p = new Paragraph(LABEL + CONTENT, new Font(bf, 12));
        float indentation = bf.getWidthPoint(LABEL, 12);
        p.setIndentationLeft(indentation);
        p.setFirstLineIndent(-indentation);
        document.add(p);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(2);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.setTotalWidth(new float[]{indentation + 4, 519 - indentation});
        table.setLockedWidth(true);
        table.addCell(LABEL);
        table.addCell(CONTENT);
        document.add(table);

        document.close();
    }

    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        Image image = Image.getInstance(IMG);
        PdfImage stream = new PdfImage(image, "", null);
        stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
        PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
        image.setDirectReference(ref.getIndirectReference());
        image.setAbsolutePosition(50, 200);
        PdfContentByte over = stamper.getOverContent(1);
        over.addImage(image);
        stamper.close();
        reader.close();
    }

    public void createmaskImagePdf(String dest) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Image image = Image.getInstance(IMAGE);
        Image mask = makeBlackAndWhitePng(MASK);
        mask.makeMask();
        image.setImageMask(mask);
        image.scaleAbsolute(PageSize.A4.rotate());
        image.setAbsolutePosition(0, 0);
        document.add(image);
        document.close();
    }

    public static Image makeBlackAndWhitePng(String image) throws IOException, DocumentException {
        BufferedImage bi = ImageIO.read(new File(image));
        BufferedImage newBi = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
        newBi.getGraphics().drawImage(bi, 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newBi, "png", baos);
        return Image.getInstance(baos.toByteArray());
    }
}
