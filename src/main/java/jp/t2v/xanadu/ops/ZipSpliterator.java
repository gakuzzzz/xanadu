package jp.t2v.xanadu.ops;


import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;

class ZipSpliterator<A, B, C> extends Spliterators.AbstractSpliterator<C> {
    private final Spliterator<A> a;
    private final Spliterator<B> b;
    private final BiFunction<? super A, ? super B, ? extends C> mapper;
    private boolean bHasNext = true;

    ZipSpliterator(Spliterator<A> a, Spliterator<B> b, BiFunction<? super A, ? super B, ? extends C> mapper) {
        super(Math.min(a.estimateSize(), b.estimateSize()), 0);
        this.a = a;
        this.b = b;
        this.mapper = mapper;
    }

    @Override
    public boolean tryAdvance(Consumer<? super C> consumer) {
        if (bHasNext) {
            boolean aHasNext = a.tryAdvance(elemA -> {
                bHasNext = b.tryAdvance(elemB -> {
                    consumer.accept(mapper.apply(elemA, elemB));
                });
            });
            return aHasNext && bHasNext;
        }
        return false;
    }
}

