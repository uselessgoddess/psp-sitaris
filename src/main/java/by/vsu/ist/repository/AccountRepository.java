package by.vsu.ist.repository;

import by.vsu.ist.domain.Account;
import by.vsu.ist.domain.Group;
import by.vsu.ist.domain.GroupPair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AccountRepository extends BaseRepository {
	public Long create(Account account) throws SQLException {
		String sql = "INSERT INTO \"employee\"(\"name\") VALUES (?)";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, account.getName());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			return resultSet.getLong(1);
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	public List<Account> readAll(String table) throws SQLException {
		String sql = "SELECT \"id\", \"name\", \"photo\" FROM \"" + table + "\"";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			List<Account> accounts = new ArrayList<>();
			while(resultSet.next()) {
				Account account = new Account();
				account.setId(resultSet.getLong("id"));
				account.setName(resultSet.getString("name"));
				account.setPhoto(resultSet.getBytes("photo"));
				accounts.add(account);
			}
			return accounts;
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	public List<Group> readGroups() throws SQLException {
		String sql = "SELECT \"id\", \"course_id\", \"coach_id\", \"max_participants\" FROM \"groups\"";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			List<Group> groups = new ArrayList<>();
			while(resultSet.next()) {
				Group account = new Group();
				account.setId(resultSet.getLong("id"));
				var coachId = resultSet.getLong("coach_id");
				account.setCoach(read("coaches", coachId).get());
				account.setMaxParticipants(resultSet.getInt("max_participants"));
				var rel = readGroupsInner();

				var participants = new ArrayList<Account>();
				for (GroupPair pair : rel) {
					if (pair.groupId.equals(account.getId())) {
						participants.add(read("employee", pair.accountId).get());
					}
				}
				account.setParticipants(participants);

				groups.add(account);
			}
			return groups;
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	public void addToGroup(Long groupId, Long accountId) throws SQLException {
		if (read("employee", accountId).isEmpty()) {
			return;
		}

		String sql = "INSERT INTO student_groups(employee_id, group_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, accountId);
			statement.setLong(2, groupId);
			statement.executeUpdate();
		} finally {
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	public List<GroupPair> readGroupsInner() throws SQLException {
		String sql = "SELECT \"employee_id\", \"group_id\" FROM \"student_groups\"";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			List<GroupPair> groups = new ArrayList<>();
			while(resultSet.next()) {
				GroupPair pair = new GroupPair();
				pair.groupId = resultSet.getLong("group_id");
				pair.accountId = resultSet.getLong("employee_id");
				groups.add(pair);
			}
			return groups;
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	public Optional<Account> read(String table, Long id) throws SQLException {
		String sql = "SELECT \"id\", \"name\", \"photo\" FROM " + "\"" + table + "\"" + "WHERE \"id\" = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			Account account = null;
			if(resultSet.next()) {
				account = new Account();
				account.setId(resultSet.getLong("id"));
				account.setName(resultSet.getString("name"));
				account.setPhoto(resultSet.getBytes("photo"));
			}
			return Optional.ofNullable(account);
		} finally {
			try { Objects.requireNonNull(resultSet).close(); } catch(Exception ignored) {}
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}

	public void update(String table, Account account) throws SQLException {
		String sql = "UPDATE \"" + table + "\" SET \"name\" = ?, \"photo\" = ? WHERE \"id\" = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setString(1, account.getName());
			statement.setBytes(2, account.getPhoto());
			statement.setLong(3, account.getId());
			statement.executeUpdate();
		} finally {
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}


	public void delete(Long id) throws SQLException {
		String sql = "DELETE FROM \"employee\" WHERE \"id\" = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			statement.executeUpdate();
		} finally {
			try { Objects.requireNonNull(statement).close(); } catch(Exception ignored) {}
		}
	}
}
