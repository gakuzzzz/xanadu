package jp.t2v.xanadu.ops;

import lombok.experimental.UtilityClass;

import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class IterableOps {

    @SuppressWarnings("unchecked")
    public <A> Stream<A> stream(final Iterable<? extends A> self) {
        return StreamSupport.stream((Spliterator<A>) self.spliterator(), false);
    }

}
