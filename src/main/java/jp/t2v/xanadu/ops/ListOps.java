package jp.t2v.xanadu.ops;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class is expected that is is used with @ExtensionMethod.
 */
@UtilityClass
public class ListOps {

    @SuppressWarnings("unchecked")
    public <A, B> List<B> flatMap(final List<A> self, final Function<? super A, ? extends Iterable<? extends B>> f) {
        return StreamOps.flatMapI(self.stream(), f).collect(Collectors.toList());
    }

    public <A, B> List<B> flatMapO(final List<A> self, final Function<? super A, Optional<? extends B>> f) {
        return StreamOps.flatMapO(self.stream(), f).collect(Collectors.toList());
    }

    public <A, B> List<B> map(final List<A> self, final Function<? super A, ? extends B> f) {
        return self.stream().map(f).collect(Collectors.toList());
    }

}
