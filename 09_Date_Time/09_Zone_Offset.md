# Zone Offset

Before understanding Zone related class need to understand the concept of Time zone and UTC standard

- UTC Offset:  UTC means **Coordinated Universal Time**. The way it is calculated is that we divide the whole world in multiple 15-degree angle from the centre and the section which has cities like London, Dublin and countries like Denmark, Iceland, Ireland, United Kingdom, Portugal, France, Senegal for UTC 0. From this UTC zero offset if you move in one direction that will be called as UTC+1. And similarly, if we go in other directions it will be UTC -1. India and Sri Lanka are in UTC +5.30.

- Time Zone: Time zone is going to represent a specific region, A time zone is an area which observes a uniform standard time for legal, commercial and social purposes. Time zones tend to follow the boundaries between countries and their subdivisions instead of strictly following longitude, because it is convenient for areas in frequent communication to keep the same time. Under UTC offset, multiple time zones also can be present, and vice versa single time zone we may have multiple UTC offset.

![image56.png](../images/image56.png)

**ZoneId** is a class inside the new Date-Time API, which represents a specific time zone. We can get the list of time zones using static method getAvailableZoneIds in ZoneId class.

Set<String> allZones = ZoneId.getAvailableZoneIds();    ***// All zones****for* (String zone: allZones){    System.out.println(zone);}ZoneId zone = ZoneId.of("Asia/Kolkata");ZoneId destZone = ZoneId.of("America/Chicago");

**ZonedDateTime** class provides date time of a specific zone. By passing this ZoneId to the now () method of ZonedDateTime class, we should be able to get the date of a time zone.  We can also use withZoneSameInstant() method if we want to use another time zone time as reference. It will also handle the daylight-saving complexities

ZoneId zone = ZoneId.of("Asia/Kolkata");ZoneId destZone = ZoneId.of("America/Chicago");ZonedDateTime indiaDateTime = ZonedDateTime.now(zone);ZonedDateTime chicagoDateTime = indiaDateTime.withZoneSameInstant(destZone);System.out.println(indiaDateTime);      ***//2024-12-20T22:13:38.450925300+05:30[Asia/Kolkata]***System.out.println(chicagoDateTime);    ***//2024-12-20T10:43:38.450925300-06:00[America/Chicago]***

**ZoneOffset** it is going to represent a fixed offset time as per the UTC standard. We should be able to create the ZonedDateTime objects with the help of ZoneOffset as well. Note this will also give us date time as per time zone and not UTC standard. Getting date time from Zone offset is very similar to ZoneID implementation. First, we need to create the objects of ZoneOffset by invoking these ofHours() method and pass the number of the UTC zone whose date time we want. Then we can pass the ZoneOffset to now() and withZoneSameInstant() method we can get ZonedDateTime as per UTC standard

ZoneOffset offset = ZoneOffset.ofHours(2);ZoneOffset destOffset = ZoneOffset.ofHours(-8);ZonedDateTime utc2DateTime = ZonedDateTime.now(offset);ZonedDateTime utcDestDateTime = indiaDateTime.withZoneSameInstant(destOffset);System.out.println(utc2DateTime);       ***//2024-12-20T18:43:38.450925300+02:00***System.out.println(utcDestDateTime);    ***//2024-12-20T08:43:38.450925300-08:00***

Both zone id and zone offset will give same information just that zone id date time will have additional zone details

ZoneId zone = ZoneId.of("America/Los_Angeles");ZonedDateTime laZone = ZonedDateTime.now(zone);System.out.println(laZone);     ***//2024-12-20T19:08:47.273808500-08:00[America/Los_Angeles]***ZoneOffset laOffset = ZoneOffset.ofHours(-8);ZonedDateTime laDateTime = ZonedDateTime.now(laOffset);System.out.println(laDateTime); ***//2024-12-20T19:08:47.273808500-08:00***

**OffsetDateTime-** If you are simply looking for an option which is going to help you to build the date and time based upon the offset, ignoring all the daylight-saving rules and geographical locations in such scenarios, you can happily use OffsetDateTime.  For this we can only use ZoneOffset object, but not the ZoneId objects

ZoneOffset offset = ZoneOffset.ofHours(2);ZoneOffset destOffset = ZoneOffset.ofHours(-8);OffsetDateTime offsetDateTime = OffsetDateTime.now(offset);OffsetDateTime destOffsetDateTime = offsetDateTime.withOffsetSameInstant(destOffset);System.out.println(offsetDateTime);System.out.println(destOffsetDateTime);

ZoneId = ZoneOffset + ZoneRules

| ZoneId zone = ZoneId.of("America/Los_Angeles");<br>ZonedDateTime laZone = ZonedDateTime.now(zone);<br>System.out.println(laZone); | ZoneOffset laOffset = ZoneOffset.ofHours(-8);<br>ZonedDateTime laDateTime = ZonedDateTime.now(laOffset);<br>System.out.println(laDateTime); | ZoneOffset laOffset = ZoneOffset.ofHours(-8);<br>OffsetDateTime destOffsetDateTime = OffsetDateTime.now(laOffset);<br>System.out.println(destOffsetDateTime); |
| --- | --- | --- |

We have another class OffsetTime to represent time

OffsetTime offsetTime = OffsetTime.of(15, 30, 0, 0, ZoneOffset.ofHours(2));System.out.println(offsetTime);*int* hour = offsetTime.getHour();*int* minutes = offsetTime.getMinute();*int* second = offsetTime.getSecond();ZoneOffset offset = offsetTime.getOffset();OffsetTime laterTime = offsetTime.plusHours(2);*boolean* isBefore = offsetTime.isBefore(laterTime);

DST

Daylight Saving Time is a concept where few countries like United States, Canada and Australia, they are going to advance their clocks by one hour during the warmer months so that darkness falls at a later clock time. If you take United States as an example, what they are going to do is, they are going to advance their clock by one hour on every March 2nd Sunday at midnight, 2 a.m. Similarly during the November 1st Sunday after the midnight, they're going to make their clock one hour behind.

Lets take example of a fight taking off on 8th march from LA it will reach NY at 5 am

ZonedDateTime departure1 = ZonedDateTime.of(2024, 3, 8, 21, 0, 0, 0, ZoneId.of("America/Los_Angeles"));***// Expected arrival in NYC (assuming flight duration is 4 hours)***ZonedDateTime arrival1 = departure1.plusHours(4).withZoneSameInstant(ZoneId.of("America/New_York"));System.out.println("Departure (PST): " + departure1);   ***//Departure (PST): 2024-03-08T21:00-08:00[America/Los_Angeles]***System.out.println("Arrival (EST): " + arrival1);       ***//Arrival (EST): 2024-03-09T04:00-05:00[America/New_York]***

Same flight taking off on 9th March after day light savings will reach at 5 a.m

ZonedDateTime departure = ZonedDateTime.of(2024, 3, 9, 21, 0, 0, 0, ZoneId.of("America/Los_Angeles"));***// Expected arrival in NYC (assuming flight duration is 4 hours)***ZonedDateTime arrival = departure.plusHours(4).withZoneSameInstant(ZoneId.of("America/New_York"));System.out.println("Departure (PST): " + departure);    ***//Departure (PST): 2024-03-09T21:00-08:00[America/Los_Angeles]***System.out.println("Arrival (EST): " + arrival);        ***//Arrival (EST): 2024-03-10T05:00-04:00[America/New_York]***

So zoned date will take care of such daylight savings as well

Most of the countries in the world they follow the GregorianCalendar. We call this GregorianCalendar as ISO calendar or English calendar, but apart from ISO or GregorianCalendar, we have other types of non-ISO calendars as well exist in few countries.  Such calendars are Thai Buddhist calendar, which is going to be followed inside Thailand. Hijra calendar which is going to be followed by the Islamic countries. Minguo calendar, which is followed inside the China and Japanese calendar, which is followed inside Japan. All these kinds of calendars we can call as non-ISO calendars.  We can also convert a non ISO date to ISO date and vice versa.

JapaneseDate japaneseDate = JapaneseDate.now();LocalDate localDate = LocalDate.now();System.out.println(japaneseDate);System.out.println(localDate);JapaneseDate japaneseDate1 = JapaneseDate.from(localDate);LocalDate localDate1 = LocalDate.from(japaneseDate);System.out.println(japaneseDate1);System.out.println(localDate1);

We have DateTimeFormatter class to format the dates with new API

LocalDate localDate = LocalDate.of(2015,3,18);LocalTime localTime = LocalTime.of(15,30,0);LocalDateTime localDateTime = LocalDateTime.of(2015,3,18,15,30,0);System.out.println(localDate);          ***//2015-03-18***System.out.println(localTime);          ***//15:30***System.out.println(localDateTime);      ***//2015-03-18T15:30***DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");String formattedDate = dateFormatter.format(localDate);System.out.println(formattedDate);      ***//2015/03/18***DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");String formattedTime = timeFormatter.format(localTime);System.out.println(formattedTime);      ***//3:30 PM***DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a");String formattedDateTime = dateTimeFormatter.format(localDateTime);System.out.println(formattedDateTime);  ***//18/03/2015 3:30 PM***String formattedDate1 = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);System.out.println(formattedDate1);     ***//2015-03-18***DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.GERMAN);String formattedDateGermany = germanFormatter.format(localDate);System.out.println(formattedDateGermany);***//18. März 2015***

We can parse the date from string also

DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");String dateString = "2015-03-18";String timeString = "15:30:00";String dateTimeString = "18/03/2015 15:30:00";LocalDate localDate = LocalDate.parse(dateString,dateFormatter);LocalTime localTime = LocalTime.parse(timeString,timeFormatter);LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString,dateTimeFormatter);
