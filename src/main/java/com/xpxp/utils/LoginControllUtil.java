package com.xpxp.utils;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;

@Service
public class LoginControllUtil {

    /** 验证码图片的宽度 */
    private static int width = 54;
    /** 验证码图片的高度 */
    private static int height = 20;
    /** 验证码字符个数 */
    private static int codeCount = 4;
    // 字符间距
    private static int x = width / (codeCount + 1);
    // 字体高度
    private static int fontHeight = height - 2;
    private static int codeY = height - 4;

    private static char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    private static Random random = new Random();

    public  String getRandomCode() {
        // 随机生成验证码
        StringBuilder randomCode = new StringBuilder();
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            randomCode.append(strRand);
        }
        return randomCode.toString();
    }

    public Integer generateValidateCodeId() {
        // 生成唯一 ID
        return random.nextInt();
    }

    public String getValidateCodeImage(String validateCode) {
        // 创建画布
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        // 添加背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 设置字体
        Font font = new Font("Fixedsys", Font.PLAIN | Font.BOLD, fontHeight);
        g.setFont(font);

        // 绘制验证码
        g.setColor(new Color(147, 14, 20));  // 设置验证码字符颜色
        for (int i = 0; i < codeCount; i++) {
            g.drawString(String.valueOf(validateCode.charAt(i)), (i + 1) * x - 6, codeY);
        }

        // 生成图像并转换为 Base64 格式
        String imgStr = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(buffImg, "jpeg", out); // 输出图像
            out.close();

            // 将图片转换为 Base64 格式
            imgStr = "data:image/png;base64," + Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace(); // 处理异常
        }

        return imgStr; // 返回图像字符串
    }


}