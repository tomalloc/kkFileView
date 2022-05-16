package cn.keking.utils;



import com.twelvemonkeys.imageio.plugins.jpeg.JPEGImageReader;
import com.twelvemonkeys.imageio.plugins.tiff.TIFFImageReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.CCITTFactory;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConvertPicUtil {

    private final static Logger logger = LoggerFactory.getLogger(ConvertPicUtil.class);

    /**
     * Tif 转  JPG。
     * @param strInputFile  输入文件的路径和文件名
     * @param strOutputFile 输出文件的路径和文件名
     * @return boolean 是否转换成功
     */
    public static List<String> convertTif2Jpg(String strInputFile, String strOutputFile) {
        List<String> listImageFiles = new ArrayList<>();

        if (strInputFile == null || "".equals(strInputFile.trim())) {
            return null;
        }
        if (!new File(strInputFile).exists()) {
            logger.info("找不到文件【" + strInputFile + "】");
            return null;
        }

        strInputFile = strInputFile.replaceAll("\\\\", "/");
        strOutputFile = strOutputFile.replaceAll("\\\\", "/");
        TIFFImageReader tiffImageReader = null;
        try(ImageInputStream imageInputStream = ImageIO.createImageInputStream(Files.newInputStream(Paths.get(strInputFile)))) {
            Iterator<ImageReader> iter = ImageIO.getImageReaders(imageInputStream);
            while (iter.hasNext()) {
                ImageReader imageReader = iter.next();
                if(imageReader instanceof TIFFImageReader){
                    tiffImageReader= (TIFFImageReader) imageReader;
                }
            }
            if(tiffImageReader==null){
                logger.warn("file format error:{}",strInputFile);
                return null;
            }
            ImageReadParam imageReadParam = tiffImageReader.getDefaultReadParam();
            tiffImageReader.setInput(imageInputStream, true, true);
            int intTifCount = tiffImageReader.getNumImages(false);

            String strFilePrefix = strInputFile.substring(strInputFile.lastIndexOf("/") + 1, strInputFile.lastIndexOf("."));


            logger.info("该tif文件共有【" + intTifCount + "】页");

            String strJpgPath = "";
            String strJpgUrl = "";
            if (intTifCount == 1) {
                // 如果是单页tif文件，则转换的目标文件夹就在指定的位置
                strJpgPath = strOutputFile.substring(0, strOutputFile.lastIndexOf("/"));
            } else {
                // 如果是多页tif文件，则在目标文件夹下，按照文件名再创建子目录，将转换后的文件放入此新建的子目录中
                strJpgPath = strOutputFile.substring(0, strOutputFile.lastIndexOf("."));
            }

            // 处理目标文件夹，如果不存在则自动创建
            File fileJpgPath = new File(strJpgPath);
            if (!fileJpgPath.exists()) {
                fileJpgPath.mkdirs();
            }

            // 循环，处理每页tif文件，转换为jpg
            for (int i = 0; i < intTifCount; i++) {
                String strJpg = "";
                if(intTifCount == 1){
                    strJpg = strJpgPath + "/" + strFilePrefix + ".jpg";
                    strJpgUrl = strFilePrefix + ".jpg";
                }else{
                    strJpg = strJpgPath + "/" + i + ".jpg";
                    strJpgUrl = strFilePrefix + "/" + i + ".jpg";
                }

                File fileJpg = new File(strJpg);

                // 如果文件不存在，则生成
                if(!fileJpg.exists()){
                    BufferedImage readImage = tiffImageReader.read(i, imageReadParam);
                    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

                    if (!writers.hasNext()) {
                        throw new IllegalArgumentException("No writer for: jpg");
                    }
                    ImageWriter writer = writers.next();
                    try(ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(Files.newOutputStream(fileJpg.toPath()))) {
                        writer.setOutput(imageOutputStream);
                        ImageWriteParam param = writer.getDefaultWriteParam();
                        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                        param.setCompressionQuality(0.4f);
                        writer.write(null,new IIOImage(readImage, null, null),param);
                    } finally {
                        writer.dispose();
                    }
                    logger.info("每页分别保存至： " + fileJpg.getCanonicalPath());
                }else{
                    logger.info("JPG文件已存在： " + fileJpg.getCanonicalPath());
                }

                listImageFiles.add(strJpgUrl);
            }

            return listImageFiles;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(tiffImageReader!=null){
                tiffImageReader.dispose();
            }
        }
    }


    /**
     * 将Jpg图片转换为Pdf文件
     *
     * @param strJpgFile 输入的jpg的路径和文件名
     * @param strPdfFile 输出的pdf的路径和文件名
     * @return
     */
    public static File convertJpg2Pdf(String strJpgFile, String strPdfFile) {
        JPEGImageReader jpgImageReader=null;

        try(PDDocument doc = new PDDocument();ImageInputStream imageInputStream = ImageIO.createImageInputStream(Files.newInputStream(Paths.get(strJpgFile)))) {
            Iterator<ImageReader> iter = ImageIO.getImageReaders(imageInputStream);
            while (iter.hasNext()) {
                ImageReader imageReader = iter.next();
                if (imageReader instanceof JPEGImageReader) {
                    jpgImageReader = (JPEGImageReader) imageReader;
                }
            }
            if (jpgImageReader == null) {
                logger.warn("file format error:{}", strJpgFile);
                return null;
            }
            ImageReadParam imageReadParam = jpgImageReader.getDefaultReadParam();
            jpgImageReader.setInput(imageInputStream, true, true);
            BufferedImage jpgImage = jpgImageReader.read(0,imageReadParam);
//            PDImageXObject.createFromFile()
            PDImageXObject pdfImage = JPEGFactory.createFromImage(doc,jpgImage);
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.drawImage(pdfImage, 0, 0, jpgImage.getWidth(), jpgImage.getHeight());
            contents.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    /**
     * 将Tif图片转换为Pdf文件（支持多页Tif）
     *
     * @param strTifFile 输入的tif的路径和文件名
     * @param strPdfFile 输出的pdf的路径和文件名
     * @return
     */
    public static File convertTif2Pdf(String strTifFile, String strPdfFile) {
        TIFFImageReader tiffImageReader=null;

        try(PDDocument doc = new PDDocument();
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(Files.newInputStream(Paths.get(strTifFile)))) {
            Iterator<ImageReader> iter = ImageIO.getImageReaders(imageInputStream);
            while (iter.hasNext()) {
                ImageReader imageReader = iter.next();
                if (imageReader instanceof TIFFImageReader) {
                    tiffImageReader = (TIFFImageReader) imageReader;
                }
            }
            if (tiffImageReader == null) {
                logger.warn("file format error:{}", strTifFile);
                return null;
            }
            ImageReadParam imageReadParam = tiffImageReader.getDefaultReadParam();
            tiffImageReader.setInput(imageInputStream, true, true);
            int intTifCount = tiffImageReader.getNumImages(false);
            for (int i = 0; i < intTifCount; i++) {
                BufferedImage tiffImage = tiffImageReader.read(i, imageReadParam);
                PDImageXObject tiffpdfImage = CCITTFactory.createFromImage(doc,tiffImage);
                PDPage page = new PDPage();
                doc.addPage(page);
                PDPageContentStream contents = new PDPageContentStream(doc, page);
                contents.drawImage(tiffpdfImage, 0, 0, tiffImage.getWidth(), tiffImage.getHeight());
                contents.close();
            }
            File pdfFile = new File(strPdfFile);
            doc.save(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}