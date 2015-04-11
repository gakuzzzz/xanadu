package jp.t2v.xanadu.ops;


import lombok.experimental.ExtensionMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtensionMethod(StreamOps.class)
@RunWith(JUnit4.class)
public class TakeWhileTest {
    @Test
    public void takeWhile() {
        List<String> given = Arrays.asList("a", "b", "c", "", "e");
        List<String> nonEmpties = given.stream().takeWhile(s -> !s.isEmpty()).toList();
        assertThat(nonEmpties, is(Arrays.asList("a", "b", "c")));
    }

    @Test
    public void returns_empty_sequence_if_none_satisfied() {
        List<String> given = Arrays.asList("a", "b", "c");
        List<String> empty = given.stream().takeWhile(s -> s.isEmpty()).toList();
        assertThat(empty, is(Collections.emptyList()));
    }

    @Test
    public void returns_empty_sequence_if_self_is_empty() {
        List<String> given = Collections.emptyList();
        List<String> empty = given.stream()
                                  .takeWhile(s -> {throw new IllegalStateException("never invoked!!");})
                                  .toList();
        assertThat(empty, is(Collections.emptyList()));
    }

}
