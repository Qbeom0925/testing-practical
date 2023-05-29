package sample.cafekiosk.learning;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

public class GuavaLearningTest {

    @DisplayName("주어진 개수만큼 List를 파티셔닝한다.")
    @Test
    public void partitionLearningTest1() throws Exception{
        //given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        //when
        List<List<Integer>> partition = Lists.partition(integers, 3);

        //then
        Assertions.assertThat(partition)
                .hasSize(2)
                .containsExactly(
                        List.of(1,2,3),
                        List.of(4,5,6)
                );
    }

    @DisplayName("주어진 개수만큼 List를 파티셔닝한다.")
    @Test
    public void partitionLearningTest2() throws Exception{
        //given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        //when
        List<List<Integer>> partition = Lists.partition(integers, 4);

        //then
        Assertions.assertThat(partition)
                .hasSize(2)
                .containsExactly(
                        List.of(1,2,3,4),
                        List.of(5,6)
                );
    }

    @DisplayName("멀티맵 확인 기능")
    @Test
    public void test() throws Exception{
        //given
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라떼");
        multimap.put("커피", "카페모카");
        multimap.put("베이커리", "크루아상");
        multimap.put("베이커리", "식빵");

        //when
        List<String> coffees = multimap.get("커피");

        //then
        Assertions.assertThat(coffees)
                .hasSize(3)
                .containsExactly("아메리카노", "카페라떼", "카페모카");
    }

    @DisplayName("멀티맵 확인 기능")
    @TestFactory
    Collection<DynamicTest> test2() throws Exception{
        //given
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라떼");
        multimap.put("커피", "카푸치노");
        multimap.put("베이커리", "크루아상");
        multimap.put("베이커리", "식빵");

        //when
        return List.of(
                DynamicTest.dynamicTest("1개 value 삭제", () -> {
                    //when
                    multimap.remove("커피", "카푸치노");

                    //then
                    Collection<String> coffees = multimap.get("커피");
                    Assertions.assertThat(coffees)
                            .hasSize(2)
                            .isEqualTo(List.of("아메리카노", "카페라떼"));
                }),
                DynamicTest.dynamicTest("1개 value 삭제", () -> {
                    //when
                    multimap.removeAll("커피");

                    //then
                    Collection<String> coffees = multimap.get("커피");
                    Assertions.assertThat(coffees).isEmpty();
                })

        );
    }
}
