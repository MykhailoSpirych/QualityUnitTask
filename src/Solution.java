import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
   final static String TEST_TEXT = """
           7
           C 1.1 8.15.1 P 15.10.2012 83
           C 1 10.1 P 01.12.2012 65
           C 1.1 5.5.1 P 01.11.2012 117
           D 1.1 8 P 01.01.2012-01.12.2012
           C 3 10.2 N 02.10.2012 100
           D 1 * P 08.10.2012-20.11.2012
           D 3 10 P 01.12.2012""";

    public static void main(String[] args) {
        List<String> lines = getTaskList(TEST_TEXT);
        System.out.println(lines.get(1));
        int s = Integer.parseInt(lines.get(0));
        System.out.println(isCorrectData(s));
        List<Integer> numbersOfQueryLine = new ArrayList<>();
        for (int i = 1; i < lines.size() ; i++) {
            if(lines.get(i).startsWith("D")){
            numbersOfQueryLine.add(i);
            }
        }
            // for(Integer num:numbersOfQueryLine){
           //   System.out.println(num);
          // }



    }


    public static boolean isCorrectData(int x){
        return x > 0 && x <= 100000;
    }

    public static List<String> getTaskList(String task){
        String[] strings = task.split("\n");
        return new ArrayList<>(Arrays.asList(strings));
    }
}
