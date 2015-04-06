package jp.t2v.xanadu.exts;


import lombok.experimental.ExtensionMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtensionMethod(OptionalExtensions.class)
@RunWith(JUnit4.class)
public class OptionalExtensionsTest {

    @Test
    public void when_A_is_Present_A_or_B_is_A() {
        Optional<String> a = Optional.of("aaa");
        Optional<String> b = Optional.of("bbb");
        assertThat(a.or(b), is(a));
    }

    @Test
    public void when_A_is_Absent_A_or_B_is_B() {
        Optional<String> a = Optional.empty();
        Optional<String> b = Optional.of("bbb");
        assertThat(a.or(b), is(b));
    }

    @Test
    public void when_A_and_B_are_Absent_A_or_B_is_Absent() {
        Optional<String> a = Optional.empty();
        Optional<String> b = Optional.empty();
        assertThat(a.or(b), is(Optional.empty()));
    }

    @Test
    public void when_A_is_Present_A_orGet_B_is_A() {
        Optional<String> a = Optional.of("aaa");
        Optional<String> b = Optional.of("bbb");
        assertThat(a.orGet(() -> b), is(a));
    }

    @Test
    public void when_A_is_Absent_A_orGet_B_is_B() {
        Optional<String> a = Optional.empty();
        Optional<String> b = Optional.of("bbb");
        assertThat(a.orGet(() -> b), is(b));
    }

    @Test
    public void when_A_and_B_are_Absent_A_orGet_B_is_Absent() {
        Optional<String> a = Optional.empty();
        Optional<String> b = Optional.empty();
        assertThat(a.orGet(() -> b), is(Optional.empty()));
    }

    @Test
    public void when_A_is_Present_B_is_not_evaluated_in_A_orGet_B() {
        Optional<String> a = Optional.of("aaa");
        List<String> mutation = new ArrayList<>();
        a.orGet(() -> { mutation.add("side effect"); return Optional.of("bbb");});
        assertThat(mutation.isEmpty(), is(true));
    }

}
