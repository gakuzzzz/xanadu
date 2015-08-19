package jp.t2v.xanadu.ops;

import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class IterableOps {

    @SuppressWarnings("unchecked")
    public <A> Stream<A> stream(final Iterable<? extends A> self) {
        return StreamSupport.stream((Spliterator<A>) self.spliterator(), false);
    }

    public <A> List<A> sortWith(final Iterable<? extends A> self, final Comparator<? super A> c) {
        return stream(self).sorted(c).collect(Collectors.toList());
    }

    public <A extends Comparable<? super A>> List<A> sort(final Iterable<? extends A> self) {
        return sortWith(self, Comparator.naturalOrder());
    }

    public <A, B extends Comparable<? super B>> List<A> sortBy(final Iterable<? extends A> self, final Function<? super A, ? extends B> f) {
        return sortWith(self, Comparator.comparing(f));
    }

}
