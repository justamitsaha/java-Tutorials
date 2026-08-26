# Time Zone

Time zone is supported by class TimeZone. Below will list all available time zones

```java
public static void main(String[] args) {    String[] timeZoneIds = TimeZone.getAvailableIDs();    for(String timeZoneId : timeZoneIds){        System.out.println(timeZoneId);     // Will print all the time zones    }}

```

Get time zone with date API

SimpleDateFormat dateFormat = *new* SimpleDateFormat("yyyy-MMMM-dd HH:mm:ss");dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));String formattedDate = dateFormat.format(*new* Date());System.out.println(formattedDate);dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Vatican"));formattedDate = dateFormat.format(*new* Date());System.out.println(formattedDate)

Get time zone with Gregorian calendar+. We can’t pass getTime method directly as it will local zone time and time zone will be discarded

GregorianCalendar gregorianCalendar = *new* GregorianCalendar(TimeZone.getTimeZone("America/New_York"));*int* year = gregorianCalendar.get(Calendar.YEAR);*int* month = gregorianCalendar.get(Calendar.MONTH);*int* date = gregorianCalendar.get(Calendar.DATE);*int* hour = gregorianCalendar.get(Calendar.HOUR_OF_DAY);*int* minutes = gregorianCalendar.get(Calendar.MINUTE);*int* seconds = gregorianCalendar.get(Calendar.SECOND);*int* milliSeconds = gregorianCalendar.get(Calendar.MILLISECOND);System.out.printf("Current Date and Time in : %04d-%02d-%02d %02d:%02d:%02d:%04d%n",        year, month, date, hour, minutes, seconds,milliSeconds);***// New york time***System.out.println(gregorianCalendar.getTime()); ***//This will not give time zone specific time***
