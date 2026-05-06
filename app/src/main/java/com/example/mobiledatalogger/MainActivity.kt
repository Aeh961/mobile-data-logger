package com.example.mobiledatalogger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mobiledatalogger.ui.theme.MobileDataLoggerTheme
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = this
            val localContext = LocalContext.current
            val scope = rememberCoroutineScope()

            var selectedType by remember { mutableStateOf("Health") }
            var inputText by remember { mutableStateOf("") }
            var insightText by remember { mutableStateOf("") }
            var isLoading by remember { mutableStateOf(false) }

            var dataList by remember {
                mutableStateOf(
                    context.getSharedPreferences("data", Context.MODE_PRIVATE)
                        .getStringSet("entries", emptySet())!!
                        .toList()
                )
            }

            MobileDataLoggerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Mobile Data Logger",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(onClick = { selectedType = "Health" }) {
                                Text("Health")
                            }

                            Button(onClick = { selectedType = "Activity" }) {
                                Text("Activity")
                            }

                            Button(onClick = { selectedType = "Sleep" }) {
                                Text("Sleep")
                            }
                        }

                        Text(
                            text = "Selected: $selectedType",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        TextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            label = { Text("Log health, activity, or sleep data") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                if (inputText.isNotBlank()) {
                                    val timestamp = SimpleDateFormat(
                                        "yyyy-MM-dd HH:mm:ss",
                                        Locale.getDefault()
                                    ).format(Date())

                                    val newEntry = "$timestamp [$selectedType] - $inputText"
                                    val updatedList = dataList + newEntry

                                    dataList = updatedList

                                    val prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
                                    prefs.edit().putStringSet("entries", updatedList.toSet()).apply()

                                    inputText = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Add Entry")
                        }

                        Button(
                            onClick = {
                                val jsonArray = JSONArray()

                                dataList.forEach { entry ->
                                    jsonArray.put(entry)
                                }

                                val exportText = jsonArray.toString(4)

                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, exportText)
                                    type = "text/plain"
                                }

                                val shareIntent = Intent.createChooser(sendIntent, "Export Data")
                                localContext.startActivity(shareIntent)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Export Data")
                        }

                        Button(
                            onClick = {
                                if (dataList.isEmpty()) {
                                    insightText = "No data to analyze yet."
                                } else {
                                    scope.launch {
                                        isLoading = true

                                        try {
                                            val combinedData = dataList.joinToString("\n")

                                            val response = RetrofitClient.api.getAiSummary(
                                                AiSummaryRequest(
                                                    sleep = "Based on logs",
                                                    activity = "Based on logs",
                                                    mood = "Unknown",
                                                    notes = combinedData
                                                )
                                            )

                                            insightText = response.suggestion
                                        } catch (e: Exception) {
                                            insightText = "Error connecting to backend: ${e.message}"
                                        }

                                        isLoading = false
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Get AI Insights")
                        }

                        if (isLoading) {
                            Text("Loading AI...")
                        }

                        if (insightText.isNotEmpty()) {
                            Text(
                                text = insightText,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        Text(
                            text = "Entries",
                            style = MaterialTheme.typography.titleMedium
                        )

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(dataList) { entry ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = entry,
                                            modifier = Modifier.weight(1f)
                                        )

                                        Button(
                                            onClick = {
                                                val updatedList = dataList.filter { it != entry }
                                                dataList = updatedList

                                                val prefs = context.getSharedPreferences(
                                                    "data",
                                                    Context.MODE_PRIVATE
                                                )
                                                prefs.edit()
                                                    .putStringSet("entries", updatedList.toSet())
                                                    .apply()
                                            }
                                        ) {
                                            Text("Delete")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}