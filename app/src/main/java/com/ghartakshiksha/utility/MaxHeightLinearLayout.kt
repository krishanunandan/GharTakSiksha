package com.ghartakshiksha.utility


import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout


class MaxHeightLinearLayout : LinearLayout {
    private var maxHeightDp = 0

    constructor(context: Context?) : super(context) {}
    /*constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a: TypedArray = context.theme
            .obtainStyledAttributes(attrs, R.styleable.MaxHeightLinearLayout, 0, 0)
        maxHeightDp = try {
            a.getInteger(R.styleable.MaxHeightLinearLayout_maxHeightDp, 0)
        } finally {
            a.recycle()
        }
    }*/

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        val maxHeightPx: Int = maxHeightDp.px//ViewUtils.dpToPx(maxHeightDp)
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeightPx, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun setMaxHeightDp(maxHeightDp: Int) {
        this.maxHeightDp = maxHeightDp
        invalidate()
    }
}