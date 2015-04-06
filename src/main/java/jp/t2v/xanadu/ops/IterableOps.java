package jp.t2v.xanadu.ops;

import lombok.experimental.UtilityClass;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class IterableOps {

    public <A> Stream<A> stream(final Iterable<A> self) {
        return StreamSupport.stream(self.spliterator(), false);
    }

}
