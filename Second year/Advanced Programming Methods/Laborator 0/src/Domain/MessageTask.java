package Domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageTask extends Task {
    private String message;
    private String from;
    private String to;
    private LocalDateTime date;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    public MessageTask(String id,String description,String message,String from,String to,LocalDateTime date){
        super(id,description);
        this.message = message;
        this.from=from;
        this.to=to;
        this.date=date;
    }

    @Override
    public void execute(){
        System.out.println(this.message +" "+this.date.format(formatter));
    }

    @Override
    public String toString(){
        return super.toString() + '|' +
                "message=" + message + '|' +
                "from=" + from + '|' +
                "to=" + to + '|' +
                "date=" + date.format(formatter);
    }
}
