package com.ngplus.ngcalendar.ui.theme

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class FullCalendar : ComponentActivity() {
    private val allDays = listOf( "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" ,"SUNDAY")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val calendarInputList by remember {
                mutableStateOf(createCalendarList())
            }
            var clickedCalendarElem by remember {
                mutableStateOf<CalendarInput?>(null)
            }
            var monthState by remember {
                mutableStateOf<Day?>(Day(1,1,1, listOf()))
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gray),
                contentAlignment = Alignment.TopCenter
            ){
                Calendar(
                    calendarInput = calendarInputList,
                    onDayClick = { day ->
                        clickedCalendarElem = calendarInputList.find { it.day == day }
                        monthState = day
                    },
                    month = monthState?.month.toString(),
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.3f)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .align(Alignment.Center)
                ){
                    Text(text = "${clickedCalendarElem?.day?.day}/${clickedCalendarElem?.day?.month}/${clickedCalendarElem?.day?.year}")
                    clickedCalendarElem?.day?.hours?.forEach{
                        Text("$it")
                    }
                }
            }
        }
    }


    private fun createCalendarList(): List<CalendarInput> {
        val calendarInputs = mutableListOf<CalendarInput>()
        val calendar = Calendar.getInstance()
        val numberOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        // data from api
        val dayOfWeek = 7
        val todayName = allDays[dayOfWeek - 1]
        val dayOfMonth = 2
        val firstDayInMonth = 6
        //val nameFirstDayInMonth = allDays[firstDayInMonth - 1]
        val month = 4
        val year = 2023
        // end data from api

        for (j in 1 until firstDayInMonth){
            calendarInputs.add(CalendarInput(Day(0,0,0, listOf("0:00"))))
        }
        for (i in 1..numberOfDaysInMonth) {
            calendarInputs.add(
                CalendarInput(
                    // call DB
                    Day(year,month,i, listOf("9:00","9:30")),
                )
            )
        }
        return calendarInputs
    }

}

private const val CALENDAR_ROWS = 5
private const val CALENDAR_COLUMNS = 7

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    calendarInput: List<CalendarInput>,
    onDayClick:(Day)->Unit,
    strokeWidth:Float = 5f,
    month:String
) {

    var canvasSize by remember {
        mutableStateOf(Size.Zero)
    }
    var clickAnimationOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    var animationRadius by remember {
        mutableStateOf(0f)
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = month,
            fontWeight = FontWeight.SemiBold,
            color = white,
            fontSize = 40.sp
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = { offset ->
                            val column =
                                (offset.x / canvasSize.width * CALENDAR_COLUMNS).toInt() + 1
                            val row = (offset.y / canvasSize.height * CALENDAR_ROWS).toInt() + 1
                            /*
                            find the clicked day the belong to canvas
                             */
                            val indexDay = (column - 1) + (row - 1) * CALENDAR_COLUMNS
                            val selectedDay = calendarInput[indexDay]
                            if (selectedDay.day.day <= calendarInput.size) {
                                onDayClick(selectedDay.day)
                                clickAnimationOffset = offset
                                scope.launch {
                                    animate(0f, 225f, animationSpec = tween(300)) { value, _ ->
                                        animationRadius = value
                                    }
                                }
                            }

                        }
                    )
                }
        ){
            val canvasHeight = size.height
            val canvasWidth = size.width
            canvasSize = Size(canvasWidth,canvasHeight)
            val ySteps = canvasHeight/ CALENDAR_ROWS
            val xSteps = canvasWidth/ CALENDAR_COLUMNS

            val column = (clickAnimationOffset.x / canvasSize.width * CALENDAR_COLUMNS).toInt() + 1
            val row = (clickAnimationOffset.y / canvasSize.height * CALENDAR_ROWS).toInt() + 1

            val path = Path().apply {
                moveTo((column-1)*xSteps,(row-1)*ySteps)
                lineTo(column*xSteps,(row-1)*ySteps)
                lineTo(column*xSteps,row*ySteps)
                lineTo((column-1)*xSteps,row*ySteps)
                close()
            }

            clipPath(path){
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(orange.copy(0.8f), orange.copy(0.2f)),
                        center = clickAnimationOffset,
                        radius = animationRadius + 0.1f
                    ),
                    radius = animationRadius + 0.1f,
                    center = clickAnimationOffset
                )
            }

            drawRoundRect(
                orange,
                cornerRadius = CornerRadius(5f,5f),
                style = Stroke(
                    width = strokeWidth
                )
            )
            /*
            draw lines for row
             */
            for(i in 1 until CALENDAR_ROWS){
                drawLine(
                    color = orange,
                    start = Offset(0f,ySteps*i),
                    end = Offset(canvasWidth, ySteps*i),
                    strokeWidth = strokeWidth
                )
            }
            /*
            draw lines for column
             */
            for(i in 1 until CALENDAR_COLUMNS){
                drawLine(
                    color = orange,
                    start = Offset(xSteps*i,0f),
                    end = Offset(xSteps*i, canvasHeight),
                    strokeWidth = strokeWidth
                )
            }
            val textHeight = 17.dp.toPx()
            /*
            display days
             */
            for(i in calendarInput.indices){
                val textPositionX = xSteps * (i% CALENDAR_COLUMNS) + strokeWidth
                val textPositionY = (i / CALENDAR_COLUMNS) * ySteps + textHeight + strokeWidth/2
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        if(calendarInput[i].day.day==0) "" else calendarInput[i].day.day.toString(),
                        textPositionX,
                        textPositionY,
                        Paint().apply {
                            textSize = textHeight
                            color = white.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }
    }

}

class Day( val year: Int, val month : Int,val day : Int,val hours: List<String>){
    override fun equals(other: Any?): Boolean {
        //return super.equals(other)
        val day = other as Day
        return day.day==this.day && day.month==this.month && day.year==this.year
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}
data class CalendarInput(
    val day : Day,
)
