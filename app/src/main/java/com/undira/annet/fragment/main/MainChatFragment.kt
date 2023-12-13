package com.undira.annet.fragment.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.undira.annet.R
import com.undira.annet.activity.SearchActivity
import com.undira.annet.adapter.main.chat.RecyclerViewAdapter
import com.undira.annet.databinding.FragmentMainChatBinding
import com.undira.annet.view_model.main.chat.ViewModel

class MainChatFragment : Fragment() {
    private lateinit var binding: FragmentMainChatBinding
    private lateinit var viewModel: ViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainChatBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this@MainChatFragment)[ViewModel::class.java]
        initializeRecyclerView()
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_fragment_search_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search -> startActivity(Intent(requireContext(), SearchActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeRecyclerView(){
        binding.recyclerView.adapter = RecyclerViewAdapter(requireContext(), viewModel.data)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}