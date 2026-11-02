# Date (`java.util.Date` & `java.sql.Date`)

The `java.util.Date` class represents a specific instant in time with millisecond precision, measured from the Unix epoch: **January 1, 1970, 00:00:00 UTC**. Despite its name, it does not represent a "date" in the calendar sense; it stores a raw 64-bit integer (`long`) of elapsed milliseconds.

---

## 1. Creating and Inspecting `java.util.Date`

The default constructor initializes the object with the current system time via `System.currentTimeMillis()`. The parameterized constructors that take year, month, and day are deprecated due to legacy offset conventions.

```java
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        // Current date and time (wraps System.currentTimeMillis())
        Date now = new Date();
        System.out.println("Current Date: " + now); // Output formatted in JVM local timezone

        // Epoch millisecond timestamp
        long epochMillis = now.getTime();
        System.out.println("Epoch Millis: " + epochMillis);

        // Construct from raw milliseconds
        Date fromMillis = new Date(epochMillis);
        System.out.println("From Millis:  " + fromMillis);

        // Deprecated constructor: Year offset by 1900, 0-indexed month
        @SuppressWarnings("deprecation")
        Date legacy = new Date(124, 11, 18, 14, 30, 0); // 2024-12-18 14:30:00
        System.out.println("Legacy:       " + legacy);
    }
}
```

---

## 2. Mutability & Defensive Copying

`java.util.Date` is **mutable**. Calling `setTime(long)` changes internal state in place. Exposing a `Date` reference directly across class boundaries breaks encapsulation:

two Java concepts:

1.  Mutability: An object can be modified after it has been created.
2.  Defensive copying: Instead of sharing a reference to a mutable object, create and share a separate copy.

### Mutability and Defensive Copying

`java.util.Date` is mutable, meaning its internal state can be changed after creation using methods like `setTime()`.
-   Order: Implements defensive copying in both the constructor (copy on write) and getter (copy on read). This prevents external code from modifying its internal state.
-   Order2: Does not implement defensive copying. It exposes the original `Date` reference, allowing external code to modify its internal state.
Key takeaway: Defensive copying protects encapsulation by preventing external modifications to an object's internal mutable state. `final` alone does not make an object immutable.

```java
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Order order = new Order(new Date());
        Order2 order2 = new Order2(new Date());
        System.out.println("Original creation time: " + order.getCreationDate());
        System.out.println("Original creation time (Order2): " + order2.getCreationDate());

        // Mutating internal state if no defensive copy is performed:
        Date leakedDate = order.getCreationDate();
        Date leakedDate2 = order2.getCreationDate();
        leakedDate.setTime(0L); // Resets to 1970-01-01 00:00:00 UTC!
        leakedDate2.setTime(0L); // Resets to 1970-01-01 00:00:00 UTC!

        // With defensive copying, the internal state remains intact:
        System.out.println("After external mutation: " + order.getCreationDate());
        System.out.println("After external mutation (Order2): " + order2.getCreationDate());
    }
}

class Order {
    private final Date creationDate;

    public Order(Date creationDate) {
        // Defensive copy on write
        this.creationDate = new Date(creationDate.getTime());
    }

    public Date getCreationDate() {
        // Defensive copy on read
        return new Date(this.creationDate.getTime());
    }
}

class Order2 {
    private Date creationDate;

    public Order2(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
```

## 3. Date Arithmetic & Comparison

Legacy date calculations require direct millisecond manipulation. Comparisons are made using `before()`, `after()`, `equals()`, or `compareTo()`:

```java
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Date now = new Date();

        // Add 24 hours in milliseconds (24 * 60 * 60 * 1000 = 86,400,000L)
        Date tomorrow = new Date(now.getTime() + 86_400_000L);

        // Comparison methods
        System.out.println("now.before(tomorrow):    " + now.before(tomorrow));    // true
        System.out.println("now.after(tomorrow):     " + now.after(tomorrow));     // false
        System.out.println("now.compareTo(tomorrow): " + now.compareTo(tomorrow)); // negative integer (< 0)
        System.out.println("now.equals(tomorrow):    " + now.equals(tomorrow));    // false
    }
}
```

---

## 4. `java.sql.Date` vs `java.util.Date`

`java.sql.Date` extends `java.util.Date` and is tailored for JDBC database operations to map to SQL `DATE` columns:

- **Suppressed Time**: `java.sql.Date` zeroes out the hours, minutes, seconds, and milliseconds to midnight in the local timezone for its `toString()` method (`YYYY-MM-DD`).
- **`toInstant()` Incompatibility**: Unlike `java.util.Date`, calling `toInstant()` directly on `java.sql.Date` throws an exception.

```java
import java.sql.Date;
import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        long millis = System.currentTimeMillis();

        java.util.Date utilDate = new java.util.Date(millis);
        java.sql.Date sqlDate = new java.sql.Date(millis);

        System.out.println("util.Date toString(): " + utilDate); // Wed Dec 18 14:30:00 IST 2024
        System.out.println("sql.Date  toString(): " + sqlDate);  // 2024-12-18

        // Modern JDBC bridging: java.sql.Date <-> LocalDate
        LocalDate localDate = sqlDate.toLocalDate();
        Date backToSql = Date.valueOf(localDate);
        System.out.println("Converted to LocalDate: " + localDate);
        System.out.println("Converted back to SQL:  " + backToSql);

        // ❌ Throws UnsupportedOperationException: java.sql.Date does not support toInstant()
        // sqlDate.toInstant();
    }
}
```

---

## 5. Bridging Legacy `Date` with Modern `java.time`

In modern applications, convert legacy `Date` instances to `Instant` or `ZonedDateTime` immediately at API boundaries:

```java
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Date legacyDate = new Date();

        // 1. java.util.Date -> Instant
        Instant instant = legacyDate.toInstant();
        System.out.println("Instant (UTC): " + instant);

        // 2. Instant -> java.util.Date
        Date backToDate = Date.from(instant);
        System.out.println("Back to Date:  " + backToDate);

        // 3. Instant -> LocalDateTime (requires explicit ZoneId)
        LocalDateTime ldt = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println("LocalDateTime: " + ldt);

        // 4. LocalDateTime -> Instant -> Date
        Instant fromLdt = ldt.atZone(ZoneId.systemDefault()).toInstant();
        Date dateFromLdt = Date.from(fromLdt);
        System.out.println("Date from LDT: " + dateFromLdt);
    }
}
```
