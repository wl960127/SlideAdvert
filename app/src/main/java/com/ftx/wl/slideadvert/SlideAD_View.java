package com.ftx.wl.slideadvert;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by FTX on 2017/12/23.
 */

public class SlideAD_View extends AppCompatImageView {

    public SlideAD_View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private RectF mBitmapRectF;
    private Bitmap mBitmap;

    private int mMinDy;



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mMinDy = h;

        LogUtils.e("onSizeChanged   h --->> "+h +"  mMinDy --->>"+ mMinDy);

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        mBitmap = drawableToBitamp(drawable);
        mBitmapRectF = new RectF(0, 0,
                w,
                mBitmap.getHeight() * w / mBitmap.getWidth());

    }


    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    private int mDy;

    public void setDy(int dy) {

        if (getDrawable() == null) {
            return;
        }
        mDy = dy - mMinDy;

        LogUtils.e("setDy  dy " +dy + "    mDy --->> "+mDy +"     mMinDy --->>"+ mMinDy);

        if (mDy <= 0) {
            LogUtils.e("setDy    if (mDy <= 0)   mDy --->>   <=0 ");
            mDy = 0;
        }
        if (mDy > mBitmapRectF.height() - mMinDy) {

            LogUtils.e("setDy   if (mDy > mBitmapRectF.height() - mMinDy)    mDy --->> "+(int) (mBitmapRectF.height() - mMinDy)
                    + " mBitmapRectF.height() -->"+ mBitmapRectF.height() +"   mMinDy---->> "+mMinDy);

            mDy = (int) (mBitmapRectF.height() - mMinDy);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            return;
        }
        canvas.save();
        canvas.translate(0, -mDy);
        canvas.drawBitmap(mBitmap, null, mBitmapRectF, null);
        canvas.restore();
    }


}
