package bike_rental.repository;

import bike_rental.model.RegularUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public int save(RegularUser user) {
        String sql = "INSERT INTO users (user_id, name, email, password, role, membership_type) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getMembershipType()
        );
    }

    public List<RegularUser> findAll() {
        String sql = "SELECT * FROM users ORDER BY name";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RegularUser u = new RegularUser();
            u.setUserId(rs.getString("user_id"));
            u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setMembershipType(rs.getString("membership_type"));
            return u;
        });
    }


    public List<RegularUser> search(String keyword) {
        String sql = "SELECT * FROM users WHERE name LIKE ? OR email LIKE ?";
        String k = "%" + keyword + "%";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RegularUser u = new RegularUser();
            u.setUserId(rs.getString("user_id"));
            u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setMembershipType(rs.getString("membership_type"));
            return u;
        }, k, k);
    }

    public RegularUser findById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        List<RegularUser> results = jdbcTemplate.query(sql, (rs, rowNum) -> {
            RegularUser u = new RegularUser();
            u.setUserId(rs.getString("user_id"));
            u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setMembershipType(rs.getString("membership_type"));
            return u;
        }, userId);
        return results.isEmpty() ? null : results.get(0);
    }

    public int update(RegularUser user) {
        String sql = "UPDATE users SET name=?, email=?, password=?, membership_type=? "
                + "WHERE user_id=?";
        return jdbcTemplate.update(sql,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getMembershipType(),
                user.getUserId()
        );
    }

    public int delete(String userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        return jdbcTemplate.update(sql, userId);
    }
}