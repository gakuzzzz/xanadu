package jp.t2v.xanadu.ops;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class is expected that is is used with @ExtensionMethod.
 *
 * <pre><code>
 * @ExtensionMethod(OptionalOps.class)
 * public class Foo {
 *
 *   public void main() {
 *       Optional&lt;String&gt; a = Optional.of("aaa");
 *       Optional&lt;String&gt; b = Optional.of("bbb");
 *
 *       Optional&lt;String&gt; result = a.or(b);
 *   }
 *
 * }
 * </code></pre>
 */
@UtilityClass
public class OptionalOps {

    @SuppressWarnings("unchecked")
    public <T> Optional<T> or(final Optional<T> self, final Optional<? extends T> other) {
        return self.isPresent() ? self : (Optional<T>) other;
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> orGet(final Optional<T> self, final Supplier<Optional<? extends T>> other) {
        return self.isPresent() ? self : (Optional<T>) other.get();
    }

    public <T> boolean isAbsent(final Optional<T> self) {
        return !self.isPresent();
    }

    public <T> void ifAbsent(final Optional<T> self, final Runnable procedure) {
        if (isAbsent(self)) procedure.run();
    }

    public <A, B> B fold(final Optional<A> self, final Supplier<? extends B> absent, final Function<? super A, ? extends B> present) {
        return self.map(present).orElseGet(absent);
    }

    public <T> Stream<T> stream(final Optional<T> self) {
        return fold(self, Stream::empty, Stream::of);
    }

}
