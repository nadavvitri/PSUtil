package exceptions;

public class InvalidColumnException extends Exception{

    private final String ERROR = "Error: ";
    private final String columnName;

    public InvalidColumnException(String columnName){
        this.columnName = columnName;
    }

    @Override
    public String getMessage() {
        return (this.ERROR + this.columnName + " not found!");
    }
}
