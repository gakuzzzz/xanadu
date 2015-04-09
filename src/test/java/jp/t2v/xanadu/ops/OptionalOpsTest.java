package jp.t2v.xanadu.ops;


import jp.t2v.xanadu.Xanadu;
import jp.t2v.xanadu.model.Hierarchy.*;
import lombok.experimental.ExtensionMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtensionMethod(Xanadu.class)
@RunWith(JUnit4.class)
public class OptionalOpsTest {

    @Test
    public void when_A_is_Present_A_or_B_is_A() {
        final Optional<String> a = Optional.of("aaa");
        final Optional<String> b = Optional.of("bbb");
        assertThat(a.or(b), is(a));
    }

    @Test
    public void when_A_is_Empty_A_or_B_is_B() {
        final Optional<String> a = Optional.empty();
        final Optional<String> b = Optional.of("bbb");
        assertThat(a.or(b), is(b));
    }

    @Test
    public void when_A_and_B_are_Empty_A_or_B_is_Empty() {
        final Optional<String> a = Optional.empty();
        final Optional<String> b = Optional.empty();
        assertThat(a.or(b), is(Optional.empty()));
    }

    @Test
    public void when_A_is_Present_A_orGet_B_is_A() {
        final Optional<String> a = Optional.of("aaa");
        final Optional<String> b = Optional.of("bbb");
        assertThat(a.orGet(() -> b), is(a));
    }

    @Test
    public void when_A_is_Empty_A_orGet_B_is_B() {
        final Optional<String> a = Optional.empty();
        final Optional<String> b = Optional.of("bbb");
        assertThat(a.orGet(() -> b), is(b));
    }

    @Test
    public void when_A_and_B_are_Empty_A_orGet_B_is_Empty() {
        final Optional<String> a = Optional.empty();
        final Optional<String> b = Optional.empty();
        assertThat(a.orGet(() -> b), is(Optional.empty()));
    }

    @Test
    public void when_A_is_Present_B_is_not_evaluated_in_A_orGet_B() {
        final Optional<String> a = Optional.of("aaa");
        final List<String> mutation = new ArrayList<>();
        a.orGet(() -> { mutation.add("side effect"); return Optional.of("bbb");});
        assertThat(mutation.isEmpty(), is(true));
    }

    @Test
    public void or_hierarchy() {
        final Optional<A> a = Optional.of(new A());
        final Optional<B> b = Optional.of(new B());
        final Optional<C> c = Optional.of(new C());

        final Optional<A> r1 = b.or(c);
        final Optional<B> r2 = b.or(Optional.of(new C()));
        final Optional<A> r3 = b.or(a);
//        final Optional<C> r4 = b.or(Optional.of(new C())); it should be compile error

        assertThat(r1, is(b));
    }

    @Test
    public void Empty_isEmpty_should_return_true() {
        assertThat(Optional.empty().isEmpty(), is(true));
    }

    @Test
    public void Present_isEmpty_should_return_false() {
        assertThat(Optional.of("a").isEmpty(), is(false));
    }

    @Test
    public void Empty_ifEmpty_should_call_procedure() {
        final List<String> mutation = new ArrayList<>();
        Optional.empty().ifEmpty(() -> mutation.add("a"));
        assertThat(mutation, is(Arrays.asList("a")));
    }

    @Test
    public void Present_ifEmpty_should_not_call_procedure() {
        final List<String> mutation = new ArrayList<>();
        Optional.of("v").ifEmpty(() -> mutation.add("a"));
        assertThat(mutation.isEmpty(), is(true));
    }

}
