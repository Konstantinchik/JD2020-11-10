package by.it._khmelov_.jd01_12;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskA3 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        // -1 0 4 5 2 0 -6 0 -2 8 end -->  4 5 2 8 0 0 0 -1 -6 -2
        int positionZero=0;
        for (;;) {
            String s = scanner.next();
            if (s.equals("end")){
                break;
            }
            Integer value=Integer.valueOf(s);
            if (value<0){
                list.add(value);
            }
            else if (value==0){
                list.add(positionZero, value);
            }
            else {
                list.add(positionZero++, value);
            }
        }
        System.out.println(list);
    }
}
