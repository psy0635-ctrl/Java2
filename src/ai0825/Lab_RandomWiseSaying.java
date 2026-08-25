package ai0825;

import java.util.Random;

public class Lab_RandomWiseSaying {
    public static void main(String[] args) {
        String[] wisesay = {"내일 죽을 것처럼 살고, 영원히 살 것처럼 배워라. - — 마하트마 간디 ",
                            "나를 죽이지 못하는 고통은 나를 더욱 강하게 만든다. - — 프리드리히 니체 ",
                            "행운은 준비가 기회를 만났을 때 생겨난다. — 세네카 ",
                            "세상은 고통으로 가득하지만, 그것을 극복하는 일로도 가득하다. — 헬렌 켈러 ",
                            "불가능해 보이는 일도 해내고 나면 언제나 가능한 일이 된다. — 넬슨 만델라 ",
                            "가장 중요한 때는 지금이고, 가장 중요한 사람은 지금 함께 있는 사람이다. — 레프 톨스토이 ",
                            "시간을 낭비하지 마라. 언제나 유용한 일에 힘쓰고 불필요한 행동을 끊어라. — 벤저민 프랭클린 ",
                            "스스로를 신뢰하라. 그러면 다른 이들도 당신을 신뢰할 것이다. — 랄프 왈도 에머슨 ",
                            "계속 갈망하라, 여전히 우직하게 살아가라 (Stay hungry, stay foolish). — 스티브 잡스 ",
                            "산을 움직이려 하는 자는 작은 돌을 들어내는 일부터 시작한다. — 공자 "
        };

        Random random = new Random();
        int randomIndex = random.nextInt(wisesay.length);
        System.out.println("오늘의 명언 : " + wisesay[randomIndex]);

    }
}
