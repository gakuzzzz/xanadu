package jp.t2v.xanadu.ops;

import lombok.experimental.UtilityClass;

import java.util.function.BiFunction;
import java.util.function.Function;

@UtilityClass
public class FunctionOps {

    public <A, B, C> Function<A, C> map(final Function<? super A, ? extends B> self, final Function<? super B, ? extends C> f) {
        return a -> f.apply(self.apply(a));
    }

    public <A, B, C> Function<A, C> flatMap(final Function<? super A, ? extends B> self, final Function<? super B, ? extends Function<? super A, ? extends C>> f) {
        return a -> f.apply(self.apply(a)).apply(a);
    }

    public <A, B, C, D> Function<A, D> map2(final Function<? super A, ? extends B> self, final Function<? super A, ? extends C> other, final BiFunction<? super B, ? super C, ? extends D> f) {
        return a -> f.apply(self.apply(a), other.apply(a));
    }

}
