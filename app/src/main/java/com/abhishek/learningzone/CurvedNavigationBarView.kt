package com.abhishek.learningzone

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.TRANSPARENT
import android.graphics.Color.rgb
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView


class CurvedNavigationBarView : BottomNavigationView {

    private var mPath: Path? = null
    private var mPaint: Paint? = null
    val CURVED_RADIUS = 60

    var mFirstCurveStartPoint = Point()
    var mFirstCurveEndPoint = Point()
    var mFirstCurveControlPoint1 = Point()
    var mFirstCurveControlPoint2 = Point()

    var mSecondCurveStartPoint = Point()
    var mSecondCurveEndPoint = Point()
    var mSecondCurveControlPoint1 = Point()
    var mSecondCurveControlPoint2 = Point()

    var mNavigationBarWidth: Int = 0
    var mNavigationBarHeight: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        mPath = Path()
        mPaint = Paint()
        mPaint!!.style = Paint.Style.FILL_AND_STROKE
        mPaint!!.color = rgb(66, 171, 241)
        setBackgroundColor(TRANSPARENT)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mNavigationBarHeight = height
        mNavigationBarWidth = width

        mFirstCurveStartPoint.set(
            mNavigationBarWidth / 2
                    - CURVED_RADIUS * 2
                    - CURVED_RADIUS / 3,
            0
        )

        mFirstCurveEndPoint.set(mNavigationBarWidth / 2, CURVED_RADIUS + CURVED_RADIUS / 4)
        mSecondCurveStartPoint = mFirstCurveEndPoint

        mSecondCurveEndPoint.set(mNavigationBarWidth / 2 + CURVED_RADIUS * 2 + CURVED_RADIUS / 3, 0)

        mFirstCurveControlPoint1.set(
            mFirstCurveStartPoint.x + CURVED_RADIUS + CURVED_RADIUS / 4,
            mFirstCurveStartPoint.y
        )
        mFirstCurveControlPoint2.set(
            mFirstCurveEndPoint.x - CURVED_RADIUS * 2 + CURVED_RADIUS,
            mFirstCurveEndPoint.y
        )

        mSecondCurveControlPoint1.set(
            mSecondCurveStartPoint.x + CURVED_RADIUS * 2 - CURVED_RADIUS,
            mSecondCurveStartPoint.y
        )
        mSecondCurveControlPoint2.set(
            mSecondCurveEndPoint.x - CURVED_RADIUS - CURVED_RADIUS / 4,
            mSecondCurveEndPoint.y
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPath!!.reset()
        mPath!!.moveTo(0f, 0f)
        mPath!!.lineTo(mFirstCurveStartPoint.x.toFloat(), mFirstCurveStartPoint.y.toFloat())

        mPath!!.cubicTo(
            mFirstCurveControlPoint1.x.toFloat(),
            mFirstCurveControlPoint1.y.toFloat(),
            mFirstCurveControlPoint2.x.toFloat(),
            mFirstCurveControlPoint2.y.toFloat(),
            mFirstCurveEndPoint.x.toFloat(),
            mFirstCurveEndPoint.y.toFloat()
        )

        mPath!!.cubicTo(
            mSecondCurveControlPoint1.x.toFloat(),
            mSecondCurveControlPoint1.y.toFloat(),
            mSecondCurveControlPoint2.x.toFloat(),
            mSecondCurveControlPoint2.y.toFloat(),
            mSecondCurveEndPoint.x.toFloat(),
            mSecondCurveEndPoint.y.toFloat()
        )

        mPath!!.lineTo(mNavigationBarWidth.toFloat(), 0f)
        mPath!!.lineTo(mNavigationBarWidth.toFloat(), mNavigationBarHeight.toFloat())
        mPath!!.lineTo(0f, mNavigationBarHeight.toFloat())
        mPath!!.close()

        canvas.drawPath(mPath!!, mPaint!!)
    }
}
