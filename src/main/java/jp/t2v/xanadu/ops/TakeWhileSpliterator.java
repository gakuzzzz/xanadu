package jp.t2v.xanadu.ops;


import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;

class TakeWhileSpliterator<T> extends Spliterators.AbstractSpliterator<T> {
    private final Spliterator<T> spliterator;
    private final Predicate<? super T> predicate;
    private boolean running = true;

    public TakeWhileSpliterator(Spliterator<T> spliterator, Predicate<? super T> predicate) {
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
