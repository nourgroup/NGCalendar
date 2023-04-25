# NGCalendar 
##### this library based on the code from <a href="https://github.com/MatthiasKerat/CalendarYT">CalendarYT</a>

### to install library proceed to the following steps :

#### build.gradle (app)
implementation project(':ngCalendar')

#### settings.gradle
include ':ngCalendar'

#### run Calendar :
FullCalendar{
    Log.i("test_calendar","MainActivity $it")
}

### FullCalendar is compose function that you can call: 
#### pass params as bellow : 
startFilterDAY : ChoiceDAY = ChoiceDAY.ALL,
endFilterDAY : DayHour? = null,
listenerClickDay : (DayHour) -> Unit

### you can assign one of these value to startFilterDAY parameter: 
enum class ChoiceDAY {
    ALL,YESTERDAY,TODAY,TOMORROW
}
ALL         : calendar will take as start date default variable 1900/01/01 
YESTERDAY   : Not yet implemented
TODAY       : calendar will take as start a current day 
TOMORROW    : Not yet implemented