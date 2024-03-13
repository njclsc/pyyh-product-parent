package com.pyyh.product.manager.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCodeUtil {

	private static final int CODE_WIDTH = 400;
	private static final int CODE_HEIGHT = 400;
	private static final int FRONT_COLOR = 0x000000;
	private static final int BACKGROUND_COLOR = 0xFFFFFF;
	
	public static BufferedImage createCode(String content) throws Exception{
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.MARGIN, 1);
		MultiFormatWriter mfw = new MultiFormatWriter();
		BitMatrix bm = mfw.encode(content, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_WIDTH, hints);
		BufferedImage image = new BufferedImage(bm.getWidth(), bm.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < bm.getWidth(); x++) {
            for (int y = 0; y < bm.getHeight(); y++) {
                image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        String path = System.getProperty("user.dir").replace("\\", "/") + "/staticsource/logo.png";
		System.out.println(path);
        Graphics2D graphics = image.createGraphics();
        BufferedImage logo = ImageIO.read(new File(path));
      //设置二维码大小，太大了会覆盖二维码，此处为20%
        int logoWidth = logo.getWidth() > bm.getWidth()*2 /10 ? (bm.getWidth()*2 /10) : logo.getWidth();
        int logoHeight = logo.getHeight() > bm.getHeight()*2 /10 ? (bm.getHeight()*2 /10) : logo.getHeight();
        //设置logo图片放置的位置，中心
        int x = (bm.getWidth() - logoWidth) / 2;
        int y = (bm.getHeight() - logoHeight) / 2;
        //开始合并并绘制图片
        graphics.drawImage(logo,x,y,logoWidth,logoHeight,null);
        graphics.drawRoundRect(x,y,logoWidth,logoHeight,15,15);
      //logob边框大小
        graphics.setStroke(new BasicStroke(2));
        graphics.setColor(Color.WHITE);
        graphics.drawRect(x,y,logoWidth,logoHeight);
        graphics.dispose();
        logo.flush();
        
        return image;
	}
}
