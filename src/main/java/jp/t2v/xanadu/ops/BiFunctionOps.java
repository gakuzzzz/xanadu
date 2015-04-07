package jp.t2v.xanadu.ops;

import lombok.experimental.UtilityClass;

import java.util.function.BiFunction;
import java.util.function.Function;
import static jp.t2v.xanadu.ds.Tuples.*;

@UtilityClass
public class BiFunctionOps {

    public <A, B, R> R applyT(final BiFunction<? super A, ? super B, ? extends R> self, final T2<? extends A, ? extends B> t) {
        return self.apply(t._1, t._2);
    }

    public <A, B, R> Function<T2<A, B>, R> tupled(final BiFunction<? super A, ? super B, ? extends R> self) {
        return t -> applyT(self, t);
    }

    public <A, B, R, V> BiFunction<A, B, V> map(final BiFunction<? super A, ? super B, ? extends R> self, final Function<? super R, ? extends V> f) {
        return (a, b) -> f.apply(self.apply(a, b));
    }

    public <A, B, R, V> BiFunction<A, B, V> flatMap(final BiFunction<? super A, ? super B, ? extends R> self, final Function<? super R, ? extends BiFunction<? super A, ? super B, ? extends V>> f) {
        return (a, b) -> f.apply(self.apply(a, b)).apply(a, b);
    }

    public <A, B, R, V> Function<V, R> compose(final BiFunction<? super A, ? super B, ? extends R> self, final Function<? super V, ? extends T2<? extends A, ? extends B>> f) {
        return v -> applyT(self, f.apply(v));
    }

    public <A, B, R> Function<A, Function<B, R>> curried(final BiFunction<? super A, ? super B, ? extends R> self) {
        return a -> b -> self.apply(a, b);
    }

}
