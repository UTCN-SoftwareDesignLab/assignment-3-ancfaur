package hospital.service.consultation;

public class ConsultationOverlapException extends Throwable {
    public ConsultationOverlapException(String message){
        super(message);
    }
}
