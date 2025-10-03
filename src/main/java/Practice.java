package com.example.stunningcode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Practice {


    public static List<Employee2> getSampleEmployees(){
        List<Employee2> l1 = new ArrayList<>();
        l1.add(new Employee2("Hero","ABC",89000.00,12));
        l1.add(new Employee2("Alice", "IT", 120000.00, 34)); // > $70k, > 30
        l1.add(new Employee2("Bob", "IT", 65000.00, 28));   // < $70k, < 30
        l1.add(new Employee2("Charlie", "IT", 105000.00, 45)); // > $70k, > 30
        l1.add(new Employee2("David", "IT", 125000.00, 31)); // Highest salary in IT
        l1.add(new Employee2("Eve", "HR", 72000.00, 22));    // > $70k
        l1.add(new Employee2("Frank", "HR", 48000.00, 50));  // < $70k
        l1.add(new Employee2("Grace", "HR", 55000.00, 35));  // < $70k, > 30
        l1.add(new Employee2("Heidi", "HR", 89000.00, 41));  // Same as your example's salary but HR
        l1.add(new Employee2("Ivan", "Sales", 68000.00, 25)); // < $70k
        l1.add(new Employee2("Judy", "Sales", 88000.00, 38)); // > $70k
        l1.add(new Employee2("Kevin", "Sales", 45000.00, 29)); // Lowest in Sales
        l1.add(new Employee2("Liam", "Engineering", 95000.00, 40));
        l1.add(new Employee2("Mia", "Engineering", 70000.00, 26)); // Boundary case
        l1.add(new Employee2("Noah", "Engineering", 130000.00, 52)); // Overall highest salary
        l1.add(new Employee2("Olivia", "Engineering", 60000.00, 23));
        l1.add(new Employee2("Peter", "Engineering", 52000.00, 33));
        l1.add(new Employee2("Quinn", "Finance", 92000.00, 44));
        l1.add(new Employee2("Ryan", "Finance", 110000.00, 39));
        l1.add(new Employee2("Sara", "Finance", 75000.00, 27));
        l1.add(new Employee2("Alice", "Marketing", 62000.00, 24)); // Duplicate name "Alice"
        return l1;
    }

    public static void main(String [] args){

        List<Employee2> employees = getSampleEmployees();
        //System.out.println("Employees before sorting :");
        //employees.forEach(System.out::println);

        Predicate<Employee2> highIT = (e)-> e.getSalary()>50000 && e.getDepartment().equalsIgnoreCase("IT");
        employees.stream().filter(highIT).forEach(System.out::println);

        Function<Employee2, String> emp = (e)->"Name " + e.getName() + " Department : " + e.getDepartment();
        employees.stream().map(emp).forEach(System.out::println);

        StringManipulation stringManipulation = (s1,s2) -> new StringBuilder(s1+s2).reverse().toString();
        System.out.println(stringManipulation.manipulate("cvv","cdcd"));
        System.out.println("List of >30 : "+employees.stream().filter((e)->e.getAge()>30).count());
        System.out.println("List of Uppercase employees: "+employees.stream().map((e)->e.getName().toUpperCase()).toList());
        System.out.println(employees.stream().map(Employee2::getDepartment).distinct().toList());
        //System.out.println(employees.stream().filter((e1,e2)->employees.sort(e1.getDepartment())));


        employees.sort((e1,e2)->Double.compare(e1.getSalary(), e2.getSalary()));
        //System.out.println("Employees after sorting :");
        //employees.forEach(System.out::println);

        Predicate<Employee2> highPaidIT = (e)-> e.getSalary()>50000 && e.getDepartment().equalsIgnoreCase("IT");
        //employees.stream().filter(highPaidIT).forEach(System.out::println);

        Function<Employee2, String> formatEmployee = e->"Name : "+ e.getName() + ", Department : " + e.getDepartment();
       // employees.stream().map(formatEmployee).forEach(System.out::println);

        StringManipulation str = ((s1, s2) ->new StringBuilder (s1+s2).reverse().toString());
        //System.out.println(str.manipulate("Hello", "World"));

        Predicate<Employee2> age30above = (e)->e.getAge()>30;
        long count = employees.stream().filter(age30above).count();
        //System.out.println(count);






    }

}
@FunctionalInterface
interface StringManipulation{
    String manipulate(String s1, String s2);
}
