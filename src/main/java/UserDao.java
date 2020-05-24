public class UserDao {
    @SuppressWarnings({"unused", "RedundantThrows"})
    public User getUserOrNull(String email, String password) throws DatabaseDownException {
        throw new RuntimeException("Not implemented");
    }

    @SuppressWarnings({"unused", "RedundantThrows"})
    public String getKey(String email, String password) throws DatabaseDownException {
        throw new RuntimeException("Not implemented");
    }
}
