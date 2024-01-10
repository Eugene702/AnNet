package com.undira.annet.fragment.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
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
import com.undira.annet.model.PostGetAll
import com.undira.annet.model.PostInsert
import com.undira.annet.store.UserStore
import com.undira.annet.view_model.main.home.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainHomeFragment : Fragment() {
    private lateinit var binding: FragmentMainHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var userStore: UserStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        userStore = UserStore(requireContext())

        lifecycleScope.launch {
            val userUuid: String? = userStore.getUuid.firstOrNull()

            if(userUuid != null){
                binding.loading.isVisible = true
                configureSearchBar()
                initializeRecyclerView()
                binding.loading.isVisible = false
                setupStatusBox()
            }
        }
        return binding.root
    }

    override fun onResume() {
        lifecycleScope.launch {
            initializeRecyclerView()
        }
        super.onResume()
    }

    private suspend fun setupStatusBox(){
        val profileUrl = "https://ui-avatars.com/api/?name=${userStore.getName.first()}"
        Glide.with(requireContext())
            .load(profileUrl)
            .into(binding.statusBoxAvatar)

        binding.statusBoxPostBtn.setOnClickListener {
            binding.statusBoxInputLayout.isErrorEnabled = false

            if(binding.statuxBoxInput.text.toString().trim().isNotEmpty()){
                lifecycleScope.launch {
                    binding.statusBoxPostBtn.isEnabled = false
                    binding.statusBoxPostBtn.text = resources.getString(R.string.loading)

                    if(userStore.getUuid.firstOrNull() != null){
                        val uuid = userStore.getUuid
                        viewModel.addPost(PostInsert(id_users = uuid.first().toString(), content = binding.statuxBoxInput.text.toString()))
                        initializeRecyclerView()
                    }

                    binding.statusBoxPostBtn.isEnabled = true
                    binding.statusBoxPostBtn.text = resources.getString(R.string.post)
                    binding.statuxBoxInput.text?.clear()
                }
            }else{
                binding.statusBoxInputLayout.error = "Input cannot be empty!"
            }
        }
    }

    private suspend fun initializeRecyclerView(){
        val data: List<PostGetAll> = viewModel.getData()
        binding.recyclerView.adapter = RecyclerViewAdapter(requireContext(), data)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private suspend fun configureSearchBar(){
        binding.searchBar.setOnClickListener { startActivity(Intent(requireContext(), SearchActivity::class.java)) }
        binding.searchBar.inflateMenu(R.menu.main_home_fragment_menu)

        try {
            val profileUrl = "https://ui-avatars.com/api/?name=${userStore.getName.first()}"
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
                                    lifecycleScope.launch {
                                        val intent = Intent(requireContext(), ProfileActivity::class.java)
                                        val userID: String? = userStore.getUuid.first()

                                        if(userID != null){
                                            intent.putExtra(ProfileActivity.USER_ID, userID)
                                            startActivity(intent)
                                        }
                                    }
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