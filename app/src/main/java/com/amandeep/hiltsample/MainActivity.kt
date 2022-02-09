package com.amandeep.hiltsample

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.amandeep.hiltsample.databinding.ActivityMainBinding
import com.amandeep.hiltsample.hilt.Car
import com.amandeep.hiltsample.hilt.Person
import com.amandeep.hiltsample.hilt.interfaceproblemwithinject.Bike
import com.amandeep.hiltsample.hilt.interfaceproblemwithinject.SomeInterfaceImpl
import com.amandeep.hiltsample.hilt.interfaceproblemwithinject.Vehicle
import com.amandeep.hiltsample.hilt.sample.People
import com.amandeep.hiltsample.hilt.sample.scoped.ActivityScopedSample
import com.amandeep.hiltsample.hilt.sample.scoped.FragmentScopedSample
import com.amandeep.hiltsample.hilt.sample.scoped.ScopedSample
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG="mainActivityTag"
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var car: Car
    @Inject lateinit var person: Person // called from @Module
    @Inject lateinit var people: People // field injection
    @Inject lateinit var scopedSample: ScopedSample
    @Inject lateinit var activityScoped: ActivityScopedSample
    @Inject lateinit var vehicle: Vehicle
    @Inject lateinit var someInterfaceImpl: SomeInterfaceImpl

    // this can be using in Fargment only
  //  @Inject lateinit var fargmentScopedSample: FragmentScopedSample


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        Log.i("TAG", "onCreate: ${person.getPersonName()}")

        Log.e(TAG, "onCreate:people info ${people.getPeopleInformation()}")
        Log.e(TAG, "onCreate:people Address ${people.getPeopleAddress()}")

        Log.e(TAG, "onCreate: Application scope ${scopedSample.getScopedFunction()}")
        Log.e(TAG, "onCreate: Activity scope ${activityScoped.getScopedFunction()}")

        Log.e(TAG, "onCreate: getVehicle = ${vehicle.getVehicleDetails()}")
        Log.e(TAG, "onCreate: some Object dependency = ${someInterfaceImpl.getDetails()}")

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action ${car.getCar()}", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

       // car.getCar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }



//    fun getBothClass(context: Context){
//        val infetface=
//            EntryPoints.get(context, NonHiltClassEntryPoint.NonHiltClassEntrypointInterface::class.java)
//        val book= infetface.getBook()
//        val item=infetface.getItem()
//        Log.e("TAG", "getBothClass: Book ${book.getBookName()}" )
//        Log.e("TAG", "getBothClass: Item  ${item.getItemName()}" )
//
//    }
}