# Instant and Duration (`java.time.Instant` & `java.time.Duration`)

While classes like `LocalDate` represent human-centric dates, machines track time as a continuous timeline of nanoseconds measured from the **Unix Epoch** (January 1, 1970, 00:00:00 UTC).

![image53.png](../images/image53.png)

---

## 1. `Instant` (Machine Timestamp)

An `Instant` represents a specific point on the machine timeline in UTC with nanosecond precision.

```java
import java.time.Instant;

// Current UTC timestamp
Instant now = Instant.now();
System.out.println(now); 
// e.g., 2024-12-18T08:05:30.123456789Z

// Creating from epoch seconds / milliseconds
Instant fromSec = Instant.ofEpochSecond(1000);
Instant fromMilli = Instant.ofEpochMilli(System.currentTimeMillis());

// Extracting components:
long epochSeconds = now.getEpochSecond(); // Total whole seconds from 1970-01-01T00:00:00Z
int nanosOfSecond = now.getNano();        // Fractional nanoseconds within the second (0 - 999,999,999)

System.out.println("Epoch seconds: " + epochSeconds);
System.out.println("Nanos of second: " + nanosOfSecond);
```

> [!NOTE]
> `getNano()` does **not** return the time since the object was created or total nanos from epoch; it returns the sub-second fractional nanosecond portion (`0` to `999,999,999`).

---

## 2. `Duration` (Time-Based Amount)

`Duration` represents an amount of time measured in **seconds and nanoseconds** (time-based). It is used for fine-grained differences between two points in time (`Instant`, `LocalTime`, `LocalDateTime`).

![image54.png](../images/image54.png)

### Creating Durations

```java
import java.time.Duration;

Duration fiveDays    = Duration.ofDays(5);
Duration tenMinutes = Duration.ofMinutes(10);
Duration thirtySecs  = Duration.ofSeconds(30);
Duration negativeSec = Duration.ofSeconds(-13);
```

### Measuring Difference Between Instants

```java
import java.time.Duration;
import java.time.Instant;

Instant start = Instant.now();
// simulate task
Instant end = start.plus(Duration.ofMinutes(120));

Duration diff = Duration.between(start, end);
System.out.println(diff); // PT2H (ISO-8601 format: Period of Time 2 Hours)

System.out.println("Total seconds: " + diff.toSeconds()); // 7200
System.out.println("Total minutes: " + diff.toMinutes()); // 120
System.out.println("Total hours:   " + diff.toHours());   // 2
```

---

## 3. Arithmetic with `Instant` and `Duration`

```java
import java.time.Duration;
import java.time.Instant;

Instant timestamp = Instant.now();

// Adding and subtracting duration
Instant future = timestamp.plus(Duration.ofDays(3));
Instant past   = timestamp.minus(Duration.ofHours(5));

// Comparing instants
System.out.println(future.isAfter(past));  // true
System.out.println(past.isBefore(future)); // true
```

---

## 4. Scaling and Truncating `Duration`

```java
import java.time.Duration;
import java.time.temporal.ChronoUnit;

Duration base = Duration.ofMinutes(30); // PT30M

// Scaling methods
Duration tripled = base.multipliedBy(3); // PT1H30M
Duration halved  = base.dividedBy(2);    // PT15M
Duration negated = base.negated();       // PT-30M

// Truncating precision
Duration complex = Duration.ofDays(1)
        .plusHours(6)
        .plusMinutes(45)
        .plusSeconds(30); // PT30H45M30S

Duration truncatedHours   = complex.truncatedTo(ChronoUnit.HOURS);   // PT30H
Duration truncatedMinutes = complex.truncatedTo(ChronoUnit.MINUTES); // PT30H45M
```
