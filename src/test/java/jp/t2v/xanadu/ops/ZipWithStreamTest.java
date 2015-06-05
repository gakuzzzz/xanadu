package jp.t2v.xanadu.ops;

import lombok.experimental.ExtensionMethod;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtensionMethod(StreamOps.class)
public class ZipWithStreamTest {
    @Test
    public void same_size() {
        List<String> self = Arrays.asList("a", "b", "c", "");
        Stream<Integer> other = Arrays.asList(1, 2, 3, 4).stream();
        List<String> zipped = self.stream().zipWith(other, (left, right) -> String.format("%s-%s", left, right)).toList();
        assertThat(zipped, is(Arrays.asList("a-1", "b-2", "c-3", "-4")));
    }

    @Test
    public void terminates_when_other_ends() {
        List<String> self = Arrays.asList("a", "b", "c", "", "e");
        Stream<Integer> other = Arrays.asList(1, 2, 3).stream();
        List<String> zipped = self.stream().zipWith(other, (left, right) -> String.format("%s-%s", left, right)).toList();
        assertThat(zipped, is(Arrays.asList("a-1", "b-2", "c-3")));
    }

    @Test
    public void terminates_when_self_ends() {
        List<String> self = Arrays.asList("a", "b", "c");
        Stream<Integer> other = Arrays.asList(1, 2, 3, 4, 5).stream();
        List<String> zipped = self.stream().zipWith(other, (left, right) -> String.format("%s-%s", left, right)).toList();
        assertThat(zipped, is(Arrays.asList("a-1", "b-2", "c-3")));
    }

    @Test
    public void return_empty_if_self_is_empty() {
        List<String> self = Collections.emptyList();
        Stream<Integer> other = Arrays.asList(1, 2, 3).stream();
        List<String> zipped = self.stream()
                                  .zipWith(other, (left, right) -> {
                                      throw new IllegalStateException("never invoked!!");
                                  }).toList();
        assertThat(zipped, is(Collections.emptyList()));
    }

    @Test
    public void return_empty_if_other_is_empty() {
        List<String> self = Arrays.asList("a", "b", "c");
        Stream<Integer> other = Stream.empty();
        List<String> zipped = self.stream()
                                  .zipWith(other, (left, right) -> {
                                      throw new IllegalStateException("never invoked!!");
                                  }).toList();
        assertThat(zipped, is(Collections.emptyList()));
    }

}
