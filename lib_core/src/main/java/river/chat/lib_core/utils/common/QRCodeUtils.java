package river.chat.lib_core.utils.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import android.graphics.Bitmap;

import java.util.Hashtable;

/**
 * Created by xiangyi.wu on 2018/8/24.
 *
 * @descripyion:
 */

public class QRCodeUtils {
    private static int IMAGE_HALFWIDTH = 100;//宽度值，影响中间图片大小

    /**
     * 生成二维码，默认大小为500*500
     *
     * @param url 需要生成二维码的文字、网址等
     *
     * @return bitmap
     */
    public static Bitmap createQRCode(String url) {
        return createQRCode(url, 500);
    }


    /**
     * 生成二维码
     *
     * @param text 需要生成二维码的文字、网址等
     * @param size 需要生成二维码的大小（）
     *
     * @return bitmap
     */
    public static Bitmap createQRCode(String text, int size) {
        if (text != null) {
            try {
                Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                hints.put(EncodeHintType.MARGIN, 1);
                /*
                 * 设置容错级别，默认为ErrorCorrectionLevel.L
                 * 因为中间加入logo所以建议你把容错级别调至H,否则可能会出现识别不了
                 */
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

                BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                                                                BarcodeFormat.QR_CODE,
                                                                size,
                                                                size,
                                                                hints);
                int[] pixels = new int[size * size];
                for (int y = 0; y < size; y++) {
                    for (int x = 0; x < size; x++) {
                        if (bitMatrix.get(x, y)) {
                            pixels[y * size + x] = 0xff000000;
                        } else {
                            pixels[y * size + x] = 0xffffffff;
                        }

                    }
                }
                Bitmap bitmap = Bitmap.createBitmap(size, size,
                                                    Bitmap.Config.ARGB_8888);
                bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }



}