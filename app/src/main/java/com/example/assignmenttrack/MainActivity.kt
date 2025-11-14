package com.example.assignmenttrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.assignmenttrack.ui.navigation.AppNavigation
import com.example.assignmenttrack.ui.theme.AssignmentTrackTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssignmentTrackTheme {
                AppNavigation()
            }
        }
    }
}
