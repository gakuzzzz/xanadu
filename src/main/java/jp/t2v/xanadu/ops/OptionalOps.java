package jp.t2v.xanadu.ops;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * This class is expected that is is used with {@link lombok.experimental.ExtensionMethod}.
 */
@UtilityClass
public class OptionalOps {

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
    	Optional<B> map = self.map(present);
        return map.orElseGet(absent);
    }

    public <T> Stream<T> stream(final Optional<? extends T> self) {
		return OptionalOps.fold(self,
				(Supplier<Stream<T>>) Stream::empty,
				(Function<T, Stream<T>>) Stream::of);
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

    @SuppressWarnings("unchecked")
	public <A, B> List<Optional<B>> traverseL(final Optional<A> self, Function<? super A, ? extends List<? extends B>> f) {
		return OptionalOps.<A, List<Optional<B>>>fold(self,
				(Supplier<? extends List<Optional<B>>>)ArrayList::new,
				(Function<? super A, ? extends List<Optional<B>>>) (A e) -> {
		    		return ListOps.<B, Optional<B>>map(
		    				(List<B>)f.apply(e), // SuppressWarnings List<? extends B> to List<B>
		    				(Function<B, Optional<B>>) Optional::of);
		    	});
    }

    @SuppressWarnings("unchecked")
    public <A> Optional<A> flatten(final Optional<Optional<? extends A>> self) {
        return self.flatMap(o -> (Optional<A>) o);
    }

}
