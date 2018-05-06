package hospital.converter;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateConverterImpl implements DateConverter {

    public Date toDate(String format, String dateString){
        switch(format){
            case "withTimeInfo":  return getTimeInfoDate(dateString);
            case "withoutTimeInfo": return getSimpleDate(dateString);
            default: return null;
        }
    }

    @Override
    public String toSimpleString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    private Date getTimeInfoDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(dateString+":00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private Date getSimpleDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
