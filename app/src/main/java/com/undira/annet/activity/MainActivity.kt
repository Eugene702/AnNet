package com.undira.annet.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.undira.annet.R
import com.undira.annet.adapter.main.ViewPagerAdapter
import com.undira.annet.databinding.ActivityMainBinding
import com.undira.annet.fragment.main.MainChatFragment
import com.undira.annet.fragment.main.MainHomeFragment
import com.undira.annet.store.UserStore
import com.undira.annet.view_model.main.ViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel
    private lateinit var store: UserStore

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this@MainActivity)[ViewModel::class.java]
        store = UserStore(this@MainActivity)
        setContentView(binding.root)

        checkUserLogin()
        configureBottomNavigation()
        initializeViewPager()
    }

    private fun checkUserLogin(){
        lifecycleScope.launch {
            val credential: String? = store.readUserCredential.firstOrNull()
            if(credential == null){
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun configureBottomNavigation(){
        val badgeMessage = binding.bottomNavigation.getOrCreateBadge(R.id.message)

        badgeMessage.number = 3

        binding.bottomNavigation.setOnItemSelectedListener {
            val selectedIndex: Int = when(it.itemId){
                R.id.message -> 1
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
        )

        binding.viewpager.adapter = ViewPagerAdapter(fragmentList, supportFragmentManager, lifecycle)
        binding.viewpager.registerOnPageChangeCallback(object: OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val selectedIndex: Int = when(position){
                    1 -> R.id.message
                    else -> R.id.home
                }

                binding.bottomNavigation.selectedItemId = selectedIndex
            }
        })
    }
}