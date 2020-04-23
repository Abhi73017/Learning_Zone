package com.abhishek.learningzone

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abhishek.learningzone.main_fragments.Home
import com.abhishek.learningzone.main_fragments.My_account
import com.abhishek.learningzone.main_fragments.info
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sdsmdg.harjot.vectormaster.VectorMasterView
import com.sdsmdg.harjot.vectormaster.models.PathModel
import kotlinx.android.synthetic.main.activity_dash_board.*

class DashBoard : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    internal lateinit var outline: PathModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        var fragment: Fragment = Home()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()

        bottom_nav.inflateMenu(R.menu.main_menu)
        bottom_nav.selectedItemId = R.id.home
        bottom_nav.setOnNavigationItemSelectedListener(this@DashBoard)

    }


    private fun onFragmentLoad(fragment1: Fragment): Boolean {
        if (fragment1 != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment1)
                    .commit()
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        var fragment: Fragment = Home()

        when (p0.itemId) {
            R.id.account -> {
                draw(6)
                //lin_id.x = bottom_nav.mFirstCurveControlPoint1.x.toFloat()
                lin_id1.visibility = View.VISIBLE
                lin_id.visibility = View.GONE
                lin_id2.visibility = View.GONE
                fragment = My_account()
                drawAnimation(fab2)
            }
            R.id.home -> {
                draw(2)
                //lin_id.x = bottom_nav.mFirstCurveControlPoint1.x.toFloat()
                lin_id1.visibility = View.GONE
                lin_id.visibility = View.VISIBLE
                lin_id2.visibility = View.GONE
                fragment = Home()
                drawAnimation(fab1)
            }
            R.id.info -> {
                draw()
                //lin_id.x = bottom_nav.mFirstCurveControlPoint1.x.toFloat()
                lin_id.visibility = View.GONE
                lin_id1.visibility = View.GONE
                lin_id2.visibility = View.VISIBLE
                fragment = info()
                drawAnimation(fab3)
            }
        }
        return onFragmentLoad(fragment)
    }

    private fun drawAnimation(fab: VectorMasterView?) {
        outline = fab!!.getPathModelByName("icon")
        outline.strokeColor = Color.WHITE
        outline.trimPathEnd = 0f

        val valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        valueAnimator.duration = 1000
        valueAnimator.addUpdateListener { valueAnimator ->
            outline.trimPathEnd = valueAnimator.animatedValue as Float
            fab.update()
        }
        valueAnimator.start()
    }

    private fun draw(i: Int) {
        bottom_nav.mFirstCurveStartPoint.set(
                bottom_nav.mNavigationBarWidth / i - bottom_nav.CURVED_RADIUS * 2 - bottom_nav.CURVED_RADIUS / 3,
                0
        )

        bottom_nav.mFirstCurveEndPoint.set(
                bottom_nav.mNavigationBarWidth / i,
                bottom_nav.CURVED_RADIUS + bottom_nav.CURVED_RADIUS / 4
        )
        bottom_nav.mSecondCurveStartPoint = bottom_nav.mFirstCurveEndPoint
        bottom_nav.mSecondCurveEndPoint.set(
                bottom_nav.mNavigationBarWidth / i + bottom_nav.CURVED_RADIUS * 2 + bottom_nav.CURVED_RADIUS / 3,
                0
        )
        bottom_nav.mFirstCurveControlPoint1.set(
                bottom_nav.mFirstCurveStartPoint.x + bottom_nav.CURVED_RADIUS + bottom_nav.CURVED_RADIUS / 4,
                bottom_nav.mFirstCurveStartPoint.y
        )
        bottom_nav.mFirstCurveControlPoint2.set(
                bottom_nav.mFirstCurveEndPoint.x - bottom_nav.CURVED_RADIUS * 2 + bottom_nav.CURVED_RADIUS,
                bottom_nav.mFirstCurveEndPoint.y
        )
        bottom_nav.mSecondCurveControlPoint1.set(
                bottom_nav.mSecondCurveStartPoint.x + bottom_nav.CURVED_RADIUS * 2 - bottom_nav.CURVED_RADIUS,
                bottom_nav.mSecondCurveStartPoint.y
        )
        bottom_nav.mSecondCurveControlPoint2.set(
                bottom_nav.mSecondCurveEndPoint.x - bottom_nav.CURVED_RADIUS - bottom_nav.CURVED_RADIUS / 4,
                bottom_nav.mSecondCurveEndPoint.y
        )


    }

    private fun draw() {
        bottom_nav.mFirstCurveStartPoint.set(
                bottom_nav.mNavigationBarWidth * 10 / 12 - bottom_nav.CURVED_RADIUS * 2 - bottom_nav.CURVED_RADIUS / 3,
                0
        )

        bottom_nav.mFirstCurveEndPoint.set(
                bottom_nav.mNavigationBarWidth * 10 / 12,
                bottom_nav.CURVED_RADIUS + bottom_nav.CURVED_RADIUS / 4
        )
        bottom_nav.mSecondCurveStartPoint = bottom_nav.mFirstCurveEndPoint
        bottom_nav.mSecondCurveEndPoint.set(
                bottom_nav.mNavigationBarWidth * 10 / 12 + bottom_nav.CURVED_RADIUS * 2 + bottom_nav.CURVED_RADIUS / 3,
                0
        )
        bottom_nav.mFirstCurveControlPoint1.set(
                bottom_nav.mFirstCurveStartPoint.x + bottom_nav.CURVED_RADIUS + bottom_nav.CURVED_RADIUS / 4,
                bottom_nav.mFirstCurveStartPoint.y
        )
        bottom_nav.mFirstCurveControlPoint2.set(
                bottom_nav.mFirstCurveEndPoint.x - bottom_nav.CURVED_RADIUS * 2 + bottom_nav.CURVED_RADIUS,
                bottom_nav.mFirstCurveEndPoint.y
        )
        bottom_nav.mSecondCurveControlPoint1.set(
                bottom_nav.mSecondCurveStartPoint.x + bottom_nav.CURVED_RADIUS * 2 - bottom_nav.CURVED_RADIUS,
                bottom_nav.mSecondCurveStartPoint.y
        )
        bottom_nav.mSecondCurveControlPoint2.set(
                bottom_nav.mSecondCurveEndPoint.x - bottom_nav.CURVED_RADIUS - bottom_nav.CURVED_RADIUS / 4,
                bottom_nav.mSecondCurveEndPoint.y
        )


    }

    /*private var backPressed: Long = 0

    override fun onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else Toast.makeText(
                baseContext,
                "Press once again to exit!",
                Toast.LENGTH_SHORT
        ).show()
        backPressed = System.currentTimeMillis()
    }*/
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press once again to exit!", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}