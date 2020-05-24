public class AuthenticationService {
    private final UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    private static <T> Status<T> handleError(Exception e) {
        if (e instanceof DatabaseDownException) {
            return Status.failure(500, "Database down");
        } else {
            return Status.failure(599, "Unknown error occurred");
        }
    }

    public Status<Boolean> checkUser(String email, String password) {
        try {
            User user = userDao.getUserOrNull(email, password);
            return Status.success(user != null);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    public Status<AuthorizationResponse> authorization(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return Status.success(new AuthorizationResponse(
                    false, "Please provide non-empty email and password"));
        }

        String key = email + password;
        try {
            String keyFromDatabase = userDao.getKey(email, password);
            boolean isAuthorized = keyFromDatabase.equals(key);
            return Status.success(new AuthorizationResponse(
                    isAuthorized, isAuthorized ? "Authorized" : "Keys did not match"));
        } catch (Exception e) {
            return handleError(e);
        }
    }
}
