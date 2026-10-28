# Calendar (`java.util.Calendar` & `GregorianCalendar`)

`java.util.Calendar` was introduced in Java 1.1 to overcome limitations of `java.util.Date`, particularly around field extraction (year, month, day), internationalization, and date arithmetic.

`Calendar` is an abstract class. Its primary concrete implementation in Java standard library is `java.util.GregorianCalendar`.

---

## 1. Creating Calendar Instances

```java
import java.util.Calendar;
import java.util.GregorianCalendar;

// 1. Factory method (returns GregorianCalendar based on system locale and timezone)
Calendar cal = Calendar.getInstance();

// 2. Direct GregorianCalendar instantiation with specific values
// NOTE: Months are 0-indexed (Calendar.JANUARY = 0)
Calendar specificDate = new GregorianCalendar(2024, Calendar.DECEMBER, 18, 14, 30);
```

---

## 2. Reading and Writing Fields (`get` & `set`)

`Calendar` uses integer constants to access individual fields:

```java
Calendar calendar = Calendar.getInstance();

// Getting fields
int year   = calendar.get(Calendar.YEAR);
int month  = calendar.get(Calendar.MONTH);       // 0-based: 0 = Jan, 11 = Dec
int day    = calendar.get(Calendar.DAY_OF_MONTH);
int hour   = calendar.get(Calendar.HOUR_OF_DAY);  // 24-hour format
int minute = calendar.get(Calendar.MINUTE);
int second = calendar.get(Calendar.SECOND);

System.out.printf("Current: %04d-%02d-%02d %02d:%02d:%02d%n",
        year, month + 1, day, hour, minute, second);

// Setting fields
calendar.set(Calendar.YEAR, 2025);
calendar.set(Calendar.MONTH, Calendar.JULY);
calendar.set(Calendar.DAY_OF_MONTH, 1);
```

---

## 3. Date Arithmetic (`add`)

The `add()` method adjusts fields without requiring manual millisecond math:

```java
Calendar cal = Calendar.getInstance();

// Add 2 hours
cal.add(Calendar.HOUR_OF_DAY, 2);

// Subtract 3 months (automatically adjusts year if needed)
cal.add(Calendar.MONTH, -3);

// Add 10 days
cal.add(Calendar.DAY_OF_MONTH, 10);
```

---

## 4. Converting to `Date` and Leap Year Check

```java
Calendar calendar = Calendar.getInstance();

// Convert Calendar to java.util.Date
java.util.Date date = calendar.getTime();

// Leap year check via GregorianCalendar
GregorianCalendar gc = new GregorianCalendar();
boolean isLeap = gc.isLeapYear(2024); // true
System.out.println("2024 is leap year: " + isLeap);
```

---

## 5. Bridging `Calendar` with Modern `java.time`

In Java 8+, convert `Calendar` directly to `ZonedDateTime` or `Instant`:

```java
import java.time.ZonedDateTime;

Calendar cal = Calendar.getInstance();

// Calendar -> Instant -> ZonedDateTime
ZonedDateTime zdt = cal.toInstant().atZone(cal.getTimeZone().toZoneId());
System.out.println(zdt);
```

> [!NOTE]
> Like `Date`, `Calendar` is **mutable** and **not thread-safe**. For new code, always prefer `java.time.LocalDate` and `java.time.ZonedDateTime`.
