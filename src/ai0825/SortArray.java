package ai0825;

import java.util.Arrays;
import java.util.Collections;

public class SortArray {
    public static void main(String[] args) {
        Integer[] numArr = {78,45,65,12,97,32,41};
//        Arrays.sort(numArr, Collections.reverseOrder()); // 내림차순정렬
        Arrays.sort(numArr); // 오름차순정렬
        for(int data : numArr){
            System.out.print(data + " ");
        }

        System.out.println();

        String[] nameArr = {"김유민","도형준","강석현","유재화","장영서"};
//        Arrays.sort(nameArr); // 오름차순정렬
        Arrays.sort(nameArr,Collections.reverseOrder());  // 내림차순정렬
        for(String data : nameArr){
            System.out.print(data + " ");
        }

        System.out.println();


    }
}
