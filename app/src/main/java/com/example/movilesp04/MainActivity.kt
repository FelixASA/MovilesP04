package com.example.movilesp04

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movilesp04.ui.theme.MovilesP04Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovilesP04Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingPreview()
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(R.string.Titulo),
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(R.drawable.imagen_principal),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(modifier = Modifier.size(120.dp, 50.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                val intent = Intent(context, TablaPeriodica::class.java)
                context.startActivity(intent)
            }) {
            Text(text = stringResource(R.string.BtnIniciar),
            fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovilesP04Theme {
        Greeting(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize())
    }
}