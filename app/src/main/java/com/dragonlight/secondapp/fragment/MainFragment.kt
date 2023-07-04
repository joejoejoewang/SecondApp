package com.dragonlight.secondapp.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dragonlight.secondapp.R
import com.dragonlight.secondapp.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main), View.OnClickListener{
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        val v = binding.root

        //主頁共有五個進入項目
        binding.imageUser.setOnClickListener(this)
        binding.imageCalender.setOnClickListener(this)
        binding.tvNumber1.setOnClickListener(this)
        binding.tvNumber3.setOnClickListener(this)
        binding.tvMainSetting.setOnClickListener(this)

        return v
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imageUser ->{
                findNavController()
                    .navigate(R.id.action_mainFragment_to_userListFragment)}
            R.id.imageCalender -> {}
            R.id.tv_number1 ->{}
            R.id.tv_number3 ->{}
            R.id.tv_main_setting ->{
                findNavController().navigate(R.id.action_mainFragment_to_setImportantLevelFragment)
            }
        }
    }
}