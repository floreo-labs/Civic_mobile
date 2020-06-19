package com.civic.widget

import android.animation.ArgbEvaluator
import android.animation.FloatEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.civic.R
import kotlin.math.roundToInt

class ViewPagerDots @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val activeDotSize: Float
    private var animatedActiveDotSize: Float
    private val inactiveDotSize: Float
    private var animatedInactiveDotSize: Float
    private val dotSpacing: Float

    private val activeDotColor: Int
    private var animatedActiveDotColor: Int
    private val inactiveDotColor: Int
    private var animatedInactiveDotColor: Int

    private val dotsPaint: Paint

    private var numDots: Int = 0
    private var activeDotPosition: Int = 0
    private var nextDotPosition: Int = 0
    private var dotPositions: FloatArray ?= null

    private val dotSizeEvaluator = FloatEvaluator()
    private val dotColorEvaluator = ArgbEvaluator()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerDots)

        activeDotSize = typedArray.getDimension(R.styleable.ViewPagerDots_activeDotSize, 0f)
        activeDotColor = ContextCompat.getColor(context, R.color.primaryVariant)
        animatedActiveDotSize = activeDotSize
        animatedActiveDotColor = activeDotColor

        inactiveDotSize = typedArray.getDimension(R.styleable.ViewPagerDots_inactiveDotSize, 0f)
        inactiveDotColor = ContextCompat.getColor(context, R.color.primaryVariant)
        animatedInactiveDotSize = inactiveDotSize
        animatedInactiveDotColor = inactiveDotColor

        if (activeDotSize == 0f || inactiveDotSize == 0f) {
            throw IllegalArgumentException("You must specify a size for both active and inactive dot size.")
        }
        typedArray.recycle()

        dotSpacing = context.resources.getDimension(R.dimen.spacing_xlarge)
        dotsPaint = Paint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = MeasureSpec.getSize(widthMeasureSpec)
        calculateDotPositions(width)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isInEditMode) return

        val middleY = height / 2f
        dotPositions?.forEachIndexed { index, xPosition ->
            dotsPaint.color = when (index) {
                activeDotPosition -> animatedActiveDotColor
                nextDotPosition -> animatedInactiveDotColor
                else -> inactiveDotColor
            }
            val dotSize = when (index) {
                activeDotPosition -> animatedActiveDotSize
                nextDotPosition -> animatedInactiveDotSize
                else -> inactiveDotSize
            }
            canvas.drawCircle(
                xPosition,
                middleY,
                dotSize,
                dotsPaint
            )
        }
    }

    fun attachTo(viewPager: ViewPager2) {
        val adapter = viewPager.adapter ?: throw IllegalStateException("Your ViewPager2 must have an adapter attached.")

        getItemCount(adapter)

        calculateAnimatedValues(viewPager)
    }

    private fun getItemCount(adapter: RecyclerView.Adapter<*>) {
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                numDots = adapter.itemCount
            }
        })
    }

    private fun calculateAnimatedValues(viewPager: ViewPager2) {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                activeDotPosition = position
                val currentItem = viewPager.currentItem
                nextDotPosition = when {
                    activeDotPosition == numDots - 1 -> position.dec()
                    activeDotPosition == 0 -> position.inc()
                    currentItem > position -> currentItem
                    else -> position.inc()
                }

                animatedActiveDotSize = dotSizeEvaluator.evaluate(positionOffset, activeDotSize, inactiveDotSize)
                animatedActiveDotColor = dotColorEvaluator.evaluate(positionOffset, activeDotColor, inactiveDotColor) as Int

                animatedInactiveDotSize = dotSizeEvaluator.evaluate(positionOffset, inactiveDotSize, activeDotSize)
                animatedInactiveDotColor = dotColorEvaluator.evaluate(positionOffset, inactiveDotColor, activeDotColor) as Int

                invalidate()
            }
        })
    }

    private fun calculateDotPositions(width: Int) {
        if (width == 0) return

        val middleX = width / 2f
        val middle = numDots.toFloat().div(2).roundToInt()
        dotPositions = FloatArray(numDots) { index ->
            val diff = index.plus(1) - middle
            middleX + diff.times(dotSpacing)
        }
    }
}