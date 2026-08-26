# New Date Time

With Java 8 new date time API was introduced which is much more robust. These introduced new classes under java.time package LocalDate, LocalTime, LocalDateTime and ZonedDateTime

![image52.png](../images/image52.png)

Current date time

LocalDate localDate = LocalDate.now();LocalTime localTime = LocalTime.now();LocalDateTime localDateTime = LocalDateTime.now();ZonedDateTime zonedDateTime = ZonedDateTime.now();System.out.println(localDate);          ***//2024-12-19***System.out.println(localTime);          ***//13:35:56.964948500***System.out.println(localDateTime);      ***//2024-12-19T13:35:56.964948500***System.out.println(zonedDateTime);      ***//2024-12-19T13:35:56.964948500+05:30[Asia/Calcutta]***

**Of Method** To create date and time with specific values we need to use of method

LocalDate date1 = LocalDate.of(2015, 3, 18);LocalDate date = LocalDate.of(2015, Month.MARCH, 18);   ***// Using Enums for months***LocalTime time = LocalTime.of(14, 30);          ***// Can support nanoseconds as well***LocalDateTime dateTime = LocalDateTime.of(2015,3,18,14,30);

ZoneId newYorkTimeZone = ZoneId.of("America/New_York");     ***//New York time zone***ZonedDateTime zonedDateTime = ZonedDateTime.of(2015,3,18,14,30,0, 0, newYorkTimeZone);System.out.println(date);System.out.println(time);System.out.println(dateTime);System.out.println(zonedDateTime);***//Some of the many utility methods***LocalDate someDate = LocalDate.ofEpochDay(1000);    ***//Date of no of days from Epoc date***LocalDate dayIn2000 = LocalDate.ofYearDay(2001, 100);   ***// 100th day of year 2001***System.out.println(someDate);System.out.println(dayIn2000);

**From method-**  It is a static method to extract date, time

LocalDateTime dateTime = LocalDateTime.of(2015, Month.MARCH, 18, 22, 30);System.out.println(dateTime);LocalDate derivedDate = LocalDate.from(dateTime);LocalTime derivedTime = LocalTime.from(dateTime);System.out.println(derivedDate);System.out.println(derivedTime);

**With methods-** Local date time is immutable. So, when we need to change the date we can use “with” method. It’s not just applicable to LocalDateTime but is applicable to LocalDate, LocalTime etc.

LocalDateTime dateTime = LocalDateTime.of(2015, Month.MARCH, 18, 22, 30);System.out.println(dateTime);LocalDateTime dateTime1 = dateTime.withYear(2030);      ***// New Date by modifying year***LocalDateTime dateTime2 = dateTime.withYear(2030).withMonth(10); ***// New date by modifying year and month***System.out.println(dateTime1);System.out.println(dateTime2);

**To And At methods** To methods helps to get LocalDate, time etc. from Date time object same we can do with from methods also

LocalDateTime dateTime = LocalDateTime.of(2015, Month.MARCH, 18, 22, 30);System.out.println(dateTime);LocalDate date = dateTime.toLocalDate();LocalTime time = dateTime.toLocalTime();System.out.println(date);System.out.println(time);

**At Methods** will help us to create new date time objects from existing one by supplementing information, this may seem similar to with methods but there is a difference with methods will give same type of object, So date time will give date time object. But with method can give us different type if object for e.g. below we are getting object of LocalDateTime by applying with method to LocalDate

LocalDate localDate = LocalDate.of(2015, 3, 18);LocalDateTime startOfDay = localDate.atStartOfDay();        ***// Time will be midnight*** System.out.println(startOfDay);LocalDateTime localDateTime = localDate.atTime(18, 20); ***// Time will be as passed***System.out.println(localDateTime);

**Get methods** will help us to get specific information like  year, month etc.

LocalDate localDate = LocalDate.of(2015, 3 , 18);*int* year = localDate.getYear(); ***// 2015***Month month = localDate.getMonth(); ***// MARCH****int* day  = localDate.getDayOfMonth();DayOfWeek dayOfWeek = localDate.getDayOfWeek();***// Monday Tuesday etc***

**Plus minus methods** helps us to do addition subtraction to date time

LocalDate localDate = LocalDate.of(2015, 3 , 18);LocalDate ld1 = localDate.plusDays(6); ***// 2015-03-24***LocalDate ld2 = localDate.plusMonths(6); ***// 2015-09-18***LocalDate ld3 = localDate.plusWeeks(2); ***// 2015-04-01***LocalDate ld4 = localDate.minusMonths(9); ***// 2014-06-18***LocalDate ld5 = localDate.minusYears(7); ***// 2008-03-18***System.out.println(ld1);System.out.println(ld2);System.out.println(ld3);System.out.println(ld4);System.out.println(ld5);
