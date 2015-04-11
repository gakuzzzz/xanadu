package jp.t2v.xanadu.ops;

import jp.t2v.xanadu.ds.Tuples.T2;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * This class is expected that is is used with @ExtensionMethod.
 */
@UtilityClass
public class ListOps {

    public <A, B, C extends Iterable<? extends B>> List<B> flatMap(final List<A> self, final Function<? super A, C> f) {
        return StreamOps.flatMapI(self.stream(), f).collect(Collectors.toList());
    }

    public <A, B> List<B> flatMapO(final List<A> self, final Function<? super A, Optional<? extends B>> f) {
        return StreamOps.flatMapO(self.stream(), f).collect(Collectors.toList());
    }

    public <A, B> List<B> map(final List<A> self, final Function<? super A, ? extends B> f) {
        return self.stream().map(f).collect(Collectors.toList());
    }

    public <A, B, C> List<C> map2(final List<A> self, final Iterable<B> other, final BiFunction<? super A, ? super B, ? extends C> f) {
        return StreamOps.map2I(self.stream(), other, f).collect(Collectors.toList());
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
        return foldLeft(self, Optional.of(new ArrayList<B>()), (acc, e) -> OptionalOps.map2(acc, f.apply(e), (xs, x) -> {xs.add(x); return xs;}));
    }

    public <A> T2<List<A>, List<A>> partition(final List<? extends A> self, Predicate<? super A> f) {
        return foldLeft(self, T2.of(new ArrayList<>(), new ArrayList<>()), (t, e) -> {
            (f.test(e) ? t._1 : t._2).add(e);
            return t;
        });
    }

    public <A, B> T2<List<A>, List<B>> unzip(final List<T2<? extends A, ? extends B>> self) {
        return foldLeft(self, T2.of(new ArrayList<>(), new ArrayList<>()), (t, e) -> {
            t._1.add(e._1);
            t._2.add(e._2);
            return t;
        });
    }

    public <A> IntFunction<Optional<A>> toFunction(final List<? extends A> self) {
        return i -> 0 <= i && i < self.size() ? Optional.ofNullable(self.get(i)) : Optional.empty();
    }

}
