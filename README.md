# NGCalendar
library will use the code from github.com/MatthiasKerat

installation of librarie

build.gradle (app)
implementation project(':ngCalendar')

settings.gradle
include ':ngCalendar'

run Calendar :
FullCalendar{
    Log.i("test_calendar","MainActivity $it")
}

FullCalendar is compose function :
@Composable
fun FullCalendar(
    startFilterDAY : ChoiceDAY = ChoiceDAY.ALL,
    endFilterDAY : DayHour? = null,
    listenerClickDay : (DayHour) -> Unit
) {
...
}

enum class ChoiceDAY {
    ALL,YESTERDAY,TODAY,TOMORROW
}
ALL : 
YESTERDAY : 
TODAY : 
TOMORROW : 

param endFilterDAY can be days number after the start day or a definit day
