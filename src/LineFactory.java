import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class LineFactory {
    public Line createLine(String line) throws ParseException {
        Line newLine = null;
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        if (line.startsWith("D")) {
            newLine = new QueryLine();
           // lineD.setLineNum(Arrays.asList(lines).indexOf(line));
            String[] parametrs = line.trim().split(" ");
            if(parametrs[1].equals("*")){
                newLine.setServiceId(0.0);
            }
            else
                newLine.setServiceId(Double.parseDouble(parametrs[1]));
            if(parametrs[2].equals("*")){
                newLine.setQuestionId(0.0);
            }
            else
                newLine.setQuestionId(Double.parseDouble(parametrs[2]));
            newLine.setResponseType(parametrs[3]);
            if (parametrs[4].contains("-")) {
                String[] dates = parametrs[4].split("-");
                Date dateFrom = format.parse(dates[0]);
                ((QueryLine) newLine).setDateFrom(dateFrom);
                Date dateTo = format.parse(dates[1]);
                ((QueryLine) newLine).setDateTo(dateTo);
            } else if (!parametrs[4].contains("-")) {
                Date dateFrom = format.parse(parametrs[4]);
                ((QueryLine) newLine).setDateFrom(dateFrom);
            }

            //queryLines.add(lineD);

        }
        else if(line.startsWith("C")){
            newLine = new TimeLine();
            //lineC.setLineNum(Arrays.asList(lines).indexOf(line));
            String[] cParams = line.trim().split(" ");
            newLine.setServiceId(Double.parseDouble(cParams[1]));
            String[] question = cParams[2].split("\\.");
            newLine.setQuestionId(Double.parseDouble(question[0]));
            newLine.setResponseType(cParams[3]);
            Date date = format.parse(cParams[4]);
            ((TimeLine) newLine).setDate(date);
            ((TimeLine) newLine).setWaitingTime(Integer.parseInt(cParams[5]));
            //timeLines.add(lineC);

        }
return newLine;
    }
    }

