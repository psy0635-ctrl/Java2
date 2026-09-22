package ai0922;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LAB_AddLineNumber {
    static void main(String[] args) {
        try{
            BufferedReader br = new BufferedReader(new FileReader("myData1.txt"));
            String line = " ";
            int cnt = 1;

            while(true){
                line = br.readLine();
                if(line == null)
                    break;
                System.out.println(cnt++ + " : " + line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
