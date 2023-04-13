package com.ngplus.ngcalendar

class DayHour(val year: Int, val month : Int, val day : Int, val hours: List<String>): Comparable<DayHour>{
    override fun equals(other: Any?): Boolean {
        //return super.equals(other)
        val day = other as DayHour
        return day.day==this.day && day.month==this.month && day.year==this.year
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "$year/$month/$day"
    }

    override fun compareTo(other: DayHour): Int {
        if(this.year>other.year){
            return 1
        }else if(this.year==other.year){
            if(this.month>other.month){
                return 1
            }else if(this.month==other.month){
                if(this.day>other.day){
                    return 1
                }else if(this.day==other.day){
                    return 0
                }else{
                    return -1
                }
            }else{
                return -1
            }
        }else{
            return -1
        }
    }
}