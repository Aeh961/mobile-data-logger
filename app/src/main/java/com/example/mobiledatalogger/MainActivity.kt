package com.example.mobiledatalogger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
                    Column(modifier = Modifier.padding(innerPadding)) {
                        TextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            label = { Text("Enter data") }
                        )

                        Button(onClick = {
                            if (inputText.isNotBlank()) {
                                val timestamp = java.text.SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm:ss",
                                    java.util.Locale.getDefault()
                                ).format(java.util.Date())

                                dataList = dataList + "$timestamp - $inputText"
                                inputText = ""
                            }
                        }) {
                            Text("Add Entry")
                        }

                        Text("Entries:")

                        dataList.forEach {
                            Text(it)
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
