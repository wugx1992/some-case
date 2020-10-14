package indi.gxwu.demo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 * @Author: gx.wu
 * @Date: 2020/9/1 11:33
 * @Description: code something to describe this module what it is
 */

public class ImgUtil {
    /**
     * 定义二维码图片的宽度
     */
    private static final int WIDTH = 125;
    /**
     * 定义二维码图片的高度
     */
    private static final int HEIGHT = 125;

    /**
     * 定义LOGO图片的宽度
     */
    private static final int LOGO_WIDTH = 40;
    /**
     * 定义LOGO图片的高度
     */
    private static final int LOGO_HEIGHT = 40;


    /**
     * 生成二维码的方法
     */
    public static BufferedImage execute() throws Exception {
        /** 判断二维码中URL */
        String url =null;
        if (url == null || "".equals(url)) {
            url = "礼好啊奥里给！";
        }

        /** 定义Map集合封装二维码配置信息 */
        Map<EncodeHintType, Object> hints = new HashMap<>();
        /** 设置二维码图片的内容编码 */
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        /** 设置二维码图片的上、下、左、右间隙 */
        hints.put(EncodeHintType.MARGIN, 1);
        /** 设置二维码的纠错级别 */
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        /**
         * 创建二维码字节转换对象
         * 第一个参数：二维码图片中的内容
         * 第二个参数：二维码格式器
         * 第三个参数：生成二维码图片的宽度
         * 第四个参数：生成二维码图片的高度
         * 第五个参数：生成二维码需要配置信息
         *  */
        BitMatrix matrix = new MultiFormatWriter().encode(url,
                BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);

        /** 获取二维码图片真正的宽度  */
        int matrix_width = matrix.getWidth();
        /** 获取二维码图片真正的高度  */
        int matrix_height = matrix.getHeight();
        /** 定义一张空白的缓冲流图片 */
        BufferedImage image = new BufferedImage(matrix_width, matrix_height,
                BufferedImage.TYPE_INT_RGB);
        /** 把二维码字节转换对象 转化 到缓冲流图片上 */
        for (int x = 0; x < matrix_width; x++) {
            for (int y = 0; y < matrix_height; y++) {
                /** 通过x、y坐标获取一点的颜色 true: 黑色  false: 白色 */
                int rgb = matrix.get(x, y) ? 0x000000 : 0xFFFFFF;
                image.setRGB(x, y, rgb);
            }
        }

        /** 获取公司logo图片 */
        BufferedImage logo = ImageIO.read(ImgUtil.class.getClassLoader().getResourceAsStream("static/img/logo.jpg"));
        /** 获取缓冲流图片的画笔 */
        Graphics2D g = (Graphics2D) image.getGraphics();
        /** 在二维码图片中间绘制公司logo */
        g.drawImage(logo, (matrix_width - LOGO_WIDTH) / 2,
                (matrix_height - LOGO_HEIGHT) / 2, LOGO_WIDTH, LOGO_HEIGHT, null);

        /** 设置画笔的颜色 */
        g.setColor(Color.WHITE);
        /** 设置画笔的粗细 */
        g.setStroke(new BasicStroke(5.0f));
        /** 设置消除锯齿 */
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        /** 绘制圆角矩形 */
        g.drawRoundRect((matrix_width - LOGO_WIDTH) / 2, (matrix_height - LOGO_HEIGHT) / 2,
                LOGO_WIDTH, LOGO_HEIGHT, 10, 10);
        return image;
    }


    public static int[] getRGBValue(int pixel, int[] result){
        result[0] = (pixel & 0xff0000) >> 16;
        result[1] = (pixel & 0xff00) >> 8;
        result[2] = (pixel & 0xff);
        return result;
    }

    /**
     * 二维码和图片的合成
     */
    public static final void combineCodeAndPicToFile() {
        long startTime = System.currentTimeMillis();
        System.out.println("开始合成：" + startTime);
        try {
            //背景图
            //BufferedImage big = ImageIO.read(new File(backPicPath));
            BufferedImage bgImage = ImageIO.read(ImgUtil.class.getClassLoader().getResourceAsStream("static/img/background.jpg"));
            System.out.println("背景图大小："+bgImage.getWidth()+" X "+ bgImage.getHeight());

            int miniSize = 80;
            int[] baseRGB = new int[]{255,255,255};
            int[] matchArea = getCoordinateForStartPoint(bgImage, baseRGB, miniSize);
            if(matchArea==null){
                System.out.println("未找到匹配的区域！");
                return;
            }else {
                System.out.println("找到匹配区域，坐标："+matchArea[0]+" X "+matchArea[1]+" 大小："+matchArea[2]);
            }

//            int pixel = bgImage.getRGB(380,750);
//            int[] rgb = new int[3];
//            getRGBValue(pixel, rgb);
//            System.out.println("颜色："+pixel+" rgb：("+rgb[0]+","+rgb[1]+","+rgb[2]+")");


            //二维码的图片
            //url扫二维码显示的内容
//            BufferedImage small = execute();
            BufferedImage small = ImageIO.read(ImgUtil.class.getClassLoader().getResourceAsStream("static/img/index.jpg"));
            //缩小二维码
            BufferedImage finalSmall = Thumbnails.of(small).size(matchArea[2], matchArea[2]).asBufferedImage();
            //合成的图片
            Graphics2D g = bgImage.createGraphics();

            //二维码或小图在大图的左上角坐标
            int x = matchArea[0];
            int y = matchArea[1];
            //将二维码花在背景图上
            g.drawImage(finalSmall, x, y, finalSmall.getWidth(), finalSmall.getHeight(), null);
            //结束绘画
            g.dispose();
            //为了保证大图背景不变色，formatName必须为"png"
            ImageIO.write(bgImage, "png", new FileOutputStream("111.jpg"));
            System.out.println("结束合成耗时：" + (System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取开始坐标和大小
     * @param image
     * @param baseRGB
     * @param mimiSize
     * @return
     */
    public static final int[] getCoordinateForStartPoint(BufferedImage image, int[] baseRGB, int mimiSize){
        int width = image.getWidth();
        int height = image.getHeight();
        if(width<mimiSize || height< mimiSize){
            return null;
        }

        int xIndex = 0, yIndex = 0;
        int pixel, xTemp = -1, yTemp = -1;
        int[] rgbResult = new int[3];
        do {
            boolean findFirstPoint = false;
            for (xIndex = 0; xIndex < width; xIndex++) {
                pixel = image.getRGB(xIndex, yIndex);
                getRGBValue(pixel, rgbResult);
                if (equalRGB(rgbResult, baseRGB)) {
                    //之前未找到第一个坐标
                    if (findFirstPoint == false) {
                        //越界跳过，执行下一行像素处理
                        if ((xIndex + mimiSize) >= width) {
                            break;
                        }
                        if((yIndex+mimiSize)>=height){
                            yIndex+=mimiSize;
                            break;
                        }
                        pixel = image.getRGB(xIndex+mimiSize-1, yIndex);
                        getRGBValue(pixel, rgbResult);
                        //最小尺寸的最右节点颜色不同，x轴跳转步长再继续执行
                        if(!equalRGB(rgbResult, baseRGB)){
                            xIndex+=mimiSize;
                            continue;
                        }
                        findFirstPoint = true;
                        xTemp = xIndex;
                        yTemp = yIndex;
                        continue;
                    } else {
                        //已经找到第一个匹配的像素点
                        //判断原始点y周延伸像素
                        if((yTemp+xIndex-xTemp)<height && equalRGB(getRGBValue(image.getRGB(xTemp, yTemp+(xIndex-xTemp)), rgbResult), baseRGB)){
                            continue;
                        }else {
                            if(xIndex-xTemp>=mimiSize){
                                //长度满足最小尺寸，再判断其他两条边和对角线上的像素是否匹配
                                if(checkOtherBorder(image, xTemp, yTemp, (xIndex-xTemp), baseRGB)){
                                    break;
                                }else{
                                    //其他边框不满足，重新查找第一个匹配像素
                                    xTemp = -1;
                                    yTemp = -1;
                                    findFirstPoint = false;
                                    continue;
                                }
                            }else if((yTemp+xIndex-xTemp)>=height){
                                //高度已经超过背景图最大高度，且匹配像素区域大小不满足
                                yIndex = yTemp+xIndex-xTemp;
                                xTemp = -1;
                                yTemp = -1;
                                findFirstPoint = false;
                                break;
                            }else{
                                //y轴延伸像素不相同，重置并x轴推进一个步长
                                xIndex = xTemp;
                                xTemp = -1;
                                yTemp = -1;
                                findFirstPoint = false;
                                continue;
                            }
                        }
                    }
                } else if (findFirstPoint) {
                    //x轴上一个像素点颜色相同，但相邻像素点颜色不相同
                    //判断当前截取的长度
                    if((xIndex-xTemp)>=mimiSize){
                        if(checkOtherBorder(image, xTemp, yTemp, (xIndex-xTemp), baseRGB)){
                            break;
                        }else{
                            //其他边框不满足，重新查找第一个匹配像素
                            xTemp = -1;
                            yTemp = -1;
                            findFirstPoint = false;
                            continue;
                        }
                    }else{
                        //大小不满足，重新查找第一个匹配像素
                        xTemp = -1;
                        yTemp = -1;
                        findFirstPoint = false;
                        continue;
                    }
                } else {
                    //还没找到一个匹配的像素
                    continue;
                }
            }

            if(xTemp!=-1 && yTemp!=-1 && (xIndex-xTemp)>=mimiSize){
                //找到匹配的区域
                break;
            }
            yIndex++;
        }while (yIndex<height);

        if(xTemp!=-1 && yTemp!=-1 && (xIndex-xTemp)>=mimiSize){
            //找到匹配的区域
            int[] result = new int[]{
                    xTemp, yTemp, xIndex-xTemp
            };

            return result;
        }
        return null;
    }

    /**
     * 判断底部、右边和对角线上的像素点颜色是否一致
     * @param image
     * @param pointX
     * @param pointY
     * @param size
     * @param baseRGB
     * @return
     */
    public static final boolean checkOtherBorder(BufferedImage image, int pointX, int pointY, int size, int[] baseRGB){
        int[] rgbResult = new int[3];
        for(int i = 0;i<size;i++){
            int pixelBottom = image.getRGB(pointX+i, pointY+size-1);
            int pixelRight = image.getRGB(pointX+size-1, pointY+i);
            int pixelDiagonal1 = image.getRGB(pointX+i, pointY+i);
            int pixelDiagonal2 = image.getRGB(pointX+i, pointY+size-1-i);
            if(!equalRGB(getRGBValue(pixelBottom, rgbResult), baseRGB)){
                return false;
            }
            if(!equalRGB(getRGBValue(pixelRight, rgbResult), baseRGB)){
                return false;
            }
            if(!equalRGB(getRGBValue(pixelBottom, rgbResult), baseRGB)){
                return false;
            }
            if(!equalRGB(getRGBValue(pixelDiagonal1, rgbResult), baseRGB)){
                return false;
            }
            if(!equalRGB(getRGBValue(pixelDiagonal2, rgbResult), baseRGB)){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断RGB颜色是否相等
     * @param rgb1
     * @param rgb2
     * @return
     */
    public static final boolean equalRGB(int[] rgb1, int[] rgb2){
        if(rgb1.length!=rgb2.length){
            return false;
        }
        if(rgb1[0]==rgb2[0] && rgb1[1]==rgb2[1] && rgb1[2]==rgb2[2]){
            return true;
        }
        return false;
    }

    /**
     * 合成base64响应前台图片数据展示
     * @return
     */
    public static final String combineCodeAndPicToBase64() {
        ImageOutputStream imOut = null;
        try {
            BufferedImage big = ImageIO.read(ImgUtil.class.getClassLoader().getResourceAsStream("static/img/123.jpg"));
            // BufferedImage small = ImageIO.read(new File(fillPicPath));
            BufferedImage small = ImgUtil.execute();
            Graphics2D g = big.createGraphics();

            //左下角位置
           /* int x = big.getWidth() - small.getWidth()-45; //加是向右，减是向左
            int y = (big.getHeight() - small.getHeight()-50); //加是向下，减是向上*/

            //右下角位置
           /* int x = 45; //加是向右，减是向左
            int y = (big.getHeight() - small.getHeight()-50); //加是向下，减是向上*/
            //居中位置
            //加是向右，减是向左
            int x = (big.getWidth() - small.getWidth())/2;
            //加是向下，减是向上
            int y = (big.getHeight() - small.getHeight()-50);

            g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
            g.dispose();
            //为了保证大图背景不变色，formatName必须为"png"
            //字节数组流
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            //将图片流转换为字节数组流
            imOut = ImageIO.createImageOutputStream(bs);
            //将合成好的背景图输入到字节数组流中
            ImageIO.write(big, "png", imOut);
            //将字节数组流转换为二进制流
            InputStream in = new ByteArrayInputStream(bs.toByteArray());

            byte[] bytes = new byte[in.available()];
            //读取数组流中的数据
            in.read(bytes);
            //转换为base64数据类型
            String base64 = Base64.getEncoder().encodeToString(bytes);
            System.out.println(base64);

            return "data:image/jpeg;base64," + base64;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        combineCodeAndPicToFile();
    }
}
