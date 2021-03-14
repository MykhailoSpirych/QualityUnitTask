public class Line {
private double questionId;
private double serviceId;
private String responseType;
private int lineCount;

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public double getQuestionId() {
        return questionId;
    }

    public void setQuestionId(double questionId) {
        this.questionId = questionId;
    }

    public double getServiceId() {
        return serviceId;
    }

    public void setServiceId(double serviceId) {
        this.serviceId = serviceId;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
}
