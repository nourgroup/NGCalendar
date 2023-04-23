# NGCalendar 
## library is using the code from <a href="https://github.com/MatthiasKerat/CalendarYT">CalendarYT</a>

## installation of library you ca proceed :

### build.gradle (app)
implementation project(':ngCalendar')

### settings.gradle
include ':ngCalendar'

### run Calendar :
FullCalendar{
    Log.i("test_calendar","MainActivity $it")
}

### FullCalendar is compose function that you can call: 

@Composable
fun FullCalendar(
    startFilterDAY : ChoiceDAY = ChoiceDAY.ALL,
    endFilterDAY : DayHour? = null,
    listenerClickDay : (DayHour) -> Unit
) {
...
}

### you can assign one of these value to startFilterDAY parameter: 
enum class ChoiceDAY {
    ALL,YESTERDAY,TODAY,TOMORROW
}
ALL         : calendar will take as start date default variable 1900/01/01 
YESTERDAY   : Not yet implemented
TODAY       : Activate a day that is app launch
TOMORROW    : Not yet implemented

