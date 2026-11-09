package com.example.spendingtracker

import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spendingtracker.ui.theme.SpendingTrackerTheme
import kotlinx.coroutines.delay
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SpendingTrackerTheme {

                var currentScreen by remember {
                    mutableStateOf("splash")
                }

                LaunchedEffect(Unit) {
                    delay(3000)
                    currentScreen = "main"
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    when (currentScreen) {

                        "splash" -> SplashScreen(
                            modifier = Modifier.padding(innerPadding)
                        )

                        "main" -> MainScreen(
                            modifier = Modifier.padding(innerPadding),
                            onViewSpending = {
                                currentScreen = "view"
                            },
                            onCalculate = {
                                currentScreen = "calculate"
                            },
                            onExit = {
                                finishAffinity()
                            }
                        )

                        "view" -> ViewScreen(
                            modifier = Modifier.padding(innerPadding),
                            onBack = {
                                currentScreen = "main"
                            }
                        )

                        "calculate" -> CalculateScreen(
                            modifier = Modifier.padding(innerPadding),
                            onBack = {
                                currentScreen = "main"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            12.dp,
            Alignment.CenterVertically
        )
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.outline_add_shopping_cart_24
            ),
            contentDescription = "Image",
            modifier = Modifier.width(300.dp).height(300.dp)
        )

        Text(
            text = "Track Your Spending",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onViewSpending: () -> Unit,
    onCalculate: () -> Unit,
    onExit: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            12.dp,
            Alignment.CenterVertically
        )
    ) {
        Button(onClick = onViewSpending) {
            Text("View All Spending")
        }

        Button(onClick = onCalculate) {
            Text("Calculate Remaining Budget")
        }

        Button(onClick = onExit) {
            Text("Exit")
        }
    }
}

@Composable
fun ViewScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val dates = ( 7..13).map { day -> Calendar.getInstance().apply { set(2026, Calendar.OCTOBER, day) } }
    val DailyBudget = arrayOf(
        500,250,250,1000,500,600,200)
    val DailySpent = arrayOf(
        340,250,300,800,300,250,85)
    val Comment = arrayOf(
        "Grocery Shopping", "Takeaway","Petrol","Date Night","Miscellaneous","Personal Care","Snacks"
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "All Spending",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Row {
            Text("Date", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Text("Budget", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Text("Spent", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Text("Comment", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
        }

        dates.forEachIndexed { index, date ->
            Row {
                Text("${date.get(Calendar.DAY_OF_MONTH)}/10", modifier = Modifier.weight(1f))
                Text("R${DailyBudget[index]}", modifier = Modifier.weight(1f))
                Text("R${DailySpent[index]}", modifier = Modifier.weight(1f))
                Text(Comment[index], modifier = Modifier.weight(1f))
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Back")
        }
    }
}

@Composable
fun CalculateScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Calculate Remaining Budget")

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}

