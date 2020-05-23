public class AuthenticationService {
    private final UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Status<Boolean> checkUser(String email, String password) {
        try {
            User user = userDao.getUserOrNull(email, password);
            return Status.success(user != null);
        } catch (DatabaseDownException e) {
            return Status.failure(500, "Database down");
        } catch (Exception e) {
            return Status.failure(599, "Unknown error occurred");
        }
    }
}
