package jp.t2v.xanadu.ops;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

class Spliterators {

    static class TakeWhileSpliterator<T> extends AbstractSpliterator<T> {
        private final Spliterator<? extends T> spliterator;
        private final Predicate<? super T> predicate;
        private boolean running = true;

        public TakeWhileSpliterator(Spliterator<? extends T> spliterator, Predicate<? super T> predicate) {
            super(spliterator.estimateSize(), 0);
            this.spliterator = spliterator;
            this.predicate = predicate;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> consumer) {
            if (running) {
                boolean hasNext = spliterator.tryAdvance(elem -> {
                    if (predicate.test(elem)) {
                        consumer.accept(elem);
                    } else {
                        running = false;
                    }
                });
                return hasNext && running;
            }
            return false;
        }
    }


    static class ZipSpliterator<A, B, C> extends AbstractSpliterator<C> {
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

    static class ChunkedSpliterator<E> extends AbstractSpliterator<List<E>>
    {
        private final Spliterator<E> spliterator;
        private final int partitionSize;

        public ChunkedSpliterator(Spliterator<E> toWrap, int partitionSize) {
            super(toWrap.estimateSize(), toWrap.characteristics() | Spliterator.NONNULL);
            if (partitionSize <= 0) throw new IllegalArgumentException(
                    "Partition size must be positive, but was " + partitionSize);
            this.spliterator = toWrap;
            this.partitionSize = partitionSize;
        }

        @Override public boolean tryAdvance(Consumer<? super List<E>> action) {
            final List<E> partition = new ArrayList<>(partitionSize);
            while (spliterator.tryAdvance(partition::add)
                    && partition.size() < partitionSize);
            if (partition.isEmpty()) return false;
            action.accept(partition);
            return true;
        }

        @Override public long estimateSize() {
            final long est = spliterator.estimateSize();
            return est == Long.MAX_VALUE? est
                    : est / partitionSize + (est % partitionSize > 0? 1 : 0);
        }
    }


}
