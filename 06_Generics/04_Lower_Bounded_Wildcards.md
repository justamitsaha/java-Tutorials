# Lower Bounded Wildcards

Lower bounded wildcards are denoted by `<? super T>`, where `T` is a specific type  and we can pass any super class of T. Here we can add item also. Reason this is not widening but narrowing operation. So all parent will have When I say ? super Developers. The element will be a subset of Developer

```java
public class Test {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee());
        print(list);
    }

    /*
    All parents allowed
     */
    static void print(List<? super Developer> list){
        for (Object employee:list){
            System.out.println(employee);
        }
    }
}

```
