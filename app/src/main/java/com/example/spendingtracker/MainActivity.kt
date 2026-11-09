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
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
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

            setContent {
                SpendingTrackerTheme {

                    var currentScreen by remember {
                        mutableStateOf("splash")
                    }

                    val DailyBudget = arrayOf(
                        500.0, 250.0, 1000.0,
                        500.0, 600.0, 200.0, 0.0
                    )

                    val DailySpent = arrayOf(
                        340.0, 250.0, 300.0,
                        800.0, 300.0, 250.0, 85.0
                    )

                    LaunchedEffect(Unit) {
                        delay(3000)
                        currentScreen = "main"
                    }

                    Scaffold { innerPadding ->

                        when (currentScreen) {

                            "splash" -> com.example.spendingtracker.SplashScreen(
                                modifier = Modifier.padding(innerPadding)
                            )

                            "main" -> com.example.spendingtracker.MainScreen(
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

                            "view" -> com.example.spendingtracker.ViewScreen(
                                modifier = Modifier.padding(innerPadding),
                                DailyBudget = DailyBudget,
                                DailySpent = DailySpent,
                                onBack = {
                                    currentScreen = "main"
                                }
                            )

                            "calculate" -> com.example.spendingtracker.CalculateScreen(
                                modifier = Modifier.padding(innerPadding),
                                DailyBudget = DailyBudget,
                                DailySpent = DailySpent,
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
    DailyBudget: Array<Double>,
    DailySpent: Array<Double>,
    onBack: () -> Unit
) {
    val dates = ( 7..13).map { day -> Calendar.getInstance().apply { set(2026, Calendar.OCTOBER, day) } }

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
    DailyBudget: Array<Double>,
    DailySpent: Array<Double>,
    onBack: () -> Unit
) {

    var showResults by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Budget Calculation",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = { showResults = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Remaining Budget")
        }

        if (showResults) {

            if (DailyBudget.size != 7 || DailySpent.size != 7) {

                Text(
                    text = "Error: Budget and spending data must contain 7 days.",
                    color = Color.Red
                )

            } else {

                Text(
                    text = "Daily Remaining Budget",
                    fontWeight = FontWeight.Bold
                )

                (7..13).forEachIndexed { index, day ->

                    val remaining =
                        DailyBudget[index] - DailySpent[index]

                    Text(
                        text = "$day/10/2026: R${"%.2f".format(remaining)}"
                    )

                    if (remaining < 0) {
                        Text(
                            text = "You exceeded your budget by R${"%.2f".format(-remaining)}",
                            color = Color.Red
                        )
                    }
                }
                Text("Total remaining for the week: R725",
                    fontWeight = FontWeight.Bold
                    )
            }
        }



        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Main Screen")
        }
    }
}