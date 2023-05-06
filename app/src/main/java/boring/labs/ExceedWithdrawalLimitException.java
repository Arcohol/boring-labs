package boring.labs;

public class ExceedWithdrawalLimitException extends RuntimeException{
    public ExceedWithdrawalLimitException(String message) {
        super(message);
    }
}
