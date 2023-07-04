package com.dragonlight.secondapp.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dragonlight.secondapp.R
import com.dragonlight.secondapp.adapter.SetLevelAdapter
import com.dragonlight.secondapp.databinding.FragmentSetImportantLevelBinding
import com.dragonlight.secondapp.db.entity.ImportantLevel
import com.dragonlight.secondapp.viewmodel.UserViewModel

class SetImportantLevelFragment : Fragment(R.layout.fragment_set_important_level),
    View.OnClickListener{

    private val viewModel by viewModels<UserViewModel>()
    private val setLevelAdapter = SetLevelAdapter()
    private lateinit var binding: FragmentSetImportantLevelBinding
    private var checkColor = 0

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetImportantLevelBinding.bind(view)

        recycleViewData(binding)
        binding.imageLevelColorChange.setOnClickListener(this)
        binding.btLevelAddSave.setOnClickListener(this)
    }

    private fun recycleViewData(binding: FragmentSetImportantLevelBinding) {
        binding.apply {
            rvLevelAdd.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = setLevelAdapter
                setHasFixedSize(true)
            }
            //recycleView 左滑刪除

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.ACTION_STATE_IDLE
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val level = setLevelAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.deleteLevel(level)
                }

            }).attachToRecyclerView(rvLevelAdd)

        }

        viewModel.readAllImportantLevel.observe(viewLifecycleOwner, Observer {
            setLevelAdapter.submitList(it)
        })

        setLevelAdapter.onItemClick = {
            viewModel.searchLevelById(it.levelId).observe(viewLifecycleOwner, Observer {
                binding.tvLevelId.text = it.levelId.toString()
                binding.etLevelAddGroupName.text =
                    Editable.Factory.getInstance().newEditable(it.levelGroup)
                binding.etLevelAddInfo.text =
                    Editable.Factory.getInstance().newEditable(it.levelInfo)
                binding.etLevelAddRecycleDat.text =
                    Editable.Factory.getInstance().newEditable(it.levelDate.toString())
                binding.imageLevelColorChange.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), it.levelColor)
                )
                binding.tvLevelColor.text = it.levelColor.toString()
            })
            binding.btLevelAddSave.text = "Update"
        }


    }

    private fun updateImportantLevel() {
        var levelColor = 0

        if (checkColor != 0) {
            checkColorValue()
            levelColor = checkColor
        } else {
            levelColor = binding.tvLevelColor.text.toString().toInt()
        }
        val level = ImportantLevel(
            binding.tvLevelId.text.toString().toInt(),
            binding.etLevelAddGroupName.text.toString(),
            binding.etLevelAddInfo.text.toString(),
            binding.etLevelAddRecycleDat.text.toString().toInt(),
            levelColor
        )
        viewModel.updateLevel(level)

        findNavController().navigateUp()
    }

    private fun writeImportantLevel() {
        var levelColor = 0

        levelColor = checkColorValue()

        val level = ImportantLevel(
            0,
            binding.etLevelAddGroupName.text.toString(),
            binding.etLevelAddInfo.text.toString(),
            binding.etLevelAddRecycleDat.text.toString().toInt(),
            levelColor
        )
        viewModel.insertLevel(level)
        findNavController().navigateUp()
    }

    private fun checkColorValue(): Int {
        var levelColor = 0
        when (checkColor) {
            R.drawable.shape_y -> {
                levelColor = checkColor
            }

            R.drawable.shape_g -> {
                levelColor = checkColor
            }

            R.drawable.shape_o -> {
                levelColor = checkColor
            }

            R.drawable.shape_b -> {
                levelColor = checkColor
            }

            R.drawable.shape_p -> {
                levelColor = checkColor
            }

            R.drawable.shape_r -> {
                levelColor = checkColor
            }
        }
        return levelColor
    }

    private fun initPopWindow(p0: View) {
        val viewGroup = p0 as? ViewGroup
        val view = LayoutInflater.from(context).inflate(R.layout.pop_win_color, viewGroup)
        val popWindow = PopupWindow(view)
        popWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popWindow.setBackgroundDrawable(ColorDrawable(Color.GRAY))
        popWindow.showAsDropDown(p0, 50, 50)

        view.findViewById<ImageButton>(R.id.image_color_red).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.image_color_orange).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.image_color_ping).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.image_color_yellow).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.image_color_green).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.image_color_blue).setOnClickListener(this)

        popWindow.isOutsideTouchable = true
        popWindow.isTouchable = true
        popWindow.isFocusable = true

        //作爲一按外側window 就會消失的判斷
        view.setOnClickListener {            //此view 爲popwindow的view
            if (popWindow.isShowing) {
                popWindow.isFocusable = false
                popWindow.isTouchable = false
                popWindow.dismiss()
            }
        }

        popWindow.update()      //一定要有這行，不然會發生奇怪的情況，不會馬上消失
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onClick(p0: View?) {
        val image = binding.imageLevelColorChange
        when (p0?.id) {
            R.id.bt_level_add_save -> {
                when (binding.btLevelAddSave.text) {
                    "Save" -> {
                        writeImportantLevel()
                    }

                    "Update" -> {
                        updateImportantLevel()
                    }
                }
            }

            R.id.image_level_color_change -> {
                initPopWindow(p0)
            }

            androidx.appcompat.R.id.home ->{
                findNavController().navigate(R.id.mainFragment)
            }


//            下列爲select color 針對pop_win_color的選擇
            R.id.image_color_ping -> {
                image.setImageDrawable(resources.getDrawable(R.drawable.shape_p))
                checkColor = R.drawable.shape_p
            }

            R.id.image_color_blue -> {
                image.setImageDrawable(resources.getDrawable(R.drawable.shape_b))
                checkColor = R.drawable.shape_b
            }

            R.id.image_color_orange -> {
                image.setImageDrawable(resources.getDrawable(R.drawable.shape_o))
                checkColor = R.drawable.shape_o
            }

            R.id.image_color_green -> {
                image.setImageDrawable(resources.getDrawable(R.drawable.shape_g))
                checkColor = R.drawable.shape_g
            }

            R.id.image_color_red -> {
                image.setImageDrawable(resources.getDrawable(R.drawable.shape_r))
                checkColor = R.drawable.shape_r
            }

            R.id.image_color_yellow -> {
                image.setImageDrawable(resources.getDrawable(R.drawable.shape_y))
                checkColor = R.drawable.shape_y
            }

        }
    }
}