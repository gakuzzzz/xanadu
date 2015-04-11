package jp.t2v.xanadu.ops;


import jp.t2v.xanadu.model.Company;
import jp.t2v.xanadu.model.Employee;
import jp.t2v.xanadu.model.Message;
import lombok.experimental.ExtensionMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import jp.t2v.xanadu.model.Hierarchy.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtensionMethod({OptionalOps.class, ListOps.class, StreamOps.class})
@RunWith(JUnit4.class)
public class ListOpsTest {

    @Test
    public void flatMapO() {
        final List<Message> messages = Arrays.asList(new Message(Optional.of("a")), new Message(Optional.empty()));
        final List<String> titles = messages.flatMapO(Message::getTitle);
        assertThat(titles, is(Arrays.asList("a")));
    }

    @Test
    public void flatMap() {
        final List<Company> companies = Arrays.asList(
            new Company(Arrays.asList(new Employee("a"), new Employee("b"))),
            new Company(Collections.emptyList()),
            new Company(Arrays.asList(new Employee("c")))
        );
        final List<Employee> employees = companies.flatMap(Company::getEmployees);
        assertThat(employees, is(Arrays.asList(new Employee("a"), new Employee("b"), new Employee("c"))));
    }

    @Test
    public void map() {
        final List<String> strings = Arrays.asList("a", "b", "c");
        assertThat(strings.map(String::toUpperCase), is(Arrays.asList("A", "B", "C")));
    }

    @Test
    public void map2() {
        final List<String> strings1 = Arrays.asList("a", "b", "c");
        final List<String> strings2 = Arrays.asList("A", "B", "C");
        assertThat(strings1.map2(strings2, (a, b) -> a + b), is(Arrays.asList("aA", "aB", "aC", "bA", "bB", "bC", "cA", "cB", "cC")));
    }

    @Test
    public void flatten() {
        final C c = new C();
        final List<List<C>> list = Arrays.asList(Arrays.asList(c, c), Collections.emptyList(), Arrays.asList(c, c, c));
        final List<B> result = list.flatten();
        assertThat(result, is(Arrays.asList(c, c, c, c, c)));
    }

    @Test
    public void sequenceO() {
        final B b = new B();
        final List<Optional<B>> list = Arrays.asList(Optional.of(b), Optional.of(b));
        final Optional<List<A>> result = list.sequenceO();
        assertThat(result, is(Optional.of(Arrays.asList(b, b))));
    }

}
