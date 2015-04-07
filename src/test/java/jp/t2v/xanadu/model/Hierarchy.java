package jp.t2v.xanadu.model;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;

public class Hierarchy {

    @Value @NonFinal @EqualsAndHashCode(callSuper=false) public static class A {}
    @Value @NonFinal @EqualsAndHashCode(callSuper=false) public static class B extends A {}
    @Value @NonFinal @EqualsAndHashCode(callSuper=false) public static class C extends B {}
    @Value @NonFinal @EqualsAndHashCode(callSuper=false) public static class D extends C {}

}
