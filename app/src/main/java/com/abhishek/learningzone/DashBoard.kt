package com.abhishek.learningzone

import android.animation.ValueAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sdsmdg.harjot.vectormaster.VectorMasterView
import com.sdsmdg.harjot.vectormaster.models.PathModel
import kotlinx.android.synthetic.main.activity_dash_board.*

class DashBoard : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    internal lateinit var outline : PathModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        bottom_nav.inflateMenu(R.menu.main_menu)
        bottom_nav.selectedItemId = R.id.home
        bottom_nav.setOnNavigationItemSelectedListener(this@DashBoard)

    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.account->{
                draw(6)
                lin_id.x = bottom_nav.mFirstCurveControlPoint1.x.toFloat()
                fab.visibility = View.VISIBLE
                fab1.visibility = View.GONE
                fab2.visibility = View.GONE

                drawAnimation(fab)
                Toast.makeText(this, "Clicked on My Account", Toast.LENGTH_SHORT).show()
            }
            R.id.home->{
                draw(2)
                lin_id.x = bottom_nav.mFirstCurveControlPoint1.x.toFloat()
                fab.visibility = View.GONE
                fab1.visibility = View.VISIBLE
                fab2.visibility = View.GONE

                drawAnimation(fab1)
                Toast.makeText(this, "Clicked on Home", Toast.LENGTH_SHORT).show()
            }
            R.id.info->{
                draw()
                lin_id.x = bottom_nav.mFirstCurveControlPoint1.x.toFloat()
                fab.visibility = View.GONE
                fab1.visibility = View.GONE
                fab2.visibility = View.VISIBLE

                drawAnimation(fab2)
                Toast.makeText(this, "Clicked on Info", Toast.LENGTH_SHORT).show()
            }
    }
        return true
    }

    private fun drawAnimation(fab: VectorMasterView?) {
        outline = fab!!.getPathModelByName("icon")
        outline.strokeColor = Color.WHITE
        outline.trimPathEnd = 0f

        val valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        valueAnimator.duration = 1000
        valueAnimator.addUpdateListener { valueAnimator->
            outline.trimPathEnd = valueAnimator.animatedValue as Float
            fab.update()
        }
        valueAnimator.start()
    }

    private fun draw(i: Int) {
        bottom_nav.mFirstCurveStartPoint.set(bottom_nav.mNavigationBarWidth/i - bottom_nav.CURVED_RADIUS*2 - bottom_nav.CURVED_RADIUS/3,
        0)

        bottom_nav.mFirstCurveEndPoint.set(bottom_nav.mNavigationBarWidth/i, bottom_nav.CURVED_RADIUS + bottom_nav.CURVED_RADIUS/4)
        bottom_nav.mSecondCurveStartPoint = bottom_nav.mFirstCurveEndPoint
        bottom_nav.mSecondCurveEndPoint.set(bottom_nav.mNavigationBarWidth/i + bottom_nav.CURVED_RADIUS*2 + bottom_nav.CURVED_RADIUS/3,
        0)
        bottom_nav.mFirstCurveControlPoint1.set(bottom_nav.mFirstCurveStartPoint.x + bottom_nav.CURVED_RADIUS + bottom_nav.CURVED_RADIUS/4,
        bottom_nav.mFirstCurveStartPoint.y)
        bottom_nav.mFirstCurveControlPoint2.set(bottom_nav.mFirstCurveEndPoint.x - bottom_nav.CURVED_RADIUS*2 + bottom_nav.CURVED_RADIUS,
        bottom_nav.mFirstCurveEndPoint.y)
        bottom_nav.mSecondCurveControlPoint1.set(bottom_nav.mSecondCurveStartPoint.x + bottom_nav.CURVED_RADIUS*2 - bottom_nav.CURVED_RADIUS,
        bottom_nav.mSecondCurveStartPoint.y)
        bottom_nav.mSecondCurveControlPoint2.set(bottom_nav.mSecondCurveEndPoint.x - bottom_nav.CURVED_RADIUS - bottom_nav.CURVED_RADIUS/4,
        bottom_nav.mSecondCurveEndPoint.y)


    }

    private fun draw() {
        bottom_nav.mFirstCurveStartPoint.set(bottom_nav.mNavigationBarWidth*10/12 - bottom_nav.CURVED_RADIUS*2 - bottom_nav.CURVED_RADIUS/3,
            0)

        bottom_nav.mFirstCurveEndPoint.set(bottom_nav.mNavigationBarWidth*10/12, bottom_nav.CURVED_RADIUS + bottom_nav.CURVED_RADIUS/4)
        bottom_nav.mSecondCurveStartPoint = bottom_nav.mFirstCurveEndPoint
        bottom_nav.mSecondCurveEndPoint.set(bottom_nav.mNavigationBarWidth*10/12 + bottom_nav.CURVED_RADIUS*2 + bottom_nav.CURVED_RADIUS/3,
            0)
        bottom_nav.mFirstCurveControlPoint1.set(bottom_nav.mFirstCurveStartPoint.x + bottom_nav.CURVED_RADIUS + bottom_nav.CURVED_RADIUS/4,
            bottom_nav.mFirstCurveStartPoint.y)
        bottom_nav.mFirstCurveControlPoint2.set(bottom_nav.mFirstCurveEndPoint.x - bottom_nav.CURVED_RADIUS*2 + bottom_nav.CURVED_RADIUS,
            bottom_nav.mFirstCurveEndPoint.y)
        bottom_nav.mSecondCurveControlPoint1.set(bottom_nav.mSecondCurveStartPoint.x + bottom_nav.CURVED_RADIUS*2 - bottom_nav.CURVED_RADIUS,
            bottom_nav.mSecondCurveStartPoint.y)
        bottom_nav.mSecondCurveControlPoint2.set(bottom_nav.mSecondCurveEndPoint.x - bottom_nav.CURVED_RADIUS - bottom_nav.CURVED_RADIUS/4,
            bottom_nav.mSecondCurveEndPoint.y)


    }
}
