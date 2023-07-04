package com.dragonlight.secondapp.fragment

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.navigateUp
import com.dragonlight.secondapp.R
import com.dragonlight.secondapp.databinding.FragmentTaskDetailBinding
import com.dragonlight.secondapp.db.entity.Task
import com.dragonlight.secondapp.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Date


class TaskDetailFragment : Fragment(R.layout.fragment_task_detail), View.OnClickListener {

    private val viewModel by viewModels<UserViewModel>()
    private val argsAddTasks by navArgs<TaskDetailFragmentArgs>()
    private lateinit var binding: FragmentTaskDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTaskDetailBinding.bind(view)

        initDataPackView()
        initSpinner()
        inputDate()
        binding.btTaskDetailSave.setOnClickListener(this)
    }

    private fun inputDate() {
        binding.tvTaskDetailDateStart.setOnClickListener(this)
    }

    private fun initSpinner() {
        viewModel.readAllImportantLevel.observe(viewLifecycleOwner, Observer { level ->
            val levelList = mutableListOf<String>()
            level.forEach {
                levelList.add(it.levelGroup)
            }
            val spAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item, levelList
            )

            binding.spTaskDetail.adapter = spAdapter
        })
        binding.spTaskDetail.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.tvTaskDetailSpinnerLevel.text = p3.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                val amount = argsAddTasks.taskDetailArgu
                viewModel.searchLevelById(amount).observe(viewLifecycleOwner, Observer {
                    binding.spTaskDetail.setSelection(it.levelId)
                })
            }

        }
    }

    private fun initMessageBox() {
        AlertDialog.Builder(requireContext())
            .setMessage("是否要設定新重要")
            .setTitle("設定重要性")
            .setPositiveButton("OK") { _, _ ->
                findNavController().navigate(R.id.action_taskDetailFragment_to_setImportantLevelFragment)
            }
            .show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun initDataPackView() {
        val date = SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis()))
        binding.datePickView.setDate("1900-01-01", "2300-01-01", date)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_task_detail_dateStart -> {
                binding.tvTaskDetailDateStart.text = binding.datePickView.dateString
            }

            R.id.bt_task_detail_save -> {
                val amount = argsAddTasks.taskDetailArgu
                val task = Task(
                    0,
                    amount,
                    binding.tvTaskDetailDateStart.text.toString(),
                    binding.etTaskDetailTitle.text.toString(),
                    binding.spTaskDetail.selectedItemPosition,
                    binding.etTaskDetailInput.text.toString()
                )

                viewModel.insertTask(task)
                Toast.makeText(context, "you got it", Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
            }
        }
    }

}