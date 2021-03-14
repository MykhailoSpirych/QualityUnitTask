import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Solution {
    private static int count;
    private static String[] lines;


    public static void main(String[] args) throws IOException{
       /* SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");*/
        System.out.println("Please enter a count of all lines.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            count = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter the number");
        }
        if (count > 0 && count <= 100000) {
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
           /* if (line.startsWith("D")) {
                QueryLine lineD = new QueryLine();
                lineD.setLineNum(Arrays.asList(lines).indexOf(line));
                String[] parametrs = line.trim().split(" ");
                if(parametrs[1].equals("*")){
                    lineD.setServiceId(0.0);
                }
                else
                lineD.setServiceId(Double.parseDouble(parametrs[1]));
                if(parametrs[2].equals("*")){
                    lineD.setQuestionId(0.0);
                }
                else
                lineD.setQuestionId(Double.parseDouble(parametrs[2]));
                lineD.setResponseType(parametrs[3]);
                if (parametrs[4].contains("-")) {
                    String[] dates = parametrs[4].split("-");
                    Date dateFrom = format.parse(dates[0]);
                    lineD.setDateFrom(dateFrom);
                    Date dateTo = format.parse(dates[1]);
                    lineD.setDateTo(dateTo);
                } else if (!parametrs[4].contains("-")) {
                    Date dateFrom = format.parse(parametrs[4]);
                    lineD.setDateFrom(dateFrom);
                }

                queryLines.add(lineD);

            }
            else if(line.startsWith("C")){
                TimeLine lineC = new TimeLine();
                lineC.setLineNum(Arrays.asList(lines).indexOf(line));
                String[] cParams = line.trim().split(" ");
                lineC.setServiceId(Double.parseDouble(cParams[1]));
                String[] question = cParams[2].split("\\.");
                lineC.setQuestionId(Double.parseDouble(question[0]));
                lineC.setResponseType(cParams[3]);
                Date date = format.parse(cParams[4]);
                lineC.setDate(date);
                lineC.setWaitingTime(Integer.parseInt(cParams[5]));
                timeLines.add(lineC);

            }

        }*/
        System.out.println(timeLines.size());
        System.out.println(queryLines.size());

        averageWaitingTime(queryLines,timeLines);

    }

    public static void averageWaitingTime(List<QueryLine> linesD,List<TimeLine> linesC){
        int linesCount=0;
        int result = 0;
        for(QueryLine lineD : linesD) {
            for(TimeLine lineC : linesC) {
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

    public static boolean isPrevious(TimeLine C,QueryLine D){
        return C.getLineNum() < D.getLineNum();
    }

    public static boolean isQuestionEq(TimeLine C,QueryLine D){
        return (D.getQuestionId()==0||(D.getQuestionId()==C.getQuestionId()));
    }

    public static boolean isServiceEq(TimeLine C,QueryLine D){
        return D.getServiceId()<=C.getServiceId();
    }

    public static boolean isResponseEq(TimeLine C,QueryLine D){
        return D.getResponseType().equals(C.getResponseType());
    }

    public static boolean isCorrectDate(TimeLine C,QueryLine D){
        return ((D.getDateTo().after(C.getDate())&&D.getDateFrom().before(C.getDate()))
                ||D.getDateFrom().equals(C.getDate()));
    }
}