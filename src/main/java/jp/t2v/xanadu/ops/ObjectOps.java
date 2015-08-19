package jp.t2v.xanadu.ops;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * This class is expected that is is used with @ExtensionMethod.
 */
@UtilityClass
public class ObjectOps {

    public <A> List<A> liftL(final A self) {
        return Collections.singletonList(self);
    }

    public <A> Optional<A> liftO(final A self) {
        return Optional.ofNullable(self);
    }

    public <A> Stream<A> liftS(final A self) {
        return Stream.of(self);
    }

    public <A> A tap(final A self, Consumer<? super A> f) {
        f.accept(self);
        return self;
    }

}
