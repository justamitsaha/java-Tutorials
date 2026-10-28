# Date (`java.util.Date` & `java.sql.Date`)

The `java.util.Date` class represents a specific instant in time with millisecond precision, measured from the Unix epoch: **January 1, 1970, 00:00:00 UTC**.

---

## 1. Creating and Inspecting `java.util.Date`

The default constructor initializes the object with the current system time via `System.currentTimeMillis()`:

```java
import java.util.Date;

public class DateExample {
    public static void main(String[] args) {
        // Current date and time
        Date currentDate = new Date();
        System.out.println(currentDate); 
        // e.g., Wed Dec 18 12:27:45 IST 2024

        // Milliseconds since Unix epoch (Jan 1, 1970 UTC)
        long epochMillis = currentDate.getTime();
        System.out.println(epochMillis); 
        // e.g., 1734505065137
    }
}
```

---

## 2. Deprecated Constructors & Legacy Quirks

Before Java 1.1 introduced `Calendar`, specific dates were constructed using year, month, and day. These constructors are deprecated due to confusing conventions:

```java
// Year is offset by 1900 (2024 - 1900 = 124)
// Month is 0-indexed (0 = Jan, 11 = Dec)
Date specificDate = new Date(124, 11, 18); // 18th December 2024, 00:00:00 local time
System.out.println(specificDate);
```

---

## 3. Date Arithmetic & Comparison

Because `Date` stores raw milliseconds, legacy calculations often manipulated timestamps directly:

```java
Date date = new Date();

// Add 1 day in milliseconds (24 * 60 * 60 * 1000 = 86,400,000 ms)
Date nextDay = new Date(date.getTime() + 86_400_000L);

// Comparison methods
System.out.println(date.before(nextDay)); // true
System.out.println(date.after(nextDay));  // false
System.out.println(date.compareTo(nextDay)); // negative integer (< 0)
```

> [!WARNING]
> `java.util.Date` is **mutable**. Methods like `setTime(long)` alter internal state in place, making instances dangerous to pass across threads or expose through getters without defensive copying.

---

## 4. `java.sql.Date` vs `java.util.Date`

`java.sql.Date` extends `java.util.Date` and is tailored for JDBC database operations:

- **Purpose**: Maps to the SQL `DATE` data type, which stores year, month, and day without time of day.
- **Normalization**: The time component is zeroed out to midnight in the local timezone.

```java
long millis = System.currentTimeMillis();

java.util.Date utilDate = new java.util.Date(millis);
java.sql.Date sqlDate = new java.sql.Date(millis);

System.out.println("util.Date : " + utilDate); // Wed Dec 18 12:27:45 IST 2024
System.out.println("sql.Date  : " + sqlDate);  // 2024-12-18 (formatted as yyyy-mm-dd)
```

---

## 5. Bridging Legacy `Date` with Modern `java.time`

In modern Java applications, convert legacy `Date` objects to modern types immediately at API boundaries:

```java
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

// 1. Date to Instant
Date legacyDate = new Date();
Instant instant = legacyDate.toInstant();

// 2. Instant to Date
Date backToDate = Date.from(instant);

// 3. java.sql.Date to LocalDate (and vice versa)
java.sql.Date sqlDate = java.sql.Date.valueOf("2024-12-18");
LocalDate localDate = sqlDate.toLocalDate();
java.sql.Date backToSql = java.sql.Date.valueOf(localDate);
```
