package wiseSaying;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {
    @BeforeEach
    void beforeEach() {
        AppTest.clear();
    }

    @Test
    @DisplayName("등록")
    void t1() {
        String out = AppTest.run("""
                등록
                인생은 짧고 예술은 길다.
                히포크라테스
                종료
                """);

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }
    @Test
    @DisplayName("목록")
    void t2() {
        String out = AppTest.run("""
                등록
                명언1
                작가1
                등록
                명언2
                작가2
                목록
                종료
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("1 / 작가1 / 명언1")
                .contains("2 / 작가2 / 명언2");
    }
    @Test
    @DisplayName("삭제")
    void t3() {
        String out = AppTest.run("""
                등록
                명언1
                작가1
                등록
                명언2
                작가2
                등록
                명언3
                작가3
                목록
                삭제?id=2
                삭제?id=2
                종료
                """);

        assertThat(out)
                .contains("2 / 작가2 / 명언2")
                .contains("2번 명언이 삭제되었습니다")
                .contains("2번 명언은 존재하지 않습니다.");
    }
    @Test
    @DisplayName("수정")
    void t4() {
        String out = AppTest.run("""
                등록
                명언1
                작가1
                등록
                명언2
                작가2
                등록
                명언3
                작가3
                수정?id=3
                명언4
                작가4
                목록
                종료
                """);

        assertThat(out)
                .contains("명언(기존) : 명언3")
                .contains("작가(기존) : 작가3")
                .contains("명언 :")
                .contains("작가 :")
                .contains("3번 명언이 수정되었습니다.")
                .contains("3 / 작가4 / 명언4");
    }
    @Test
    @DisplayName("빌드")
    void t5() {
        String out = AppTest.run("""
                빌드
                종료
                """);

        assertThat(out)
                .contains("data.json 파일의 내용이 갱신되었습니다.");
    }

    @Test
    @DisplayName("검색 - 내용(content) 기준")
    void t6() {
        String out = AppTest.run("""
        등록
        현재를 사랑하라.
        작자미상
        등록
        과거에 집착하지 마라.
        작자미상
        목록?keywordType=content&keyword=과거
        """);

        assertThat(out)
                .contains("검색타입 : content")
                .contains("검색어 : 과거")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("검색 - 작가(author) 기준")
    void t7() {
        String out = AppTest.run("""
        등록
        지금을 살아라.
        작자미상
        등록
        배워서 남 주자.
        작자미상
        등록
        행복은 성실에서 온다.
        홍길동
        목록?keywordType=author&keyword=작자
        """);

        assertThat(out)
                .contains("검색타입 : author")
                .contains("검색어 : 작자")
                .contains("1 / 작자미상 / 지금을 살아라.")
                .contains("2 / 작자미상 / 배워서 남 주자.")
                .doesNotContain("3 / 홍길동 / 행복은 성실에서 온다.");
    }

    @Test
    @DisplayName("페이징 - 1페이지")
    void t8() {
        StringBuilder input = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            input.append("등록\n");
            input.append("명언 ").append(i).append("\n");
            input.append("작자미상 ").append(i).append("\n");
        }
        input.append("목록\n");

        String out = AppTest.run(input.toString());

        assertThat(out)
                .contains("페이지 : [1] / 2")
                .contains("10 / 작자미상 10 / 명언 10")
                .contains("6 / 작자미상 6 / 명언 6")
                .doesNotContain("5 / 작자미상 5 / 명언 5"); // 1페이지에는 5번 이하 없음
    }

    @Test
    @DisplayName("페이징 - 2페이지")
    void t9() {
        StringBuilder input = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            input.append("등록\n");
            input.append("명언 ").append(i).append("\n");
            input.append("작자미상 ").append(i).append("\n");
        }
        input.append("목록?page=2\n");

        String out = AppTest.run(input.toString());

        assertThat(out)
                .contains("페이지 : 1 / [2]")
                .contains("5 / 작자미상 5 / 명언 5")
                .contains("1 / 작자미상 1 / 명언 1")
                .doesNotContain("6 / 작자미상 6 / 명언 6"); // 2페이지에는 6번 이상 없음
    }


}
