package jp.t2v.xanadu.exts;


import lombok.Value;
import lombok.experimental.ExtensionMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtensionMethod(ListExtensions.class)
@RunWith(JUnit4.class)
public class ListExtensionsTest {

    @Value
    public static class Message {
        Optional<String> title;
    }

    @Test
    public void flatMapO() {
        List<Message> messages = Arrays.asList(new Message(Optional.of("a")), new Message(Optional.empty()));
        List<String> titles = messages.flatMapO(Message::getTitle);
        assertThat(titles, is(Arrays.asList("a")));
    }

    @Value
    public static class Employee {
        String name;
    }

    @Value
    public static class Company {
        List<Employee> employees;
    }

    @Test
    public void flatMap() {
        List<Company> companies = Arrays.asList(new Company(Arrays.asList(new Employee("a"), new Employee("b"))), new Company(Collections.emptyList()), new Company(Arrays.asList(new Employee("c"))));
        List<Employee> employees = companies.flatMap(Company::getEmployees);
        assertThat(employees, is(Arrays.asList(new Employee("a"), new Employee("b"), new Employee("c"))));
    }

}
