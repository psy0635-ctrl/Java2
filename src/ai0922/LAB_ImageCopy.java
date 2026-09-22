package ai0922;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

// 이미지 파일과 같은 이진 파일은 1byte씩 읽어서 1byte씩 출력해야 한다.
public class LAB_ImageCopy {
    public static void main(String[] args) {
        try {
            // 원본 이미지 읽기
            FileInputStream fis = new FileInputStream("FireFlower.png");

            // 복사할 이미지 파일 만들기
            FileOutputStream fos = new FileOutputStream("FireFlowerCopy.png");

            int data;

            // 이미지 데이터를 1바이트씩 읽어서 복사
            while ((data = fis.read()) != -1) {
                fos.write(data);
            }

            fis.close();
            fos.close();

            System.out.println("이미지 복사 완료");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
