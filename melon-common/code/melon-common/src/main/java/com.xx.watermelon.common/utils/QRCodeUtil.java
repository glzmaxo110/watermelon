package com.zc.travel.common.utils;  
  
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;
/** 
 * 二维码生成工具 
 * @author ljheee 
 * 
 */  
public class QRCodeUtil {  
  
    private static String charset = "utf-8";
    private static String formatName = "JPG";
    // 二维码尺寸  
    private static int qrcodeSize = 130;
    // LOGO宽度
    private static int widths = 60;
    // 二维码尺寸
    private static int heights = 60;
      
    /**
     * 设置二维码参数
     * @param qrcodeSize
     * @param width
     * @param height
     */
    public static void setQrcode(String charset,String formatName,int qrcodeSize,int width,int height){
    	if(StringUtils.isNotBlank(charset))
    		QRCodeUtil.charset = charset;
    	if(StringUtils.isNotBlank(formatName))
    		QRCodeUtil.formatName = formatName;
    	if(qrcodeSize != 0)
    		QRCodeUtil.qrcodeSize = qrcodeSize;
    	if(width != 0)
    		QRCodeUtil.widths = width;
    	if(height != 0){
    		QRCodeUtil.heights = height;
    	}
    	
    }
    
    /** 
     * 生成二维码 
     * @param content   源内容 
     * @param imgPath   生成二维码保存的路径 
     * @param needCompress  是否要压缩 
     * @return      返回二维码图片 
     * @throws Exception 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {  
        Hashtable hints = new Hashtable();  
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
        hints.put(EncodeHintType.CHARACTER_SET, charset);  
        hints.put(EncodeHintType.MARGIN, 1);  
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrcodeSize, qrcodeSize,  
                hints);  
        int width = bitMatrix.getWidth();  
        int height = bitMatrix.getHeight();  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        for (int x = 0; x < width; x++) {  
            for (int y = 0; y < height; y++) {  
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);  
            }  
        }  
        if (imgPath == null || "".equals(imgPath)) {  
            return image;  
        }  
        // 插入图片  
        QRCodeUtil.insertImage(image, imgPath, needCompress);  
        return image;  
    }  
  
    /** 
     * 在生成的二维码中插入图片 
     * @param source 
     * @param imgPath 
     * @param needCompress 
     * @throws Exception 
     */  
    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {  
        File file = new File(imgPath);  
        if (!file.exists()) {  
            System.err.println("" + imgPath + "   该文件不存在！");  
            return;  
        }  
        Image src = ImageIO.read(new File(imgPath));  
        int width = src.getWidth(null);  
        int height = src.getHeight(null);  
        if (needCompress) { // 压缩LOGO  
            if (width > widths) {  
                width = widths;  
            }  
            if (height > heights) {  
                height = heights;  
            }  
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);  
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
            Graphics g = tag.getGraphics();  
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
            g.dispose();  
            src = image;  
        }  
        // 插入LOGO  
        Graphics2D graph = source.createGraphics();  
        int x = (qrcodeSize - width) / 2;  
        int y = (qrcodeSize - height) / 2;  
        graph.drawImage(src, x, y, width, height, null);  
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);  
        graph.setStroke(new BasicStroke(3f));  
        graph.draw(shape);  
        graph.dispose();  
    }  
  
    /** 
     * 生成带logo二维码，并保存到磁盘 
     * @param content 
     * @param imgPath   logo图片 
     * @param destPath 
     * @param needCompress 
     * @throws Exception 
     */  
    public static void encode(String content, String imgPath, String destPath, boolean needCompress) throws Exception {  
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);  
        mkdirs(destPath);  
        String file = new Random().nextInt(99999999) + ".jpg";//生成随机文件名  
        ImageIO.write(image, formatName, new File(destPath + "/" + file));  
    }  
    
    /** 
     * 生成带logo二维码，并保存到磁盘 
     * @param content 
     * @param imgPath   logo图片 
     * @param needCompress 
     * @throws Exception 
     */  
    public static String encodeBase64(String content, String imgPath, boolean needCompress) throws Exception {  
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ImageIO.write(image, "jpg", out);  
        byte[] b = out.toByteArray(); 
        return Base64.encode(b);
    }
    
    /** 
     * 生成带logo二维码，并保存到磁盘 
     * @param content 
     * @throws Exception 
     */  
    public static void decodeBase64(String content, String destPath) throws Exception {  
         byte[] b = Base64.decodeToBytes(content);
         ByteArrayInputStream bin = new ByteArrayInputStream(b);
         BufferedImage image = ImageIO.read(bin);  
         mkdirs(destPath);  
         String file = "000000.jpg";//生成随机文件名
         ImageIO.write(image, formatName, new File(destPath + "/" + file)); 
    }
  
    public static void mkdirs(String destPath) {  
        File file = new File(destPath);  
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir。(mkdir如果父目录不存在则会抛出异常)  
        if (!file.exists() && !file.isDirectory()) {  
            file.mkdirs();  
        }  
    }  
  
    public static void encode(String content, String imgPath, String destPath) throws Exception {  
        QRCodeUtil.encode(content, imgPath, destPath, false);  
    }  
  
    public static void encode(String content, String destPath, boolean needCompress) throws Exception {  
        QRCodeUtil.encode(content, null, destPath, needCompress);  
    }  
  
    public static void encode(String content, String destPath) throws Exception {  
        QRCodeUtil.encode(content, null, destPath, false);  
    }  
  
    public static void encode(String content, String imgPath, OutputStream output, boolean needCompress)  
            throws Exception {  
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);  
        ImageIO.write(image, formatName, output);  
    }  
  
    public static void encode(String content, OutputStream output) throws Exception {  
        QRCodeUtil.encode(content, null, output, false);  
    }  
  
      
    /** 
     * 从二维码中，解析数据 
     * @param file  二维码图片文件 
     * @return   返回从二维码中解析到的数据值 
     * @throws Exception 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static String decode(File file) throws Exception {  
        BufferedImage image;  
        image = ImageIO.read(file);  
        if (image == null) {  
            return null;  
        }  
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);  
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
        Result result;  
        Hashtable hints = new Hashtable();  
        hints.put(DecodeHintType.CHARACTER_SET, charset);  
        result = new MultiFormatReader().decode(bitmap, hints);  
        String resultStr = result.getText();  
        return resultStr;  
    }  
  
    public static String decode(String path) throws Exception {  
        return QRCodeUtil.decode(new File(path));  
    } 
    
    /**
     * 生成带背景图片的二维码
     * @param content 网址
     * @param imgPath  APP邀请二维码背景图片
     * @param needCompress 是否要压缩
     * @param inviteCode 邀请码 
     * @param memberName 邀请人
     * @return
     * @throws Exception
     */
    public static String appEncodeBase64(String content, String imgPath, boolean needCompress,String inviteCode,String memberName) throws Exception {  
    	setQrcode("utf-8", "jpg", 320, 170, 170);
    	BufferedImage image = QRCodeUtil.createImage(content, null, needCompress);
        Image srcImage = (Image)image;
        BufferedImage markImageEncodeByIcon = WaterMarkUtils.markImageEncodeByIcon(srcImage,imgPath, 0,inviteCode,memberName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ImageIO.write(markImageEncodeByIcon, "jpg", out); 
        byte[] b = out.toByteArray(); 
        return Base64.encode(b);
    }
    
    
    public static void main(String[] args) {
        try {
        	//生成带logo 的二维码   
            /*String text = "http://my.csdn.net/ljheee";  
            QRCodeUtil.encode(text, "11.JPG", "d:/WPS", true); */
            //生成不带logo 的二维码  
            String textt = "http://www.baidu.com";
            //String url = QRCodeUtil.encodeBase64(textt, "", true);
            String url = QRCodeUtil.appEncodeBase64(textt, "E:/invitationImage.png", true,"035698","小志志");
            System.out.println(url);
			QRCodeUtil.decodeBase64(url, "e:/");
			 //指定二维码图片，解析返回数据  
	        //System.out.println(QRCodeUtil.decode("D:/WPS/21718050.jpg"));  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
} 