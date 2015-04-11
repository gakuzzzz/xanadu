package jp.t2v.xanadu.ops;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This class is expected that is is used with @ExtensionMethod.
 */
@UtilityClass
public class StreamOps {

    public <A, B> Stream<B> flatMapO(final Stream<A> self, Function<? super A, Optional<? extends B>> f) {
        return self.map(f).flatMap(OptionalOps::stream);
    }

    public <A, B, C extends Iterable<? extends B>> Stream<B> flatMapI(final Stream<A> self, Function<? super A, C> f) {
        return self.map(f).flatMap(i -> StreamSupport.stream(i.spliterator(), false));
    }

    public <A, B, C> Stream<C> map2I(final Stream<A> self, final Iterable<B> other, final BiFunction<? super A, ? super B, ? extends C> f) {
        return self.flatMap(a -> IterableOps.stream(other).<C>map(b -> f.apply(a, b)));
    }

    public <T, C extends Collection<T>> C toCollection(final Stream<? extends T> self, final Supplier<C> collectionFactory) {
        return self.collect(Collectors.toCollection(collectionFactory));
    }

    public <T> List<T> toList(final Stream<? extends T> self) {
        return toCollection(self, ArrayList::new);
    }

    public <T> Stream<T> takeWhile(final Stream<T> self, Predicate<? super T> predicate) {
        return StreamSupport.stream(new TakeWhileSpliterator<>(self.spliterator(), predicate), false);
    }

    public <A, B, C> Stream<C> zip(final Stream<A> self, final Stream<B> other, final BiFunction<? super A, ? super B, ? extends C> f) {
        return StreamSupport.stream(new ZipSpliterator<>(self.spliterator(), other.spliterator(), f), false);
    }

    public <A, B, C> Stream<C> zip(final Stream<A> self, final Iterable<B> other, final BiFunction<? super A, ? super B, ? extends C> f) {
        return StreamSupport.stream(new ZipSpliterator<>(self.spliterator(), other.spliterator(), f), false);
    }

}
