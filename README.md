# NGCalendar 
##### this library based on the code from <a href="https://github.com/MatthiasKerat/CalendarYT">CalendarYT</a>

### to install library proceed to the following steps :

#### build.gradle (app)
implementation project(':ngCalendar')

#### settings.gradle
include ':ngCalendar'

#### project don't need to activate jetifier 
android.enableJetifier=false

#### run Calendar :
FullCalendar(0,10){
    day.value = it
    Log.i("test_calendar","MainActivity $it")
}
### FullCalendar is compose function that you can call: 
#### pass params as bellow : 


### activate days by putting number that belong to the current date
#### ex. 
