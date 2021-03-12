import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Solution {
    private static int count;
    private static String[] lines;

    public static void main(String[] args) throws IOException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
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
        for (String line : lines) {
            if (line.startsWith("D")) {
                QueryLine lineD = new QueryLine();
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
                    Date dateFrom = format.parse(dates[1]);
                    lineD.setDateFrom(dateFrom);
                    Date dateTo = format.parse(dates[2]);
                    lineD.setDateTo(dateTo);
                } else if (!parametrs[4].contains("-")) {
                    Date dateFrom = format.parse(parametrs[4]);
                    lineD.setDateFrom(dateFrom);
                }
                queryLines.add(lineD);
            }
            else if(line.startsWith("C")){
                TimeLine lineC = new TimeLine();
                String[] cParams = line.trim().split(" ");
                lineC.setServiceId(Double.parseDouble(cParams[1]));
                lineC.setQuestionId(Double.parseDouble(cParams[2]));
                lineC.setResponseType(cParams[3]);
                Date date = format.parse(cParams[4]);
                lineC.setDate(date);
                lineC.setWaitingTime(Integer.parseInt(cParams[5]));
                timeLines.add(lineC);
            }

        }
    }
}