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
import com.undira.annet.adapter.main.notification.RecyclerViewAdapter
import com.undira.annet.databinding.FragmentMainNotificationBinding
import com.undira.annet.view_model.main.notification.ViewModel

class MainNotificationFragment : Fragment() {
    private lateinit var binding: FragmentMainNotificationBinding
    private lateinit var viewModel: ViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainNotificationBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this@MainNotificationFragment)[ViewModel::class.java]
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        initializeRecyclerView()
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