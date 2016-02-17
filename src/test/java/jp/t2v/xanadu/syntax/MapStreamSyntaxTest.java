package jp.t2v.xanadu.syntax;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static jp.t2v.xanadu.syntax.MapStreamSyntax.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class MapStreamSyntaxTest {

    @Test
    public void mapValues() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "aaa");
        map.put(2, "bbbbb");
        map.put(3, "cccccc");

        Map<Integer, String> result = map.entrySet().stream()
                .filter(byValue(s -> s.length() > 3))
                .map(values(String::toUpperCase))
                .collect(entryToMap());

        assertThat(result, is(new HashMap<Integer, String>() {{ put(2, "BBBBB"); put(3, "CCCCCC"); }}));
    }



}
