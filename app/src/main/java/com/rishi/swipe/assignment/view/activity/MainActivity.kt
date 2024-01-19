package com.rishi.swipe.assignment.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.rishi.swipe.assignment.R
import com.rishi.swipe.assignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint



/**
 * Main entry point of the application. Displays the main navigation graph.
 *
 * @see AppCompatActivity
 * @see AndroidEntryPoint
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Binding for the main activity layout
    private lateinit var activityMainBinding: ActivityMainBinding

    // Configuration for the app bar navigation
    private lateinit var appBarConfiguration: AppBarConfiguration

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in [onSaveInstanceState].
     * Note: Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using view binding
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Set the toolbar as the action bar
        setSupportActionBar(activityMainBinding.toolbar)

        // Get the NavController for navigating through the fragments
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Configure the app bar navigation with the NavController's graph
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /**
     * Called when the user presses the Up button in the app bar.
     *
     * @return `true` if Up navigation completed successfully, `false` otherwise.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
