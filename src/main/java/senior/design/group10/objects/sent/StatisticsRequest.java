package senior.design.group10.objects.sent;

public class StatisticsRequest {
    private String start;
    private String end;

    public StatisticsRequest(){}

    public StatisticsRequest(String start, String end){
        this.start = start;
        this.end = end;
    }

    public String getStart(){
        return start;
    }
    public String getEnd(){
        return end;
    }
}
