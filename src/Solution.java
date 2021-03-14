import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    private static int count;
    private static String[] lines;


    public static void main(String[] args) throws IOException{

        System.out.println("Please enter a count of all lines.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            count = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the number");
        }
        if (count > 0 && count <= 100000) {
            System.out.println("Please enter "+count+" lines.");
            lines = new String[count];
            for (int i = 0; i < count; i++) {
                lines[i] = reader.readLine();
            }
        } else System.out.println("The number can`t be negative or more than 100000.Please try again.");
        List<TimeLine> timeLines = new ArrayList<>();
        List<QueryLine> queryLines = new ArrayList<>();
        LineFactory factory = new LineFactory();
        for (String line : lines) {
            Line currentLine = null;
            try {
                currentLine = factory.createLine(line);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (currentLine != null) {
                currentLine.setLineNum(Arrays.asList(lines).indexOf(line));
            }
            if(currentLine instanceof TimeLine){
                timeLines.add((TimeLine) currentLine);
            }
            else queryLines.add((QueryLine) currentLine);
        }


        averageWaitingTime(queryLines,timeLines);

    }

    public static void averageWaitingTime(List<? extends Line> linesD,List<? extends Line> linesC){
        int linesCount=0;
        int result = 0;
        for(Line line : linesD) {
            QueryLine lineD=(QueryLine) line;
            for(Line line1 : linesC) {
                TimeLine lineC=(TimeLine) line1;
                if(lineD.getQuestionId() == 0)
                {
                    if (isServiceEq(lineC,lineD) &&
                            isResponseEq(lineC,lineD) &&
                            isCorrectDate(lineC,lineD)&&
                    isPrevious(lineC,lineD)) {
                        linesCount++;
                        result += lineC.getWaitingTime();
                    }
                }
                else if (isServiceEq(lineC,lineD) &&
                        isQuestionEq(lineC,lineD) &&
                        lineD.getResponseType().equals(lineC.getResponseType())&&
                        isCorrectDate(lineC,lineD)&&
                isPrevious(lineC,lineD)) {
                    linesCount++;
                    result += lineC.getWaitingTime();
                }

            }
            if(linesCount == 0)
                System.out.println("-");
            else
                System.out.println(result/linesCount);
            linesCount = 0;
            result = 0;
        }
    }

    public static boolean isPrevious(Line C,Line D){
        return C.getLineNum() < D.getLineNum();
    }

    public static boolean isQuestionEq(Line C,Line D){
        return D.getQuestionId()==C.getQuestionId();
    }

    public static boolean isServiceEq(Line C,Line D){
        return D.getServiceId()<=C.getServiceId();
    }

    public static boolean isResponseEq(Line C,Line D){
        return D.getResponseType().equals(C.getResponseType());
    }

    public static boolean isCorrectDate(Object objC, Object objD){
        QueryLine D = (QueryLine) objD;
        TimeLine C = (TimeLine) objC;
        return ((D.getDateTo().after(C.getDate())&&D.getDateFrom().before(C.getDate()))
                ||D.getDateFrom().equals(C.getDate()));
    }
}