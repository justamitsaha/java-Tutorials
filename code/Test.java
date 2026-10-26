import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Employee employee = new Employee(50000, 28);
        Response res  = new Response<>();
        res.setResponse(employee);
        res.setCode("200");
        System.out.println(res);
    }
}
class Response<T> {

    private T response;
    private String code;
    public T getResponse() {
        return response;
    }
    public void setResponse(T response) {
        this.response = response;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}

class Employee {
    int salary;
    int age;

    public Employee(int salary, int age) {
        super();
        this.salary = salary;
        this.age = age;
    }
}


