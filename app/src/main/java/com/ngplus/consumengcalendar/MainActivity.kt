package com.ngplus.consumengcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ngplus.consumengcalendar.ui.theme.ConsumeNGCalendarTheme
import com.ngplus.ngcalendar.CalendarTypeSelection
import com.ngplus.ngcalendar.FullCalendar
import com.ngplus.ngcalendar.FullDate
import com.ngplus.ngcalendar.ui.theme.whiteGray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val firstDate = remember {
                mutableStateOf(FullDate())
            }
            val secondDate = remember {
                mutableStateOf(FullDate())
            }
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .background(whiteGray)
                ) {
                    // example. Allow user to select 30 days before the current date
                    // and 60 days after the current date
                    FullCalendar(-30, 60, CalendarTypeSelection.TwoDateSelection) {
                        firstDate.value = it["first_date"]!!
                        secondDate.value = it["second_date"]!!
                    }
                    Text(text = "first : ${firstDate.value}")
                    Text(text = "second : ${secondDate.value}")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ConsumeNGCalendarTheme {
        FullCalendar(
            2, 20
        ) {
        }
    }
}