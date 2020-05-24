public class AuthorizationResponse {
    private final boolean isAuthorized;
    private final String message;

    public AuthorizationResponse(boolean isAuthorized, String message) {
        this.isAuthorized = isAuthorized;
        this.message = message;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public String getMessage() {
        return message;
    }
}
