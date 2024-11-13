package uk.co.devfoundry.viewmodeltutorial

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uk.co.devfoundry.viewmodeltutorial.page.CounterActivity
import uk.co.devfoundry.viewmodeltutorial.ui.theme.ViewModelTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewModelTutorialTheme {
                NavigateToCounter { startActivity(Intent(this, CounterActivity::class.java)) }
            }
        }
    }

}

@Composable
fun NavigateToCounter(navigate: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(100.dp, 250.dp)
            .border(5.dp, color = Color.Blue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Vertically center the buttons within Column
    ) {
        Button(onClick = { navigate() }) {
            Text(text = "Navigate")

        }
    }
}
