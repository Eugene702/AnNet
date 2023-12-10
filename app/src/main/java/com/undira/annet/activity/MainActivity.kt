package com.undira.annet.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.undira.annet.R
import com.undira.annet.adapter.main.ViewPagerAdapter
import com.undira.annet.databinding.ActivityMainBinding
import com.undira.annet.fragment.main.MainChatFragment
import com.undira.annet.fragment.main.MainHomeFragment
import com.undira.annet.fragment.main.MainNotificationFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureBottomNavigation()
        initializeViewPager()
    }

    private fun configureBottomNavigation(){
        val badgeMessage = binding.bottomNavigation.getOrCreateBadge(R.id.message)
        val badgeNotification = binding.bottomNavigation.getOrCreateBadge(R.id.notification)

        badgeMessage.number = 3
        badgeNotification.number = 3

        binding.bottomNavigation.setOnItemSelectedListener {
            val selectedIndex: Int = when(it.itemId){
                R.id.message -> 1
                R.id.notification -> 2
                else -> 0
            }

            binding.viewpager.setCurrentItem(selectedIndex, true)
            return@setOnItemSelectedListener true
        }
    }

    private fun initializeViewPager(){
        val fragmentList: ArrayList<Fragment> = arrayListOf(
            MainHomeFragment(),
            MainChatFragment(),
            MainNotificationFragment()
        )

        binding.viewpager.adapter = ViewPagerAdapter(fragmentList, supportFragmentManager, lifecycle)
        binding.viewpager.registerOnPageChangeCallback(object: OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val selectedIndex: Int = when(position){
                    1 -> R.id.message
                    2 -> R.id.notification
                    else -> R.id.home
                }

                binding.bottomNavigation.selectedItemId = selectedIndex
            }
        })
    }
}