package com.undira.annet.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.undira.annet.R
import com.undira.annet.adapter.main.ViewPagerAdapter
import com.undira.annet.databinding.ActivityMainBinding
import com.undira.annet.fragment.main.MainChatFragment
import com.undira.annet.fragment.main.MainHomeFragment
import com.undira.annet.fragment.main.MainNotificationFragment
import com.undira.annet.view_model.main.ViewModel
import io.github.jan.supabase.gotrue.user.UserInfo

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel:ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this@MainActivity)[ViewModel::class.java]
        setContentView(binding.root)

        checkUserLogin()
        configureBottomNavigation()
        initializeViewPager()
    }

    private fun checkUserLogin(){
        val credential: UserInfo? = viewModel.getCredential()
        if(credential == null){
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
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