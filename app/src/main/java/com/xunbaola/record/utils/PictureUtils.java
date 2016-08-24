package com.xunbaola.record.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

import java.io.IOException;

public class PictureUtils {
    private static final String TAG ="PictureUtils" ;

    public static BitmapDrawable getScaledDrawable(Activity a, String path){
        Display display=a.getWindowManager().getDefaultDisplay();
        float destWidth=display.getWidth();
        float destHeight=display.getHeight();
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(path,options);
        float srcWidth=options.outWidth;
        float srcHeight=options.outHeight;
        int inSampleSize=1;
        if (srcHeight>destHeight||srcWidth>destWidth){
            if (srcWidth>srcHeight) {
                inSampleSize=Math.round(srcHeight/destHeight);
            }else {
                inSampleSize=Math.round(srcWidth/destWidth);
            }

        }
        options=new BitmapFactory.Options();
        options.inSampleSize=inSampleSize;
        Bitmap bitmap= BitmapFactory.decodeFile(path,options);
        return new BitmapDrawable(a.getResources(),bitmap);
    }
    public static void cleanImageView(ImageView imageView){
       BitmapDrawable b= imageView.getDrawable() instanceof BitmapDrawable ? ((BitmapDrawable) imageView.getDrawable()) : null;
        if (b != null) {
            b.getBitmap().recycle();
            imageView.setImageDrawable(null);
        }
    }
    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return degree;
        }
        Log.d(TAG,"degree= "+degree);
        return degree;
    }
    /**
     * 旋转图片，使图片保持正确的方向。
     * @param bitmap 原始图片
     * @param degrees 原始图片的角度
     * @return Bitmap 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return bmp;
    }
}
