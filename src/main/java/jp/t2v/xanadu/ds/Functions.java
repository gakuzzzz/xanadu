package jp.t2v.xanadu.ds;

import lombok.experimental.UtilityClass;

import java.util.function.Function;

import static jp.t2v.xanadu.ds.Tuples.*;

@UtilityClass
public class Functions {

    @FunctionalInterface
    public static interface F3<A, B, C, R> {

        R apply(final A a, final B b, final C c);

        default R applyT(final T3<? extends A, ? extends B, ? extends C> t) {
            return apply(t._1, t._2, t._3);
        }

        default Function<T3<A, B, C>, R> tupled() {
            return this::applyT;
        }

        default <V> F3<A, B, C, V> map(final Function<? super R, ? extends V> f) {
            return (a, b, c) -> f.apply(apply(a, b, c));
        }

        default <V> F3<A, B, C, V> andThen(final Function<? super R, ? extends V> f) {
            return map(f);
        }

        default <V> F3<A, B, C, V> flatMap(final Function<? super R, ? extends F3<? super A, ? super B, ? super C, ? extends V>> f) {
            return (a, b, c) -> f.apply(apply(a, b, c)).apply(a, b, c);
        }

        default <V> Function<V, R> compose(final Function<? super V, ? extends T3<? extends A, ? extends B, ? extends C>> f) {
            return v -> applyT(f.apply(v));
        }

        default Function<A, Function<B, Function<C, R>>> curried() {
            return a -> b -> c -> apply(a, b, c);
        }

    }

}
