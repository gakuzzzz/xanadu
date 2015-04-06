package jp.t2v.xanadu.exts;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This class is expected that is is used with @ExtensionMethod.
 */
@UtilityClass
public class ListExtensions {

    @SuppressWarnings("unchecked")
    public <A, B> List<B> flatMap(final List<A> self, final Function<? super A, ? extends Iterable<? extends B>> f) {
        return self.stream()
            .map(f)
            .flatMap(i -> StreamSupport.stream((Spliterator<B>) i.spliterator(), false))
            .collect(Collectors.toList());
    }

    public <A, B> List<B> flatMapO(final List<A> self, final Function<? super A, Optional<? extends B>> f) {
        return StreamExtensions.flatMapO(self.stream(), f).collect(Collectors.toList());
    }

    public <A, B> List<B> map(final List<A> self, final Function<? super A, ? extends B> f) {
        return self.stream().map(f).collect(Collectors.toList());
    }

}
