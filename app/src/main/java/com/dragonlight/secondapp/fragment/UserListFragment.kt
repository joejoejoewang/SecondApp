package com.dragonlight.secondapp.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dragonlight.secondapp.R
import com.dragonlight.secondapp.adapter.UserListAdapter
import com.dragonlight.secondapp.databinding.FragmentUserListBinding
import com.dragonlight.secondapp.viewmodel.UserViewModel

class UserListFragment : Fragment(R.layout.fragment_user_list), View.OnClickListener{
    private val viewModel by viewModels<UserViewModel>()
    private var userListAdapter = UserListAdapter()
    private lateinit var binding: FragmentUserListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserListBinding.bind(view)

        recycleViewModel(binding)
        searchAction()
        binding.floatingListUser.setOnClickListener(this)
    }

    private fun recycleViewModel(binding: FragmentUserListBinding) {
        binding.apply {
            rvListUser.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = userListAdapter
                setHasFixedSize(true)
            }
        }

        viewModel.readAllUser.observe(viewLifecycleOwner, Observer {
            userListAdapter.submitList(it)
        })

        userListAdapter.onItemClick = {
            val amount = it.userId
            val action =
                UserListFragmentDirections.actionUserListFragmentToUserDetailInfoFragment(amount)
            findNavController().navigate(action)
        }
    }

    private fun searchAction() {
       binding.searchListUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchDatabase(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchDatabase(newText)
                }
                return true
            }
        })
    }
    private fun searchDatabase(query: String) {
        val searchQuery = "%${query}"
        viewModel.searchContactName(searchQuery).observe(viewLifecycleOwner, Observer {
            userListAdapter.submitList(it)
        })
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.floatingListUser ->{
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = ContactsContract.Contacts.CONTENT_URI
                }
                startActivity(intent)
            }
        }
    }
}