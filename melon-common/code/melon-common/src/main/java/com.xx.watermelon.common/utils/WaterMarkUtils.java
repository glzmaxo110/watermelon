package com.zc.travel.common.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class WaterMarkUtils {
	// 水印透明度
    private static float alpha = 0.5f;
    // 字体
    private static String fontName = "微软雅黑";
    // 字体风格(普通)
    private static int fontStyle = Font.PLAIN;
    // 字体大小
    private static int fontSize = 45;
    // 水印文字
    private static String logoText = "组团助手网验证使用,其它使用无效";
    // 水印旋转角度
    private static Integer degree = -45;
    // 水印文字颜色
    private static Color color = Color.gray;
    //水印图片间隔
    private static int interval = 1;
    
    /**
     * 设置图片添加水印参数
     * @param alpha 	水印透明度
     * @param fontName 	水印字体
     * @param fontStyle 水印字体风格
     * @param fontSize 	水印字体大小
     * @param colorCode 水印文字颜色
     * @param logoText 	水印文字
     * @param degree  	水印旋转角度
     * @param interval  水印图片间隔
     */
    public static void setImageMarkOptions(float alpha, String fontName, int fontStyle, int fontSize ,String colorCode , String logoText ,Integer degree,int interval) {
        if (alpha != 0.0f)
        	WaterMarkUtils.alpha = alpha;
        if (StringUtils.isNotBlank(fontName))
        	WaterMarkUtils.fontName = fontName;
        if (fontStyle != 0)
        	WaterMarkUtils.fontStyle = fontStyle;
        if (fontSize != 0)
        	WaterMarkUtils.fontSize = fontSize;
        if (StringUtils.isNotBlank(colorCode))
        	WaterMarkUtils.color = Color.decode(colorCode);
        if (StringUtils.isNotBlank(logoText))
        	WaterMarkUtils.logoText = logoText;
        if (null != degree)
        	WaterMarkUtils.degree = degree;
        if (interval != 0)
        	WaterMarkUtils.interval = interval;
    }
    
    
    /**
     * 设置APP邀请码二维码图片的背景图
     * @param iconPath		水印图片(二维码)
     * @param srcImgPath 	二维码背景图片
     * @param degree 		水印图片旋转角度
     * @param inviteCode 	邀请码
     * @param memberName 	邀请人
     */
    public static BufferedImage markImageEncodeByIcon(Image iconPath, String srcImgPath, Integer degree, String inviteCode, String memberName) {
    	BufferedImage buffImg = null ;
    	try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            buffImg = new BufferedImage(srcImg.getWidth(null),srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null),srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,null);
            // 3、设置水印旋转
            if (null != degree) {
            	g.rotate(Math.toRadians(degree),(double) buffImg.getWidth() / 2,(double) buffImg.getHeight() / 2);
            }
            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            // 5、得到Image对象。
            Image img = imgIcon.getImage();
            // 6、水印图片的位置
            g.drawImage(img, (srcImg.getWidth(null)/2)-(img.getWidth(null)/2), (srcImg.getHeight(null)/2)-(img.getHeight(null)-80), null);
            
            //设置水印文字参数
            setImageMarkOptions(0, "微软雅黑", fontStyle, 36, "#1082D8", "邀请码:"+inviteCode, 0,0);
            // 1、设置水印文字颜色
            g.setColor(color);
            // 2、设置水印文字Font
            g.setFont(new Font(fontName, fontStyle, fontSize));
            //g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            int waterWidth = fontSize * getTextLength(logoText);
            //计算水印与原图高宽差
            int widthDiff = srcImg.getWidth(null)-waterWidth;
            // 3、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            g.drawString(logoText, widthDiff/2, 850);
            
            //设置水印文字参数
            setImageMarkOptions(0, "微软雅黑", fontStyle, 36, "#5B5B5B", "我是 ("+memberName+") 邀您加入组团助手", 0,0);
            // 1、设置水印文字颜色
            g.setColor(color);
            // 2、设置水印文字Font
            g.setFont(new Font(fontName, fontStyle, fontSize));
            //g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            int waterWidths = fontSize * getTextLength(logoText);
            //计算水印与原图高宽差
            int widthDiffs = srcImg.getWidth(null)-waterWidths;
            // 3、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            g.drawString(logoText, widthDiffs/2, 920);
            // 释放资源
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return buffImg;
    }
    
    
    /**
     * 给图片添加水印图片
     * @param iconPath 		水印图片路径
     * @param srcImgFile 	源图片
     * @param targerPath 	目标图片路径
     * @param fileExt 		文件后缀名(JPG,png)等
     */
    public static void markImageByIcon(String iconPath, File srcImgFile,String targerPath,String fileExt) {
        markImageByIcon(iconPath, srcImgFile, targerPath, fileExt,null);
    }

    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     * @param iconPath		水印图片路径
     * @param srcImgFile 	源图片
     * @param targerPath 	目标图片路径
     * @param fileExt 		文件后缀名(JPG,png)等
     * @param degree 		水印图片旋转角度
     */
    public static void markImageByIcon(String iconPath, File srcImgFile,String targerPath, String fileExt ,Integer degree) {
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(srcImgFile);
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null),srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,null);
            // 3、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree),(double) buffImg.getWidth() / 2,(double) buffImg.getHeight() / 2);
            }
            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            // 5、得到Image对象。
            Image img = imgIcon.getImage();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            // 6、水印图片的位置
            for (int height = interval + imgIcon.getIconHeight(); height/2 < buffImg.getHeight(); height = height +interval+ imgIcon.getIconHeight()) {  
                for (int weight = interval + imgIcon.getIconWidth(); weight/2 < buffImg.getWidth(); weight = weight +interval+ imgIcon.getIconWidth()) {  
                    g.drawImage(img, weight - imgIcon.getIconWidth(), height - imgIcon.getIconHeight(), null);  
                }
            } 
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();
            // 8、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, fileExt, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     * @param file 			源文件对象
     * @param targerPath 	目标文件路径
     * @param degree 		旋转角度
     * @param fileExt 		文件后缀名(JPG,png)等
     */
    public static void markImageByText(File file,String targerPath, String fileExt) {
        InputStream is = null;
        OutputStream os = null;
        try {
            // 1、源图片
            Image srcImg = ImageIO.read(file);
            //计算原始图片宽度长度
            int width = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);
            BufferedImage buffImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null),srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree),(double) buffImg.getWidth() / 2,(double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(new Font(fontName, fontStyle, fontSize));
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            int waterWidth = fontSize * getTextLength(logoText);
            int waterHeight = fontSize;
            
            /*//计算水印与原图高宽差
            int widthDiff = width-waterWidth;
            int heightDiff = height-waterHeight;
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            g.drawString(logoText, widthDiff/2, heightDiff/2);*/
            
            int x = -width/2;
            int y = -height/2;
            while(x < width*1.5){
            	y = -height/2;
            	while(y < height*1.5){
            		g.drawString(logoText, x, y);
            		y+=waterHeight+60;
            	}
            	x+=waterWidth+20;
            }
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, fileExt, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    //计算水印文本长度
    //1、中文长度即文本长度 2、英文长度为文本长度二分之一
    public static int getTextLength(String text){
	    //水印文字长度
	    int length = text.length();
	    for (int i = 0; i < text.length(); i++) {
				String s =String.valueOf(text.charAt(i));
				if (s.getBytes().length>1) {
				length++;
			}
	    }
	    length = length%2==0?length/2:length/2+1;
	    return length;
    }
    
    public static void main(String[] args) {
        /*String srcImgPath = "E:/360Downloads/20180209155010.png";
        String iconPath = "E:/360Downloads/20180209155010.png";

        String targerTextPath2 = "E:/360Downloads/2.png";

        String targerIconPath = "E:/360Downloads/3.png";
        String targerIconPath2 = "E:/360Downloads/4.png";

        setImageMarkOptions(0.3f,null,0,0,null,null,-45,28);
        System.out.println("给图片添加水印文字开始...");
        // 给图片添加水印文字,水印文字旋转-45
        markImageByText(new File(srcImgPath), targerTextPath2,"jpg");
        System.out.println("给图片添加水印文字结束...");

        System.out.println("给图片添加水印图片开始...");
        // 给图片添加水印图片
        //markImageByIcon(iconPath, srcImgPath, targerIconPath);
        // 给图片添加水印图片,水印图片旋转-45
        markImageByIcon(iconPath, srcImgPath, targerIconPath2, -45);
        System.out.println("给图片添加水印图片结束...");*/
    }
}
