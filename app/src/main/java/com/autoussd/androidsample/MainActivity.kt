package com.autoussd.androidsample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autoussd.AutoUssd
import com.autoussd.androidsample.ui.theme.AndroidSampleTheme

class MainActivity : ComponentActivity() {
  companion object {
    private const val TAG = "MainActivity"
  }

  // 3. Declare an instance of AutoUssd
  lateinit var autoussd: AutoUssd

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // 4. Initialize the instance
    autoussd = AutoUssd(
      this, "Buvll5o7D3bw8ZDLR0a2caAO3XB2", listOf("OxrRDRD65l4mMf7PCw5xC"),
      object : AutoUssd.Callback() {
        override fun onReady(instance: AutoUssd) {
          // You should wait for this callback
          // before calling the execute method
          Log.d(TAG, "AutoUssd Ready!!!")
        }

        override fun onLoadSessionsError(description: String) {
          Log.e(TAG, "Unable to load sessions: $description")
        }

        override fun onUnsupportedNetworkError(description: String) {
          Log.e(TAG, "Unable to load sessions: $description")
        }

        override fun onSessionMenuMismatch(mismatchedContent: String, candidateKeywords: List<String>) {
          Log.e(TAG, "Session menu mismatch")
          Log.e(TAG, "USSD content: $mismatchedContent")
          Log.e(TAG, "Compared to: ${candidateKeywords.joinToString(separator = "|||||")}")
        }

        override fun onSessionCompleted(lastMenuContent: String) {
          Log.d(TAG, "Session completed with last menu content: $lastMenuContent")
        }

        override fun onSessionTimeout() {
          Log.e(TAG, "Session timed out")
        }

        override fun onSessionError(description: String) {
          Log.e(TAG, "Session error: $description")
        }

        override fun onSessionInterrupted() {
          super.onSessionInterrupted()
          Log.e(TAG, "Session interrupted by user")
        }
      }
    )

    enableEdgeToEdge()

    setContent {
      AndroidSampleTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Greeting(
            modifier = Modifier.padding(innerPadding)
          ) { autoussd.execute("OxrRDRD65l4mMf7PCw5xC", mapOf()) }
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    // 5. Dispose AutoUssd when done
    autoussd.dispose()
  }
}

@Composable
fun Greeting(
  modifier: Modifier = Modifier,
  onBegin: () -> Unit
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = modifier
      .fillMaxSize()
      .padding(24.dp),
  ) {
    Image(
      painter = painterResource(R.mipmap.logo),
      contentDescription = "Logo",
      modifier = Modifier.fillMaxWidth(0.6f)
    )

    Spacer(modifier = Modifier.height(24.dp))

    Text(
      text = "Sample App - Android",
      fontWeight = FontWeight.SemiBold,
      fontSize = 20.sp
    )

    Spacer(modifier = Modifier.height(24.dp))

    Button(onClick = { onBegin() }) {
      Text(text = "Execute Session")
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  AndroidSampleTheme {
    Greeting {}
  }
}