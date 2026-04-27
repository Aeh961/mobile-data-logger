package com.example.mobiledatalogger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobiledatalogger.ui.theme.MobileDataLoggerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var inputText by remember { mutableStateOf("") }
            var dataList by remember { mutableStateOf(listOf<String>()) }

            MobileDataLoggerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Mobile Data Logger",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.padding(8.dp))

                        TextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            label = { Text("Enter data") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.padding(8.dp))

                        Button(
                            onClick = {
                                if (inputText.isNotBlank()) {
                                    val timestamp = java.text.SimpleDateFormat(
                                        "yyyy-MM-dd HH:mm:ss",
                                        java.util.Locale.getDefault()
                                    ).format(java.util.Date())

                                    dataList = dataList + "$timestamp - $inputText"
                                    inputText = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Add Entry")
                        }

                        Spacer(modifier = Modifier.padding(12.dp))

                        Text(
                            text = "Entries",
                            style = MaterialTheme.typography.titleMedium
                        )

                        LazyColumn {
                            items(dataList) { entry ->
                                Text(entry)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobileDataLoggerTheme {
        Greeting("Android")
    }
}
