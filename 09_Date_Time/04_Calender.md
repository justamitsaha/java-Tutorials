# Calender

To overcome the few of the drawbacks of java.util.Date Java team, they introduced a new set of classes with the name Calendar. This is an abstract class, and this class is implemented by another concrete class which is GregorianCalendar. GregorianCalendar is a hybrid calendar that supports both the Julian and Gregorian calendar systems

The calender has a get instance method which returns Gregorian calender

Calendar calendar = Calendar.getInstance();System.out.println(calendar); ***//java.util.GregorianCalendar***

It has a get time method which returns Date object with current time

Calendar calendar = Calendar.getInstance();Date date = calendar.getTime();System.out.println(date);   ***//Thu Dec 19 09:57:33 IST 2024***

We can also create instance of Gregorian calendar, with default constructor we will get current date and time but we can also pass any particular dare and we also don’t gave to do calculation from 1900 like Date class. Month starts with index of zero so January will be 0, February 1 etc., but we can remove the confusion of month by passing constants for month

GregorianCalendar gregorianCalendar = *new* GregorianCalendar(2100, Calendar.JANUARY, 1);Date currentDateObj = gregorianCalendar.getTime();System.out.println(currentDateObj);

Using Gregorian Calendar object, we can invoke the get() method. To this get() method we can pass a constant which is Calendar.YEAR Calender.Month etc to get specific data. This get method based on the index passed via YEAR, MONTH etc. will call getYear, getMonth etc. but we don’t have to worry about the internal handling

```java
int year = gregorianCalendar.get(Calendar.YEAR);int month = gregorianCalendar.get(Calendar.MONTH);int date = gregorianCalendar.get(Calendar.DATE);int hour = gregorianCalendar.get(Calendar.HOUR_OF_DAY);int minutes = gregorianCalendar.get(Calendar.MINUTE);int seconds = gregorianCalendar.get(Calendar.SECOND);int milliSeconds = gregorianCalendar.get(Calendar.MILLISECOND);System.out.printf("Current Date and Time in : %04d-%02d-%02d %02d:%02d:%02d:%04d%n",        year, month, date, hour, minutes, seconds,milliSeconds);

```

Similarly we have set methods also

gregorianCalendar.set(Calendar.YEAR, 2101);gregorianCalendar.set(Calendar.MONTH, Calendar.FEBRUARY);Date currentDateObj1 = gregorianCalendar.getTime();System.out.println(currentDateObj1);

We can format the date using SimpleDateFormat We can also add or subtract date, month etc. from Calender

SimpleDateFormat dateFormat = *new* SimpleDateFormat("yyyy-MMMM-dd HH:mm:ss");String formattedDate = dateFormat.format(gregorianCalendar.getTime());System.out.println(formattedDate);gregorianCalendar.add(Calendar.HOUR, 2);        ***//2 hours added***gregorianCalendar.add(Calendar.MONTH, -3);      ***//3 months reduced***formattedDate = dateFormat.format(gregorianCalendar.getTime());System.out.println(formattedDate);

Can also check leap year

*boolean* isLeapYear = gregorianCalendar.isLeapYear(2049);System.out.println(isLeapYear);
