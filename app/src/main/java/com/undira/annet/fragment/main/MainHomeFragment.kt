package com.undira.annet.fragment.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.undira.annet.R
import com.undira.annet.databinding.FragmentMainHomeBinding
import kotlinx.coroutines.launch

class MainHomeFragment : Fragment() {
    private lateinit var binding: FragmentMainHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainHomeBinding.inflate(layoutInflater)
        configureSearchBar()
        return binding.root
    }

    private fun configureSearchBar(){
        try {
            val profileUrl: String = resources.getString(R.string.sample_profile_ink)
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

                        return true
                    }
                }).submit()
        }catch (_: Exception){
            binding.searchBar.menu.findItem(R.id.profile).icon = ResourcesCompat.getDrawable(resources, R.drawable.icon, null)
        }
    }
}