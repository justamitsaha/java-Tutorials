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