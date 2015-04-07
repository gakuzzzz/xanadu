package jp.t2v.xanadu.ds;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Tuples {

    @Value(staticConstructor = "of")
    @FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
    @Getter(AccessLevel.PRIVATE)
    public static class T2<A, B> { @NonNull A _1; @NonNull B _2; }

    @Value(staticConstructor = "of")
    @FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
    @Getter(AccessLevel.PRIVATE)
    public static class T3<A, B, C> { @NonNull A _1; @NonNull B _2; @NonNull C _3; }

//    @Value(staticConstructor = "of")
//    @FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
//    @Getter(AccessLevel.PRIVATE)
//    public static class T4<A, B, C, D> { @NonNull A _1; @NonNull B _2; @NonNull C _3; @NonNull D _4; }
//
//    @Value(staticConstructor = "of")
//    @FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
//    @Getter(AccessLevel.PRIVATE)
//    public static class T5<A, B, C, D, E> { @NonNull A _1; @NonNull B _2; @NonNull C _3; @NonNull D _4; @NonNull E _5; }
//
//    @Value(staticConstructor = "of")
//    @FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
//    @Getter(AccessLevel.PRIVATE)
//    public static class T6<A, B, C, D, E, F> { @NonNull A _1; @NonNull B _2; @NonNull C _3; @NonNull D _4; @NonNull E _5; @NonNull F _6; }

}
