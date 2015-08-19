package jp.t2v.xanadu.ops;


import jp.t2v.xanadu.model.Message;
import lombok.experimental.ExtensionMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtensionMethod(StreamOps.class)
@RunWith(JUnit4.class)
public class StreamOpsTest {

    @Test
    public void flatMapO() {
        List<Message> messages = Arrays.asList(new Message(Optional.of("a")), new Message(Optional.empty()));
        List<String> titles = messages.stream().flatMapO(Message::getTitle).collect(Collectors.toList());
        assertThat(titles, is(Arrays.asList("a")));
    }

    @Test
    public void toList() {
        List<Message> messages = Arrays.asList(new Message(Optional.of("a")), new Message(Optional.empty()));
        List<Message> messages2 = messages.stream().toList();
        assertThat(messages, is(messages2));
    }

    @Test
    public void chunked() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<List<Integer>> result = list.stream().chunked(3).toList();
        assertThat(result, is(Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9),
                Arrays.asList(10))
        ));
    }

}
