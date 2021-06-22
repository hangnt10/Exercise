package Word;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import Utilities.Client;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;

public class ReportToWord {
    Client client;

    public ReportToWord(Client client) {
        this.client = client;
    }

    public void addImagesToWordDocument(File imageFile1)
            throws IOException, InvalidFormatException, InterruptedException {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph para = doc.createParagraph();
        para.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = para.createRun();
        BufferedImage bimg = ImageIO.read(imageFile1);
        int width = bimg.getWidth();
        int height = bimg.getHeight();

        float scale = (float) (400.0/width);
        if (scale < 1) {
            width= Math.round(width*scale);
            height = Math.round(height * scale);
        }
        String imgFile = imageFile1.getName();
        int imgFormat = getImageFormat(imgFile);
        String p1 = "Test Case Report";
        run.setText(p1);
        run.addBreak();
        run.addPicture(new FileInputStream(imageFile1), imgFormat, imgFile, Units.toEMU(width), Units.toEMU(height));
        // page break
        FileOutputStream out = new FileOutputStream(client.title + "Report/word_images.docx");
        doc.write(out);
        out.close();
        doc.close();
    }

    public void addTexttoWorđocument(String content) throws Exception{

    }

    private int getImageFormat(String imgFileName) {
        int format;
        if (imgFileName.endsWith(".emf"))
            format = XWPFDocument.PICTURE_TYPE_EMF;
        else if (imgFileName.endsWith(".wmf"))
            format = XWPFDocument.PICTURE_TYPE_WMF;
        else if (imgFileName.endsWith(".pict"))
            format = XWPFDocument.PICTURE_TYPE_PICT;
        else if (imgFileName.endsWith(".jpeg") || imgFileName.endsWith(".jpg"))
            format = XWPFDocument.PICTURE_TYPE_JPEG;
        else if (imgFileName.endsWith(".png"))
            format = XWPFDocument.PICTURE_TYPE_PNG;
        else if (imgFileName.endsWith(".dib"))
            format = XWPFDocument.PICTURE_TYPE_DIB;
        else if (imgFileName.endsWith(".gif"))
            format = XWPFDocument.PICTURE_TYPE_GIF;
        else if (imgFileName.endsWith(".tiff"))
            format = XWPFDocument.PICTURE_TYPE_TIFF;
        else if (imgFileName.endsWith(".eps"))
            format = XWPFDocument.PICTURE_TYPE_EPS;
        else if (imgFileName.endsWith(".bmp"))
            format = XWPFDocument.PICTURE_TYPE_BMP;
        else if (imgFileName.endsWith(".wpg"))
            format = XWPFDocument.PICTURE_TYPE_WPG;
        else {
            return 0;
        }
        return format;
    }



}
