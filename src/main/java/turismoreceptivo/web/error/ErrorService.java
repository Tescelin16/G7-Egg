package turismoreceptivo.web.error;

public class ErrorService extends Exception{
    
    public ErrorService (String msn){
        super(msn);
    }
}