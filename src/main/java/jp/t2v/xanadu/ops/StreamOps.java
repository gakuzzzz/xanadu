package jp.t2v.xanadu.ops;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This class is expected that is is used with @ExtensionMethod.
 */
@UtilityClass
public class StreamOps {

    @SuppressWarnings("unchecked")
    public <A, B> Stream<B> flatMapO(final Stream<A> self, Function<? super A, Optional<? extends B>> f) {
        return self.map(f).flatMap(OptionalOps::stream);
    }

    @SuppressWarnings("unchecked")
    public <A, B> Stream<B> flatMapI(final Stream<A> self, Function<? super A, ? extends Iterable<? extends B>> f) {
        return self.map(f).flatMap(i -> StreamSupport.stream((Spliterator<B>) i.spliterator(), false));
    }

    public <T, C extends Collection<T>> C toCollection(final Stream<T> self, final Supplier<C> collectionFactory) {
        return self.collect(Collectors.toCollection(collectionFactory));
    }

    public <T> List<T> toList(final Stream<T> self) {
        return toCollection(self, ArrayList::new);
    }

}
