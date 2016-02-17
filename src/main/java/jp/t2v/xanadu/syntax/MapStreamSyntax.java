package jp.t2v.xanadu.syntax;

import lombok.experimental.UtilityClass;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@UtilityClass
public class MapStreamSyntax {

    private static final class ImmutableEntry<K, V> implements Map.Entry<K, V>, Serializable {
        private final K key;
        private final V value;

        private ImmutableEntry(final K key, final V value) {
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }
        @Override
        public V getValue() {
            return value;
        }
        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException("immutable");
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Map.Entry)) return false;
            final Map.Entry<?, ?> e2 = (Map.Entry<?, ?>) obj;
            return  getKey().equals(e2.getKey()) && getValue().equals(e2.getValue());
        }

        @Override
        public int hashCode() {
            return (getKey() == null   ? 0 : getKey().hashCode()) ^
                   (getValue() == null ? 0 : getValue().hashCode());
        }

        @Override
        public String toString() {
            return String.format("Entry[%s: %s]", key, value);
        }

        public static <KK, VV> ImmutableEntry<KK, VV> of(final KK key, final VV value) {
            return new ImmutableEntry<>(key, value);
        }

        public static <KK, VV> ImmutableEntry<KK, VV> fromEntry(final Map.Entry<? extends KK, ? extends VV> base) {
            return of(base.getKey(), base.getValue());
        }
    }

    public <E, K, V> Function<E, Map.Entry<K, V>> toEntry(final Function<? super E, ? extends K> ketFactory, final Function<? super E, ? extends V> valueFactory) {
        return e -> ImmutableEntry.of(ketFactory.apply(e), valueFactory.apply(e));
    }

    public <K1, V1, R> Function<Map.Entry<K1, V1>, R> toAny(final BiFunction<? super K1, ? super V1, ? extends R> f) {
        return e -> f.apply(e.getKey(), e.getValue());
    }

    public <K1, K2, V> Function<Map.Entry<K1, V>, Map.Entry<K2, V>> keys(final Function<? super K1, ? extends K2> f) {
        return e -> ImmutableEntry.of(f.apply(e.getKey()), e.getValue());
    }

    public <K, V, R> Function<Map.Entry<K, V>, R> keyToAny(final Function<? super K, ? extends R> f) {
        return e -> f.apply(e.getKey());
    }

    public <K, V> Predicate<Map.Entry<K, V>> byKey(final Predicate<? super K> f) {
        return e -> f.test(e.getKey());
    }

    public <K, V1, V2> Function<Map.Entry<K, V1>, Map.Entry<K, V2>> values(final Function<? super V1, ? extends V2> f) {
        return e -> ImmutableEntry.of(e.getKey(), f.apply(e.getValue()));
    }

    public <K, V, R> Function<Map.Entry<K, V>, R> valueToAny(final Function<? super V,  ? extends R> f) {
        return e -> f.apply(e.getValue());
    }

    public <K, V> Predicate<Map.Entry<K, V>> byValue(final Predicate<? super V> f) {
        return e -> f.test(e.getValue());
    }

    public <K, V> Collector<Map.Entry<K, V>, ?, Map<K, V>> entryToMap() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    public <K, V> Collector<Map.Entry<K, V>, ?, Map<K, V>> entryToMap(final BinaryOperator<V> mergeFunction) {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, mergeFunction);
    }

    public <K, V, M extends Map<K, V>> Collector<Map.Entry<K, V>, ?, M> entryToMap(final BinaryOperator<V> mergeFunction, final Supplier<M> mapSupplier) {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, mergeFunction, mapSupplier);
    }

}
