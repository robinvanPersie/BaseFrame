package com.antimage.basemodule.utils.android.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.support.v8.renderscript.ScriptIntrinsicConvolve3x3;
import android.support.v8.renderscript.ScriptIntrinsicConvolve5x5;


public class BlurUtil {
    public static final int SCALED_DEFAULT = 0;
    public static final int SCALED_WITH_FILTER = 1;
    public static int scaledType;

    /**
     * 对图片执行模糊操作
     * @param context 上下文
     * @param src 源文件
     * @return
     */
    public static Bitmap doBlur(Context context, Bitmap src, int position){
        Bitmap result = null;
        int radius = 11;
        float scaled = 1f;
        switch (position){
            case 0:
                result = FastBlur.fastBlur(getScaledBitmap(src,scaled),radius);
                break;
            case 1:
                result = doRenderScriptBlur(context,src,scaled,radius);
                break;
            case 2:
                result = BoxBlur.blur(radius,getScaledBitmap(src,scaled));
                break;
            case 3:
                result = StackBlur.blur(radius,getScaledBitmap(src,scaled));
                break;
            case 4:
                result = GaussianFastBlur.blur(radius,getScaledBitmap(src,scaled));
                break;
            case 5:
                result = doRenderScriptBox3x3Blur(context,getScaledBitmap(src,scaled),radius);
                break;
            case 6:
                result = doRenderScriptBox5x5Blur(context,getScaledBitmap(src,scaled),radius);
                break;
            case 7:
                result = doRenderScriptGaussian5x5Blur(context,getScaledBitmap(src,scaled),radius);
                break;
        }
        return result;
    }



    private static Bitmap doRenderScriptBlur(Context context, Bitmap bitmap, float scale, int radius){
        Bitmap result =  getScaledBitmap(bitmap,scale);
        RenderScript renderScript = RenderScript.create(context);
        Allocation allocation = Allocation.createFromBitmap(renderScript,result);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,allocation.getElement());
        blur.setInput(allocation);
        blur.setRadius(radius);
        blur.forEach(allocation);
        allocation.copyTo(result);
        return result;
    }


    private static Bitmap doRenderScriptBox3x3Blur(Context context, Bitmap bitmap, int radius){
        RenderScript rs = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicConvolve3x3 script = ScriptIntrinsicConvolve3x3.create(rs, Element.U8_4(rs));
        script.setCoefficients(BlurCore.BOX_3x3);
        for (int i = 0; i < radius; i++) {
            script.setInput(input);
            script.forEach(output);
            input = output;
        }
        output.copyTo(bitmap);
        return bitmap;
    }



    private static Bitmap doRenderScriptBox5x5Blur(Context context, Bitmap bitmap, int radius){
        RenderScript rs = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicConvolve5x5 script = ScriptIntrinsicConvolve5x5.create(rs, Element.U8_4(rs));
        script.setCoefficients(BlurCore.BOX_5x5);
        for (int i = 0; i < radius; i++) {
            script.setInput(input);
            script.forEach(output);
            input = output;
        }
        output.copyTo(bitmap);
        return bitmap;
    }


    private static Bitmap doRenderScriptGaussian5x5Blur(Context context, Bitmap bitmap, int radius){
        RenderScript rs = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicConvolve5x5 script = ScriptIntrinsicConvolve5x5.create(rs, Element.U8_4(rs));
        script.setCoefficients(BlurCore.GAUSSIAN_5x5);
        for (int i = 0; i < radius; i++) {
            script.setInput(input);
            script.forEach(output);
            input = output;
        }
        output.copyTo(bitmap);
        return bitmap;
    }



    private static Bitmap getScaledBitmap(Bitmap src, float scaled){
        switch (scaledType){
            case SCALED_WITH_FILTER:
                return getScaledBitmapWithFilter(src,scaled);
            default:
                return getScaledBitmapDefault(src,scaled);
        }
    }

    private static Bitmap getScaledBitmapDefault(Bitmap src, float scaled){
        Bitmap result = Bitmap.createBitmap((int)(src.getWidth()*scaled),(int)(src.getHeight()*scaled), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.scale(scaled,scaled);
        canvas.drawBitmap(src,0,0,null);
        return result;
    }


    private static Bitmap getScaledBitmapWithFilter(Bitmap src, float scaled){
        return Bitmap.createScaledBitmap(src,(int)(src.getWidth()*scaled),(int)(src.getHeight()*scaled),true);
    }
}
