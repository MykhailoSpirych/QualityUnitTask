import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LineFactory {
    public Line createLine(List<String> list) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Line line = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).startsWith("D")) {
                line = new QueryLine();
                line.setLineCount(i);
                String[] parametrs = list.get(i).trim().split(" ");
                if(parametrs[1].equals("*")){
                    line.setServiceId(0.0);
                }
                else
                    line.setServiceId(Double.parseDouble(parametrs[1]));
                if(parametrs[2].equals("*")){
                    line.setQuestionId(0.0);
                }
                else
                    line.setQuestionId(Double.parseDouble(parametrs[2]));
                line.setResponseType(parametrs[3]);
                if (parametrs[4].contains("-")) {
                    String[] dates = parametrs[4].split("-");
                    Date dateFrom = format.parse(dates[0]);
                    ((QueryLine) line).setDateFrom(dateFrom);
                    Date dateTo = format.parse(dates[1]);
                    ((QueryLine) line).setDateTo(dateTo);
                } else if (!parametrs[4].contains("-")) {
                    Date dateFrom = format.parse(parametrs[4]);
                    ((QueryLine) line).setDateFrom(dateFrom);
                }
            }
            else if(list.get(i).startsWith("C")){
                line = new TimeLine();
                line.setLineCount(i);
                String[] cParams = list.get(i).trim().split(" ");
                line.setServiceId(Double.parseDouble(cParams[1]));
                String[] question = cParams[2].split("\\.");
                line.setQuestionId(Double.parseDouble(question[0]));
                line.setResponseType(cParams[3]);
                Date date = format.parse(cParams[4]);
                ((TimeLine) line).setDate(date);
                ((TimeLine) line).setWaitingTime(Integer.parseInt(cParams[5]));

            }

        }
        return line;
    }
}
