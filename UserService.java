public class UserService {
    private Map<String, User> users = new HashMap<>();

    public void addUser(User user) { users.put(user.getId(), user); }
    public boolean removeUser(String id) { return users.remove(id) != null; }
    public boolean updateUser(String id, String name, String email) {
        User u = users.get(id);
        if (u == null) return false;
        u.setName(name);
        u.setEmail(email);
        return true;
    }
    public User getUser(String id) { return users.get(id); }
    public List<User> getAllUsers() { return new ArrayList<>(users.values()); }
}
