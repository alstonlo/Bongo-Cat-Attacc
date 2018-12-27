package protocol;

public class ErrorProtocol extends Protocol {

    public final String response;
    public final int errorState;

    public ErrorProtocol(String response, int errorState) {
        this.response = response;
        this.errorState = errorState;
    }
}
