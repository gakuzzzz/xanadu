package jp.t2v.xanadu;

import jp.t2v.xanadu.ds.Tuples;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class Xanadu {

    // =============================================================================================
    // java.util.List
    // =============================================================================================
    public <A, B, C extends Iterable<? extends B>> List<B> flatMap(final List<A> self, final Function<? super A, C> f) {
        return toList(flatMapI(self.stream(), f));
    }

    public <A, B> List<B> flatMapO(final List<A> self, final Function<? super A, Optional<? extends B>> f) {
        return toList(flatMapO(self.stream(), f));
    }

    public <A, B> List<B> map(final List<A> self, final Function<? super A, ? extends B> f) {
        return toList(self.stream().map(f));
    }

    public <A, B, C> List<C> map2(final List<A> self, final Iterable<B> other, final BiFunction<? super A, ? super B, ? extends C> f) {
        return toList(map2I(self.stream(), other, f));
    }

    public <A, B extends Iterable<? extends A>> List<A> flatten(final List<B> self) {
        return flatMap(self, Function.identity());
    }

    public <A> boolean allMatch(final List<A> self, final Predicate<? super A> p) {
        return self.stream().allMatch(p);
    }

    public <A> boolean anyMatch(final List<A> self, final Predicate<? super A> p) {
        return self.stream().anyMatch(p);
    }

    @SuppressWarnings("unchecked")
    public <A> Optional<A> maxBy(final List<? extends A> self, final Comparator<? super A> c) {
        return (Optional<A>) self.stream().max(c);
    }

    @SuppressWarnings("unchecked")
    public <A> Optional<A> minBy(final List<? extends A> self, final Comparator<? super A> c) {
        return (Optional<A>) self.stream().min(c);
    }

    public <A extends Comparable<? super A>> Optional<A> max(final List<? extends A> self) {
        return maxBy(self, Comparator.naturalOrder());
    }

    public <A extends Comparable<? super A>> Optional<A> min(final List<? extends A> self) {
        return minBy(self, Comparator.naturalOrder());
    }

    public <A, B> B foldLeft(final List<A> self, B init, BiFunction<? super B, ? super A, ? extends B> f) {
        B sentinel = init;
        for (final A a : self) {
            sentinel = f.apply(sentinel, a);
        }
        return sentinel;
    }

    public <A, B extends A> Optional<List<A>> sequenceO(final List<Optional<B>> self) {
        return traverseO(self, Function.identity());
    }

    public <A, B> Optional<List<B>> traverseO(final List<A> self, Function<? super A, Optional<? extends B>> f) {
        return foldLeft(self, Optional.of(new ArrayList<B>()), (acc, e) -> map2(acc, f.apply(e), (xs, x) -> {
            xs.add(x);
            return xs;
        }));
    }


    // =============================================================================================
    //  java.util.Optional
    // =============================================================================================
    @SuppressWarnings("unchecked")
    public <A> Optional<A> or(final Optional<? extends A> self, final Optional<? extends A> other) {
        return (Optional<A>) (self.isPresent() ? self : other);
    }

    @SuppressWarnings("unchecked")
    public <A> Optional<A> orGet(final Optional<? extends A> self, final Supplier<Optional<? extends A>> other) {
        return (Optional<A>) (self.isPresent() ? self : other.get());
    }

    public <T> boolean isEmpty(final Optional<T> self) {
        return !self.isPresent();
    }

    public <T> void ifEmpty(final Optional<T> self, final Runnable procedure) {
        if (isEmpty(self)) procedure.run();
    }

    public <A, B> B fold(final Optional<A> self, final Supplier<? extends B> absent, final Function<? super A, ? extends B> present) {
        return self.map(present).orElseGet(absent);
    }

    public <T> Stream<T> stream(final Optional<? extends T> self) {
        return fold(self, Stream::empty, Stream::of);
    }

    public <A> boolean allMatch(final Optional<A> self, final Predicate<? super A> p) {
        return stream(self).allMatch(p);
    }

    public <A> boolean anyMatch(final Optional<A> self, final Predicate<? super A> p) {
        return stream(self).anyMatch(p);
    }

    public <A, B, C> Optional<C> map2(final Optional<A> self, final Optional<B> other, final BiFunction<? super A, ? super B, ? extends C> f) {
        return self.flatMap(a -> other.map(b -> f.apply(a, b)));
    }

    public <A> List<Optional<A>> sequenceL(final Optional<List<? extends A>> self) {
        return traverseL(self, Function.identity());
    }

    public <A, B> List<Optional<B>> traverseL(final Optional<A> self, Function<? super A, ? extends List<? extends B>> f) {
        return fold(self, ArrayList::new, e -> map(f.apply(e), Optional::of));
    }

    @SuppressWarnings("unchecked")
    public <A> Optional<A> flatten(final Optional<Optional<? extends A>> self) {
        return self.flatMap(o -> (Optional<A>) o);
    }

    // =============================================================================================
    //  java.lang.Iterable
    // =============================================================================================
    @SuppressWarnings("unchecked")
    public <A> Stream<A> stream(final Iterable<? extends A> self) {
        return StreamSupport.stream((Spliterator<A>) self.spliterator(), false);
    }


    // =============================================================================================
    //  java.util.stream.Stream
    // =============================================================================================
    public <A, B> Stream<B> flatMapO(final Stream<A> self, Function<? super A, Optional<? extends B>> f) {
        return self.map(f).flatMap(Xanadu::stream);
    }

    public <A, B, C extends Iterable<? extends B>> Stream<B> flatMapI(final Stream<A> self, Function<? super A, C> f) {
        return self.map(f).flatMap(i -> StreamSupport.stream(i.spliterator(), false));
    }

    public <A, B, C> Stream<C> map2I(final Stream<A> self, final Iterable<B> other, final BiFunction<? super A, ? super B, ? extends C> f) {
        return self.flatMap(a -> stream(other).<C>map(b -> f.apply(a, b)));
    }

    public <T, C extends Collection<T>> C toCollection(final Stream<? extends T> self, final Supplier<C> collectionFactory) {
        return self.collect(Collectors.toCollection(collectionFactory));
    }

    public <T> List<T> toList(final Stream<? extends T> self) {
        return toCollection(self, ArrayList::new);
    }


    // =============================================================================================
    //  java.util.function.Function
    // =============================================================================================
    public <A, B, C> Function<A, C> map(final Function<? super A, ? extends B> self, final Function<? super B, ? extends C> f) {
        return a -> f.apply(self.apply(a));
    }

    public <A, B, C> Function<A, C> flatMap(final Function<? super A, ? extends B> self, final Function<? super B, ? extends Function<? super A, ? extends C>> f) {
        return a -> f.apply(self.apply(a)).apply(a);
    }

    public <A, B, C, D> Function<A, D> map2(final Function<? super A, ? extends B> self, final Function<? super A, ? extends C> other, final BiFunction<? super B, ? super C, ? extends D> f) {
        return a -> f.apply(self.apply(a), other.apply(a));
    }


    // =============================================================================================
    //  java.util.function.BiFunction
    // =============================================================================================
    public <A, B, R> R applyT(final BiFunction<? super A, ? super B, ? extends R> self, final Tuples.T2<? extends A, ? extends B> t) {
        return self.apply(t._1, t._2);
    }

    public <A, B, R> Function<Tuples.T2<A, B>, R> tupled(final BiFunction<? super A, ? super B, ? extends R> self) {
        return t -> applyT(self, t);
    }

    public <A, B, R, V> BiFunction<A, B, V> map(final BiFunction<? super A, ? super B, ? extends R> self, final Function<? super R, ? extends V> f) {
        return (a, b) -> f.apply(self.apply(a, b));
    }

    public <A, B, R, V> BiFunction<A, B, V> flatMap(final BiFunction<? super A, ? super B, ? extends R> self, final Function<? super R, ? extends BiFunction<? super A, ? super B, ? extends V>> f) {
        return (a, b) -> f.apply(self.apply(a, b)).apply(a, b);
    }

    public <A, B, R, V> Function<V, R> compose(final BiFunction<? super A, ? super B, ? extends R> self, final Function<? super V, ? extends Tuples.T2<? extends A, ? extends B>> f) {
        return v -> applyT(self, f.apply(v));
    }

    public <A, B, R> Function<A, Function<B, R>> curried(final BiFunction<? super A, ? super B, ? extends R> self) {
        return a -> b -> self.apply(a, b);
    }

    // =============================================================================================
    //
    // =============================================================================================

}
