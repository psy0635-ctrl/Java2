package ai0825;

import java.util.Scanner;

public class Lab_Score {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int[] scoreArr = new int[5];

        int sum = 0;
        double avg = 0;

        System.out.println("김연아 선수가 멋진 경기를 마쳤습니다.");
        System.out.println("심사위원분들은 최대 10점까지 주실 수 있습니다.");

        for(int i = 0; i < scoreArr.length; i++){
            System.out.print("심사위원 " + (i+1) + ":");
            scoreArr[i] = s.nextInt();
            sum += scoreArr[i];
        }

        avg = (double)sum/scoreArr.length;

        System.out.println("[심사위원 입력 점수]");
        for (int i = 0; i < scoreArr.length; i++) {
            System.out.printf("심사위원 %d : %d점   ", i+1, scoreArr[i]);
        }
        System.out.println();
        System.out.println("합계 점수 : " + sum);
        System.out.printf("평균 점수 : %.2f\n" , avg);

        s.close();
    }

}
