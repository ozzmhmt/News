package co.appstorm.newsx.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import co.appstorm.newsx.R;

import static android.widget.LinearLayout.HORIZONTAL;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class CirclePageIndicator extends View implements PageIndicator {
    private int currentDisplayed;
    public int viewCount = -1;

    private int mOrientation;
    private float strokeWidth;

    private float mRadius;
    private Paint mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintFillHighlight = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean drawRrect = false;

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (isInEditMode()) return;

        currentDisplayed = 0;

        // Defaults resource
        final int defOrientation = LinearLayout.HORIZONTAL;
        final int defFillColor = ContextCompat.getColor(context, R.color.trans_black_light);
        final int defHighlightColor = ContextCompat.getColor(context, R.color.colorWhite);
        final int defStrokeColor = ContextCompat.getColor(context, R.color.default_circle_indicator_stroke_color);
        final float defStrokeWidth = getResources().getDimension(R.dimen.default_circle_indicator_stroke_width);
        final float defRadius = getResources().getDimension(R.dimen.default_circle_indicator_radius);

        // Retrieve styles attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
        mOrientation = a.getInteger(R.styleable.CircleIndicator_android_orientation, defOrientation);
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(a.getColor(R.styleable.CircleIndicator_fillColor, defFillColor));
        mPaintFillHighlight.setStyle(Paint.Style.FILL);
        mPaintFillHighlight.setColor(a.getColor(R.styleable.CircleIndicator_highlightColor, defHighlightColor));
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setColor(a.getColor(R.styleable.CircleIndicator_strokeColor, defStrokeColor));
        strokeWidth = a.getDimension(R.styleable.CircleIndicator_strokeWidth, defStrokeWidth);
        mPaintStroke.setStrokeWidth(strokeWidth);
        mRadius = a.getDimension(R.styleable.CircleIndicator_radius, defRadius);
        drawRrect = a.getBoolean(R.styleable.CircleIndicator_drawRect, false);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (viewCount == 0) return;

        // Distance between start point of circles
        final float threeRadius = mRadius * 3;

        float dX;
        float dY;

        // Draw circles
        for (int i = 0; i < viewCount; i++) {
            float drawLong = mRadius + (i * threeRadius) + strokeWidth;
            if (mOrientation == HORIZONTAL) {
                dX = drawLong;
                dY = mRadius + strokeWidth;
            } else {
                dX = mRadius + strokeWidth;
                dY = drawLong;
            }
            if (currentDisplayed == i) {
                if (drawRrect) {
                    canvas.drawRect(dX - mRadius, dY - mRadius, dX + mRadius, dY + mRadius, mPaintFillHighlight);
                } else {
                    canvas.drawCircle(dX, dY, mRadius, mPaintFillHighlight);
                }
            } else {
                if (mPaintFill.getAlpha() > 0) {
                    if (drawRrect) {
                        canvas.drawRect(dX - mRadius, dY - mRadius, dX + mRadius, dY + mRadius, mPaintFill);
                    } else {
                        canvas.drawCircle(dX, dY, mRadius, mPaintFill);
                    }

                }
            }
            if (strokeWidth > 0) {
                if (drawRrect) {
                    canvas.drawRect(dX - mRadius, dY - mRadius, dX + mRadius, dY + mRadius, mPaintFill);
                } else {
                    canvas.drawCircle(dX, dY, mRadius, mPaintStroke);
                }
            }
        }
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
        if (viewCount == 0)
            return;
        int distance = (int) ((viewCount * 2 * (mRadius + strokeWidth)) + (viewCount - 1) * mRadius) + getPaddingTop() + getPaddingBottom();
        try {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
            if (mOrientation == HORIZONTAL) {
                params.width = distance;
                params.height = (int) (mRadius + strokeWidth) * 2;
            } else {
                params.height = distance;
                params.width = (int) (mRadius + strokeWidth) * 2;
            }
            setLayoutParams(params);
        } catch (ClassCastException exception) {
            throw new ClassCastException("CircleIndicator must be placed in RelativeLayout");
        }
    }

    public void setCurrentDisplayed(int num) {
        currentDisplayed = num;
        invalidate();
    }

    public int getCurrentDisplayed() {
        return currentDisplayed;
    }

    @Override
    public void updateIndicator(int currentView, int totalView) {
        this.currentDisplayed = currentView;
        invalidate();
    }
}