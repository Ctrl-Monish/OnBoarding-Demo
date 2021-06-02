package com.projects.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.projects.onboarding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var onBoardingAdapter: OnBoardingAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager : ViewPager? = null
    var position = 0
    var firstTime:SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(restorePref()){
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        val list:MutableList<OnboardingData> = ArrayList()
        list.add(OnboardingData("Explore careers that have high job satisfaction.",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut lab.",
        R.drawable.onboarding1))
        list.add(OnboardingData("Get Selected for Your Dream Job.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut lab.",
            R.drawable.onboarding2))
        list.add(OnboardingData("We help you to connect with the Organization.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut lab.",
            R.drawable.onboarding3))

        setOnBoardingVp(list)

        position = onBoardingViewPager!!.currentItem
        binding.nextbtn.setOnClickListener{
            if(position<list.size) {
                position++
                onBoardingViewPager!!.currentItem = position
            }
            if(position==list.size) {
                savePref()
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)            }
        }
        binding.skipbtn.setOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }

        tabLayout = findViewById(R.id.tabindicator)
        tabLayout!!.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == list.size-1){
                    binding.skipbtn.text = ""
                }else{
                    binding.skipbtn.text = "Skip"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
    private fun setOnBoardingVp(onboardingData: List<OnboardingData>){
        onBoardingViewPager = findViewById(R.id.onBoardingvp2)
        onBoardingAdapter = OnBoardingAdapter(this,onboardingData)
        onBoardingViewPager!!.adapter = onBoardingAdapter

        binding.tabindicator.setupWithViewPager(onBoardingViewPager)
    }

    private fun savePref(){
        firstTime = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = firstTime!!.edit()
        editor.putBoolean("isFirstTime", true)
        editor.apply()
    }
    private fun restorePref(): Boolean{
        firstTime = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return firstTime!!.getBoolean("isFirstTime", false)
    }
}