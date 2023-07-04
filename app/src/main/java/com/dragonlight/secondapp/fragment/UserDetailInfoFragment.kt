package com.dragonlight.secondapp.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toolbar
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dragonlight.secondapp.R
import com.dragonlight.secondapp.adapter.UserDetailAdapter
import com.dragonlight.secondapp.databinding.FragmentUserDetailInfoBinding
import com.dragonlight.secondapp.db.entity.User
import com.dragonlight.secondapp.viewmodel.UserViewModel

class UserDetailInfoFragment : Fragment(R.layout.fragment_user_detail_info), View.OnClickListener {
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var binding: FragmentUserDetailInfoBinding
    private var userDetailAdapter = UserDetailAdapter()
    private val args by navArgs<UserDetailInfoFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserDetailInfoBinding.bind(view)

        recycleViewModel()
        initUiModel()
        // add Task
        binding.floatingDetail.setOnClickListener(this)
        binding.btUserDetailSave.setOnClickListener(this)
    }

    private fun initUiModel() {
        val amount = args.userDetailArgu

        //initData
        binding.apply {
            viewModel.searchUserById(amount).observe(viewLifecycleOwner, Observer {
                tvDetailName.text = it.userName
                tvDetailNumber.text = it.userNumber
                spDetail.setSelection(it.levelId)
                tvUserDetailSpinnerLevel.text = it.levelId.toString()
                when (it.buyOrSell) {
                    true -> {
                        swDetailBS.isChecked = true
                        swDetailBS.text = "買"
                    }

                    false -> {
                        swDetailBS.isChecked = false
                        swDetailBS.text = "賣"
                    }
                }
            })
        }

        //spinner
        //load all data
        viewModel.readAllImportantLevel.observe(viewLifecycleOwner, Observer { level ->
            val levelList = mutableListOf<String>()
            level.forEach {
                levelList.add(it.levelGroup)
            }
            val spAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item, levelList
            )

            binding.spDetail.adapter = spAdapter
        })

        //spinner select action
        binding.spDetail.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.tvUserDetailSpinnerLevel.text = id.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.searchUserById(amount).observe(viewLifecycleOwner, Observer { user ->
                    if (user.levelId == 0) {
                        binding.spDetail.setSelection(0)
                    } else {
                        binding.spDetail.setSelection(user.levelId)
                    }
                })
            }
        }

        //TextView & Switch   load data from database
        viewModel.searchUserById(amount).observe(viewLifecycleOwner, Observer { user ->
            binding.tvDetailName.text = user.userName
            binding.tvDetailNumber.text = user.userNumber
            when(binding.swDetailBS.isChecked){
                true ->{
                    binding.swDetailBS.text ="買"
                    binding.swDetailBS.setBackgroundColor(resources.getColor(R.color.blue))
                }
                false ->{
                    binding.swDetailBS.text ="賣"
                    binding.swDetailBS.setBackgroundColor(resources.getColor(R.color.pink))
                }
            }
            binding.tvUserDetailInfoContactId.text = user.contactId.toString()
        })

        //switch action
        binding.swDetailBS.setOnClickListener(this)


    }


    private fun recycleViewModel() {
        val amount = args.userDetailArgu
        binding.rvDetailUser.apply {
            adapter = userDetailAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        viewModel.loadAllTaskWithUser(amount)
            .observe(viewLifecycleOwner, Observer {
                userDetailAdapter.submitList(it)
            })

        userDetailAdapter.onItemClick = {
            val amount = it.taskId
            val action = UserDetailInfoFragmentDirections
                .actionUserDetailInfoFragmentToTaskDetailFragment(amount)
            findNavController().navigate(action)
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.floating_detail -> {
                val amount = args.userDetailArgu
                val action = UserDetailInfoFragmentDirections
                    .actionUserDetailInfoFragmentToTaskDetailFragment(amount)
                findNavController().navigate(action)
            }

            R.id.sw_detailBS -> {
                val sw = binding.swDetailBS
                when (sw.isChecked) {
                    true -> {
                        sw.text = "買"
                        sw.setBackgroundColor(resources.getColor(R.color.blue))
                    }

                    false -> {
                        sw.text = "賣"
                        sw.setBackgroundColor(resources.getColor(R.color.pink))
                    }
                }
            }
            R.id.bt_user_detail_save ->{
                val amount = args.userDetailArgu
                    val user = User(
                        amount,
                        binding.tvUserDetailInfoContactId.text.toString().toInt(),
                        binding.tvDetailName.text.toString(),
                        binding.tvDetailNumber.text.toString(),
                        binding.swDetailBS.isChecked,
                        binding.tvUserDetailSpinnerLevel.text.toString().toInt()
                    )
                    viewModel.updateUser(user)
            }
        }
    }
}