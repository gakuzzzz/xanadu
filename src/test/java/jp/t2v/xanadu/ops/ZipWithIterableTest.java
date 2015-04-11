package jp.t2v.xanadu.ops;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.experimental.ExtensionMethod;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtensionMethod(StreamOps.class)
public class ZipWithIterableTest {
    @Test
    public void same_size() {
        List<String> self = Arrays.asList("a", "b", "c", "");
        List<Integer> other = Arrays.asList(1, 2, 3, 4);
        List<String> zipped = self.stream().zip(other, (left, right) -> String.format("%s-%s", left, right)).toList();
        assertThat(zipped, is(Arrays.asList("a-1", "b-2", "c-3", "-4")));
    }

    @Test
    public void terminates_when_other_ends() {
        List<String> self = Arrays.asList("a", "b", "c", "", "e");
        List<Integer> other = Arrays.asList(1, 2, 3);
        List<String> zipped = self.stream().zip(other, (left, right) -> String.format("%s-%s", left, right)).toList();
        assertThat(zipped, is(Arrays.asList("a-1", "b-2", "c-3")));
    }

    @Test
    public void terminates_when_self_ends() {
        List<String> self = Arrays.asList("a", "b", "c");
        List<Integer> other = Arrays.asList(1, 2, 3, 4, 5);
        List<String> zipped = self.stream().zip(other, (left, right) -> String.format("%s-%s", left, right)).toList();
        assertThat(zipped, is(Arrays.asList("a-1", "b-2", "c-3")));
    }

    @Test
    public void return_empty_if_self_is_empty() {
        List<String> self = Collections.emptyList();
        List<Integer> other = Arrays.asList(1, 2, 3);
        List<String> zipped = self.stream()
                                  .zip(other, (left, right) -> {
                                      throw new IllegalStateException("never invoked!!");
                                  }).toList();
        assertThat(zipped, is(Collections.emptyList()));
    }

    @Test
    public void return_empty_if_other_is_empty() {
        List<String> self = Arrays.asList("a", "b", "c");
        List<Integer> other = Collections.emptyList();
        List<String> zipped = self.stream()
                                  .zip(other, (left, right) -> {
                                      throw new IllegalStateException("never invoked!!");
                                  }).toList();
        assertThat(zipped, is(Collections.emptyList()));
    }

}
