package com.example.lab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterAppTheme {
                CounterScreen()
            }
        }
    }
}

@Composable
fun CounterAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content
    )
}

@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(5) }
    var incrementCount by remember { mutableStateOf(7) }
    var decrementCount by remember { mutableStateOf(2) }
    var maxValue by remember { mutableStateOf(5) }
    var minValue by remember { mutableStateOf(3) }
    var history by remember { mutableStateOf(listOf<Pair<Int, Boolean>>()) }

    LaunchedEffect(Unit) {
        history = listOf(
            2 to true, 3 to true, 4 to true, 5 to true,
            4 to false, 3 to false, 4 to true, 5 to true
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(Color(0xFFF8FAFF)), // Fondo azul muy claro
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sebastián Lemus",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2D4CC8), // Azul oscuro moderno
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Contador
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Botón decremento - Coral
            Button(
                onClick = {
                    count--
                    decrementCount++
                    history = history + Pair(count, false)
                    updateMinMaxValues(count, history, { minValue = it }, { maxValue = it })
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B6B)),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(60.dp)
            ) {
                Text("-", fontSize = 28.sp, color = Color.White)
            }

            Text(
                text = "$count",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D4CC8),
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            // Botón incremento - Verde esmeralda
            Button(
                onClick = {
                    count++
                    incrementCount++
                    history = history + Pair(count, true)
                    updateMinMaxValues(count, history, { minValue = it }, { maxValue = it })
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(60.dp)
            ) {
                Text("+", fontSize = 28.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Divisor
        Divider(
            thickness = 2.dp,
            color = Color(0xFFE2E8F0),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Estadísticas con fondo y bordes redondeados
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp)
        ) {
            Text(
                "Estadísticas",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D4CC8),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            StatRow("Total increments:", incrementCount.toString(), Color(0xFF10B981))
            StatRow("Total decrements:", decrementCount.toString(), Color(0xFFFF6B6B))
            StatRow("Valor máximo:", maxValue.toString(), Color(0xFF6366F1))
            StatRow("Valor mínimo:", minValue.toString(), Color(0xFF6366F1))
            StatRow("Total cambios:", history.size.toString(), Color(0xFF6366F1))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Historial
        Text(
            "Historial:",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF2D4CC8),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            items(history) { item ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(48.dp)
                        .background(
                            color = if (item.second) Color(0xFF10B981) else Color(0xFFFF6B6B),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.first.toString(),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Reiniciar - Púrpura moderno
        Button(
            onClick = {
                count = 0
                incrementCount = 0
                decrementCount = 0
                maxValue = 0
                minValue = 0
                history = emptyList()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp),
        ) {
            Text("Reiniciar",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun StatRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            fontSize = 16.sp,
            color = Color(0xFF4A5568)
        )
        Text(
            value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

fun updateMinMaxValues(
    currentValue: Int,
    historyList: List<Pair<Int, Boolean>>,
    setMin: (Int) -> Unit,
    setMax: (Int) -> Unit
) {
    if (historyList.isNotEmpty()) {
        val min = historyList.minOf { it.first }
        val max = historyList.maxOf { it.first }
        setMin(min)
        setMax(max)
    } else {
        setMin(currentValue)
        setMax(currentValue)
    }
}

@Preview(showBackground = true)
@Composable
fun CounterAppPreview() {
    CounterAppTheme {
        CounterScreen()
    }
}