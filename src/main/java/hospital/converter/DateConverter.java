package hospital.converter;

import java.util.Date;

public interface DateConverter {
    Date toDate(String format, String dateString);
    String toSimpleString(Date date);
}
