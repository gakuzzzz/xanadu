package jp.t2v.xanadu.exts;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is expected that is is used with @ExtensionMethod.
 */
@UtilityClass
public class StreamExtensions {

    public <A, B> Stream<B> flatMapO(final Stream<A> self, Function<? super A, Optional<? extends B>> f) {
        return self.map(f).flatMap(OptionalExtensions::stream);
    }

    public <T, C extends Collection<T>> C toCollection(final Stream<T> self, final Supplier<C> collectionFactory) {
        return self.collect(Collectors.toCollection(collectionFactory));
    }

    public <T> List<T> toList(final Stream<T> self) {
        return toCollection(self, ArrayList::new);
    }

}
