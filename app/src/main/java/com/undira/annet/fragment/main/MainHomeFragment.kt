package com.undira.annet.fragment.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.undira.annet.R
import com.undira.annet.activity.ProfileActivity
import com.undira.annet.activity.SearchActivity
import com.undira.annet.adapter.main.home.RecyclerViewAdapter
import com.undira.annet.databinding.FragmentMainHomeBinding
import com.undira.annet.view_model.main.home.HomeViewModel
import kotlinx.coroutines.launch

class MainHomeFragment : Fragment() {
    private lateinit var binding: FragmentMainHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        configureSearchBar()
        initializeRecyclerView()
        return binding.root
    }

    private fun initializeRecyclerView(){
        binding.recyclerView.adapter = RecyclerViewAdapter(requireContext(), viewModel.data)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun configureSearchBar(){
        binding.searchBar.setOnClickListener { startActivity(Intent(requireContext(), SearchActivity::class.java)) }
        binding.searchBar.inflateMenu(R.menu.main_home_fragment_menu)

        try {
            val profileUrl: String = "https://ui-avatars.com/api/?name="
            Glide.with(requireContext())
                .load(profileUrl)
                .centerCrop()
                .circleCrop()
                .sizeMultiplier(.05f)
                .addListener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        lifecycleScope.launch {
                            binding.searchBar.menu.findItem(R.id.profile).icon = resource
                        }

                        binding.searchBar.setOnMenuItemClickListener {
                            when(it.itemId){
                                R.id.profile -> {
                                    startActivity(Intent(requireContext(), ProfileActivity::class.java))
                                    true
                                }
                                else -> false
                            }
                        }

                        return true
                    }
                }).submit()
        }catch (_: Exception){
            binding.searchBar.menu.findItem(R.id.profile).icon = ResourcesCompat.getDrawable(resources, R.drawable.icon, null)
        }
    }
}