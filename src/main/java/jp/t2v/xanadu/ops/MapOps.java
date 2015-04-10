package jp.t2v.xanadu.ops;

import jp.t2v.xanadu.ds.Tuples.*;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class MapOps {

    public <K, V> Optional<V> getOpt(final Map<K, ? extends V> self, K key) {
        return Optional.ofNullable(self.get(key));
    }

    public <K, V> Function<K, Optional<V>> toFunction(final Map<? super K, ? extends V> self) {
        return k -> getOpt(self, k);
    }

    public <K, V> Function<K, V> toUnsafeFunction(final Map<? super K, ? extends V> self) {
        return k -> getOpt(self, k).orElseThrow(NoSuchElementException::new);
    }

    public <K, V> T2<K, V> toTuple(Map.Entry<? extends K, ? extends V> self) {
        return T2.of(self.getKey(), self.getValue());
    }

    private <K, V> Predicate<Map.Entry<K, V>> entryPredicate(final BiPredicate<? super K, ? super V> f) {
        return e -> f.test(e.getKey(), e.getValue());
    }

    public <K1, V1, K2, V2> Map<K2, V2> map(final Map<K1, V1> self, BiFunction<? super K1, ? super V1, T2<? extends K2, ? extends V2>> f) {
        return self.entrySet().stream()
            .map(e -> f.apply(e.getKey(), e.getValue()))
            .collect(Collectors.toMap(t -> t._1, t -> t._2));
    }

    public <K, V> Map<K, V> filter(final Map<? extends K, ? extends V> self, BiPredicate<? super K, ? super V> f) {
        return self.entrySet().stream()
            .filter(entryPredicate(f))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public <K, V1, V2> Map<K, V2> mapValues(final Map<? extends K, ? extends V1> self, final Function<? super V1, ? extends V2> f) {
        return self.entrySet().stream()
            .map(e -> T2.of(e.getKey(), f.apply(e.getValue())))
            .collect(Collectors.toMap(t -> t._1, t -> t._2));
    }

    public <K, V> Map<K, V> filterKeys(final Map<? extends K, ? extends V> self, final Predicate<? super K> f) {
        return self.entrySet().stream()
            .filter(e -> f.test(e.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public <K, V> boolean allMatch(final Map<K, V> self, final BiPredicate<? super K, ? super V> p) {
        return self.entrySet().stream().allMatch(entryPredicate(p));
    }

    public <K, V> boolean anyMatch(final Map<K, V> self, final BiPredicate<? super K, ? super V> p) {
        return self.entrySet().stream().anyMatch(entryPredicate(p));
    }


}
