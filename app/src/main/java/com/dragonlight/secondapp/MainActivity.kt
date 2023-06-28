package com.dragonlight.secondapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dragonlight.secondapp.databinding.ActivityMainBinding
import com.dragonlight.secondapp.db.entity.User
import com.dragonlight.secondapp.fragment.MainFragmentDirections
import com.dragonlight.secondapp.viewmodel.UserViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<UserViewModel>()
    private val userList = emptyList<User>()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        userPermission()
        initUi()
    }

    private fun userPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS), 1
            )
        } else {
            readContact()
        }
    }

    @SuppressLint("Range")
    private fun readContact() {
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )?.apply {
            while (moveToNext()) {
                val displayId = getInt(
                    getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone._ID
                    )
                )
                val displayName = getString(
                    getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    )
                )
                val displayNumber = getString(
                    getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                )

                viewModel.readAllUser.observe(this@MainActivity, Observer {
                    if (it.isEmpty()) {
                        viewModel.insertUser(
                            User(
                                0, displayId, displayName,
                                displayNumber, true, 0
                            )
                        )
                    } else {
                        userList.forEach {
                            if (it.contactId == displayId) {
                                if (it.userName != displayName) {
                                    viewModel.updateUser(
                                        User(
                                            it.userId, it.contactId, it.userName,
                                            it.userNumber, true, 0
                                        )
                                    )
                                }
                                if (it.userNumber != displayNumber) {
                                    viewModel.updateUser(
                                        User(
                                            it.userId, it.contactId, it.userName,
                                            displayNumber, true, 0
                                        )
                                    )
                                }
                            }
                        }
                    }
                })
            }
            close()
        }
    }

    private fun initUi() {
        setSupportActionBar(binding.appBarMain.toolbar)

        drawerLayout = binding.drawLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.fragmentContainerView)

//        setSupportActionBar(binding.appBarMain.toolbar)
        setupActionBarWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.setting, R.id.userGroup
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.userGroup ->{
                val action = MainFragmentDirections.actionMainFragmentToSetImportantLevelFragment()
                findNavController(R.id.fragmentContainerView).navigate(action)
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}