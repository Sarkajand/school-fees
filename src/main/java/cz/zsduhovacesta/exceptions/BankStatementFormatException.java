package cz.zsduhovacesta.exceptions;


public class BankStatementFormatException extends Exception {

    public BankStatementFormatException() {

    }

    public BankStatementFormatException(String message) {
        super(message);
    }

    public BankStatementFormatException(Throwable cause) {
        super(cause);
    }

    public BankStatementFormatException(String message, Throwable cause) {
        super(message, cause);
    }

}
