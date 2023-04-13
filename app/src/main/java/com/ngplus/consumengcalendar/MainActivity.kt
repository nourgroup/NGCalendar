package com.ngplus.consumengcalendar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ngplus.consumengcalendar.ui.theme.ConsumeNGCalendarTheme
import com.ngplus.ngcalendar.Cal
import com.ngplus.ngcalendar.DayHour

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val day = remember{
                mutableStateOf(DayHour(1,1,1, listOf("")))
            }
            ConsumeNGCalendarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // TODO lib return month-1 ,user have to add one to the month
                    // ALL, YESTERDAY, TODAY, TOMORROW
                    // DAY.ALL is by default
                    // endFilterDAY = Day(2023,5,30, listOf())
                    Cal{
                        day.value = it
                        Log.i("test_calendar","MainActivity $it")
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(text = "${day.value.year}/${day.value.month.plus(1)}/${day.value.day}")
                        day.value.hours.forEach {
                            Text(it)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ConsumeNGCalendarTheme {
        Cal(
            endFilterDAY = DayHour(2023,5,1, listOf())
        ){
        }
    }
}