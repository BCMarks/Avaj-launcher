package src;

public class CustomExceptions extends Exception {

    private static final long serialVersionUID = 1L;

    public CustomExceptions() {

    }
    
    public CustomExceptions(String s) {
        super(s);  
    }

    public void throwException(String s) throws CustomExceptions {
        throw new CustomExceptions(s);
    }
}  