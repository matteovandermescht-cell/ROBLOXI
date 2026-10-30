package com.robloxi.app

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = MaterialTheme.colorScheme.copy(
                    primary = Color(0xFF5EA8FF),
                    secondary = Color(0xFF8C7BFF),
                    background = Color(0xFF0B1020),
                    surface = Color(0xFF161F33)
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0B1020)
                ) {
                    RobloxiApp()
                }
            }
        }
    }
}

data class ScreenSpec(
    val route: String,
    val title: String,
    val subtitle: String
)

@Composable
fun RobloxiApp() {
    val screens = listOf(
        ScreenSpec("home", "Home", "Dashboard overview"),
        ScreenSpec("arena", "Arena", "Instant match actions"),
        ScreenSpec("game", "Game", "Gameplay and session control"),
        ScreenSpec("settings", "Settings", "Customization and profile"),
    )

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = screens.first().route,
        modifier = Modifier.fillMaxSize()
    ) {
        screens.forEachIndexed { index, screen ->
            composable(screen.route) {
                NavigationScreen(
                    screen = screen,
                    totalScreens = screens.size,
                    currentIndex = index,
                    onNext = {
                        val nextIndex = (index + 1) % screens.size
                        navController.navigate(screens[nextIndex].route) {
                            launchSingleTop = true
                        }
                    },
                    onPrevious = {
                        val prevIndex = if (index == 0) screens.lastIndex else index - 1
                        navController.navigate(screens[prevIndex].route) {
                            launchSingleTop = true
                        }
                    },
                    onHome = {
                        navController.navigate(screens.first().route) {
                            popUpTo(screens.first().route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NavigationScreen(
    screen: ScreenSpec,
    totalScreens: Int,
    currentIndex: Int,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onHome: () -> Unit
) {
    val accent = Color(0xFF5EA8FF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1020))
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "ROBLOXI",
                color = Color(0xFF8C7BFF),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${screen.title} • ${currentIndex + 1}/$totalScreens",
                color = Color(0xFFF4F7FF),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = screen.subtitle,
                color = Color(0xFFB4BFDA),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF161F33), shape = RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                Text(
                    text = "AI-generated screen ready.\nYour app can auto-navigate between game states, dashboards, and settings.",
                    color = Color(0xFFF4F7FF),
                    fontSize = 17.sp,
                    lineHeight = 28.sp
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = accent),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Next Screen", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onPrevious,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2942)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Previous", color = Color(0xFFF4F7FF), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onHome,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8C7BFF)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Home", color = Color(0xFFFFFFFF), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
