import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.BorderCollapsePropertyValue;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;

import static jdk.nashorn.internal.runtime.ECMAErrors.getMessage;

class User
{
    String name="Samin Yeaser";
    String father="Neyamul Kabir";
    String mother="Bilkis Akter";
    String dob="07/08/1997";
    String id="200029133421";
}

public class PDFExample {
    public static float cardHeight = 149.94f;//147.94f;
    public static float cardWidth = 239.69f;//240.69f;
    public static float totalCardWidth = 491.38f;
    public static float centerWidthBetweenFrontAndBack = 6.30f;//6.62f;
    public static float cardFrontTopHeight = 46.0f;//43.0f;
    public static float cardFrontBottomHeight = cardHeight - cardFrontTopHeight - 2;
    public static float marginLeft = 54.85f;//54.77f;//56.88f;
    public static float marginRight = 56.87f;//56.88f;
    public static float marginTop = 40f;//52.80f;//53.80f;
    public static float marginBottom = 25f;
    public static float pageWidth = 612f;
    public static float pageHeight = 792f;
    private int SectionHeaderFontSize = 12;
    private int HeaderFontSize = 10;
    private int RegularFontSize = 8;
    private int SmallFontSize = 6;

    public static String getBase64FromImageURL(String url) {
        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();
            InputStream is = ucon.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception ex) {
            return "";
        }
    }

    public static String base64FromFilePath(String path) {
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(path));
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception ex) {
            return null;
        }
    }
    static public void main(String args[]) throws IOException {
        String dest="exx.pdf";
        String engs="1234567";
        String bans="আমার নাম অমি";
        Text text=new Text("আমার নাম অমি");
        //System.out.println(new Paragraph().getWidth());
        PdfWriter writer=new PdfWriter(dest);
        PdfDocument pdf=new PdfDocument(writer);
        Document document=new Document(pdf,new PageSize(PageSize.A4.rotate()));
        document.add(new Paragraph("Hello World"));
        Map<String,String> revenueData = new HashMap<String,String>();
        revenueData.put("1/20/2010", "$100,000");
        revenueData.put("1/21/2010", "$200,000");
        revenueData.put("1/22/2010", "$300,000");
        revenueData.put("1/23/2010", "$400,000");
        revenueData.put("1/24/2010", "$500,000");
        Table table = new Table(new float[] { 1, 3 });
        Paragraph p=new Paragraph("omi Omi Omi Omi");
        p.setWidth(30);
        document.add(p);
        table.addCell("Month");
        table.addCell(new Cell().add(new Paragraph("Revenue")).setWidth(200));
        for (Map.Entry<String, String> entry : revenueData.entrySet()) {
            table.addCell(entry.getKey());
            table.addCell(entry.getValue());
        }
        User user=new User();
        document.add(table);
        Table FullCard = new Table(new float[]{cardWidth, centerWidthBetweenFrontAndBack, cardWidth});
        FullCard.setBorderCollapse(BorderCollapsePropertyValue.SEPARATE);
        FullCard.setVerticalBorderSpacing(15f);
        FullCard.setWidth(totalCardWidth).setFixedLayout();
        //Image gLogo = getGlogo(base64FromFilePath(PDFExample.class.getClassLoader().getResource("govtLogo.jpeg").getPath()));
        SingleCard(FullCard, pdf);
        document.add(FullCard);
        document.close();


    }
    static Image getGlogo(String imageString) {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageByte = new byte[0];
        try {
//            imageByte = decoder.decodeBuffer("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");
            imageByte = decoder.decodeBuffer(imageString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int imageHeight = getImageHeight(imageByte, 28);//28 is the image width

        ImageData imageData = ImageDataFactory.create(imageByte);
        Image image = new Image(imageData);

        image.scaleAbsolute(28, imageHeight);
        return image;
    }
    public static int getImageHeight(byte[] imageByte, int width) {
        int height = 0;
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(bis);
            System.out.println("going to read image.."+bufferedImage);
            int imageWidth = bufferedImage.getWidth();
            int imageHeight = bufferedImage.getHeight();


            double aspectRatio = (double) imageHeight / (double) imageWidth;
            height = (int) (aspectRatio * width);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
        return height;
    }
    static void setCellBorder(Cell cell, float top, float left, float bottom, float right) {
        cell.setBorderTop(new SolidBorder(top));
        cell.setBorderLeft(new SolidBorder(left));
        cell.setBorderBottom(new SolidBorder(bottom));
        cell.setBorderRight(new SolidBorder(right));
    }

    static void setCellPadding(Cell cell, float top, float left, float bottom, float right) {
        cell.setPaddingTop(top);
        cell.setPaddingLeft(left);
        cell.setPaddingBottom(bottom);
        cell.setPaddingRight(right);
    }
    public static void SingleCard(Table FullCard, PdfDocument pdfDocument) throws IOException {
        Cell frontCell = new Cell();
        frontCell.setPadding(0);
        setCellBorder(frontCell, 1f, 1f, 1f, 1f);
        frontCell.setHeight(cardHeight);

        // front cell coding
        Table fTable = new Table(1);
        fTable.useAllAvailableWidth();

        // front cell top coding start
        Cell fTopCell = new Cell();
        fTopCell.setPadding(0);
        fTopCell.setHeight(cardFrontTopHeight);
//        setCellBorder(fTopCell, 0f, 0f, 1f, 0f);
        fTopCell.setBorderTop(Border.NO_BORDER);fTopCell.setBorderLeft(Border.NO_BORDER);fTopCell.setBorderRight(Border.NO_BORDER);
        fTopCell.setBorderBottom(new SolidBorder(1));
        setCellPadding(fTopCell, 0, 0, 0, 0);
        fTopCell.setHorizontalAlignment(HorizontalAlignment.LEFT);


        Table fTopTable = new Table(new float[]{30, 209});
        generateFrontTopTable(fTopTable);
        fTopCell.add(fTopTable);
        fTable.addCell(fTopCell);
        frontCell.add(fTable);
        FullCard.addCell(frontCell);

    }

    public static void generateFrontTopTable(Table fTopTable) throws IOException {
        fTopTable.useAllAvailableWidth();
        Cell fTopTableLeftCell = new Cell();
        setCellPadding(fTopTableLeftCell, 6f, 3.5f, 0f, 0f);
        fTopTableLeftCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        fTopTableLeftCell.setBorder(Border.NO_BORDER);
//        setCellBorder(fTopTableLeftCell, 0f, 0f, 0f, 0f);
        fTopTableLeftCell.add(new Paragraph("Image"));
        fTopTable.addCell(fTopTableLeftCell);
        Cell fTopTableRightCell = new Cell();
//        setCellBorder(fTopTableRightCell, 0f, 0f, 0f, 0f);
        setCellPadding(fTopTableRightCell, 5f, 0f, 0f, 0f);
        Paragraph p1 = new Paragraph();
//        p1.setPaddingBottom(1);
        p1.setMarginBottom(1);
//        todo: this seems fishy
//        p1.setFirstLineIndent(1);
//        p1.setMarginLeft(12);
        p1.setFixedLeading(13);
        p1.setTextAlignment(TextAlignment.CENTER);
        Paragraph p2 = new Paragraph();
//        p2.setPaddingBottom(0);
        p2.setMarginBottom(0);
//        todo: this seems fishy
//        p1.setFirstLineIndent(12);
//        p1.setMarginLeft(12);
        p2.setTextAlignment(TextAlignment.CENTER);

        p2.setFixedLeading(9.5f);
        Paragraph p3 = new Paragraph();
//        p3.setPaddingBottom(2);
        p3.setMarginBottom(2);
        p3.setTextAlignment(TextAlignment.CENTER);
//        todo: this seems fishy
//        p3.setFirstLineIndent(15);
        p3.setMarginLeft(0);
        p3.setFixedLeading(13.3f);

        p1.add(new Text("Bangladesh"));
        fTopTableRightCell.add(p1);
        p2.add(new Text("Republic of Bangladesh"));
        fTopTableRightCell.add(p2);

        Text c1 = new Text("NID");
        Text c2 = new Text(" / ");
        Text c3 = new Text("JPP");

        p3.add(c1);
        p3.add(c2);
        p3.add(c3);

        fTopTableRightCell.add(p3);

        fTopTableLeftCell.setBorder(Border.NO_BORDER);
        fTopTableRightCell.setBorder(Border.NO_BORDER);

        fTopTable.addCell(fTopTableRightCell);
    }
}
