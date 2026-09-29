package ai0929.Exception;

import java.io.*;

public class ThrowsTest1 {
    public static void main(String[] args) {
        try{
            BufferedReader br = new BufferedReader(new FileReader("myData1.txt"));

            while(true){
                String line = br.readLine();
                if(line==null){
                    break;
                }
                System.out.println(line);
            }
        }catch (FileNotFoundException e){
            System.out.println("파일을 찾을 수 없습니다.");
        }catch (IOException e){
            System.out.println("한 줄 읽어올 때 문제가 발생했습니다.");
        }
    }
}
