package com.projects.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

class OnBoardingAdapter(private var context: Context, private var onboardingDataList: List<OnboardingData>) : PagerAdapter(){
    override fun getCount(): Int {
        return onboardingDataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.onboarding_screen,null)

        val image: ImageView = view.findViewById(R.id.imageView)
        val title: TextView = view.findViewById(R.id.title_tv)
        val description: TextView = view.findViewById(R.id.desc_tv)

        image.setImageResource(onboardingDataList[position].imageurl)
        title.text = onboardingDataList[position].title
        description.text = onboardingDataList[position].description

        container.addView(view)
        return view
    }
}