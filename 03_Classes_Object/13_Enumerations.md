# Enumerations

Enumerations are a type in java like class, interface which is used to maintain constants in a specific order. Say we want to store the constants like priority LOW, MEDIUM, HIGH, URGENT and based on the value we want to return SLA in hours. In order to achive this we would have to define constants

```java
public static final int LOW = 7;
public static final int MEDIUM = 5;
public static final int HIGH = 3;
public static final int URGENT = 1;

```

Instead we can use Enums

```java
enum PriorityEnum {
    LOW, MEDIUM, HIGH, URGENT
}

public class Test {

    public static void main(String[] args) {
        System.out.println(Test.getEstimatedCompletionTime(PriorityEnum.LOW));
        System.out.println(Test.getEstimatedCompletionTime(PriorityEnum.MEDIUM));
        System.out.println(Test.getEstimatedCompletionTime(PriorityEnum.HIGH));
        System.out.println(Test.getEstimatedCompletionTime(PriorityEnum.URGENT));
        System.out.println(PriorityEnum.URGENT);
    }

    public static int getEstimatedCompletionTime(PriorityEnum priority) {
        return switch (priority) {
            case LOW -> 7;
            case MEDIUM -> 5;
            case HIGH -> 3;
            case URGENT -> 1;
        };
    }
}

```

We can instantiate Enum like, but can’t create instance, we can assign the existing values. All constants of Enum are by default static and final

```java
PriorityEnum priorityEnum = null;
priorityEnum = PriorityEnum.URGENT;

```

Enum can implement another interface, but it can’t implement another Class because all Enum by default implement Enum class.

Items of enum have an ordinal associated with it based on the count where it is placed. So 1st element is 1st next 2nd and so on. Enum class extended by all enum provide methods to get Enum values and ordinals

```java
enum PriorityEnum implements Serializable {
    LOW, MEDIUM, HIGH, URGENT
}

public class Test {

    public static void main(String[] args) throws ParseException {
        System.out.println(PriorityEnum.URGENT);
        for (PriorityEnum priorityEnum: PriorityEnum.values()){
            System.out.println(priorityEnum.ordinal());     // 1, 2, 3...
            System.out.println(priorityEnum.name());        //LOW, MEDIUM ...
        }
        PriorityEnum medium = PriorityEnum.MEDIUM;
        System.out.println(medium.name()+"<-->"+medium.ordinal());  // Name and Ordinal
        PriorityEnum low = PriorityEnum.valueOf("LOW");
        System.out.println(low);                            //LOW
        PriorityEnum high = PriorityEnum.values() [2];
        System.out.println(high);                           //HIGH
    }
}

```

Although we can’t create object of ENUM but we can  create constructors. The reason is behind the scene JDK during run time creates class for each constants. When we run below code, we can see that the constructor code runs 4 times each for the instance created.

```java
enum PriorityEnum implements Serializable {
    LOW, MEDIUM, HIGH, URGENT;

    PriorityEnum() {
        System.out.println("Hellos" + this.ordinal());  // Will be printed for each ordinal
    }
}

public class Test {

    public static void main(String[] args) throws ParseException {
        PriorityEnum high = PriorityEnum.HIGH;
    }
}

```

We  can associate data with each enum constant by adding instance variables and a constructor to the enum. Here's an example demonstrating how to associate data with enums. Can use any variable name doesn’t have to be abbreviation.

```java
enum Day {
    SUNDAY("Sun"), // Each enum constant can have associated data
    MONDAY("Mon"),
    TUESDAY("Tue"),
    WEDNESDAY("Wed"),
    THURSDAY("Thu"),
    FRIDAY("Fri"),
    SATURDAY("Sat");

    // Instance variable to hold the abbreviation of the day
    private final String abbreviation;

    // Constructor to initialize the abbreviation
    Day(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    // Getter method to retrieve the abbreviation
    public String getAbbreviation() {
        return abbreviation;
    }
}

public class Test {
    public static void main(String[] args) {
        System.out.println("Abbreviations of the days:");
        for (Day day : Day.values()) {
            System.out.println(day + ": " + day.getAbbreviation());
        }
    }
}

```

In this example:

- Each enum constant in the Day enum has an associated abbreviation stored as an instance variable.

- The constructor Day(String abbreviation) initializes the abbreviation for each enum constant.

- The getAbbreviation() method allows accessing the abbreviation associated with each enum constant.

- In the Main class, we iterate through all enum constants using Day.values() and print their names along with their associated abbreviations.

- This approach allows you to associate any type of data with each enum constant, providing flexibility and encapsulation within the enum type.
