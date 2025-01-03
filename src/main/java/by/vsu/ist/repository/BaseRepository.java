package by.vsu.ist.repository;

import java.sql.Connection;

abstract public class BaseRepository {
	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	protected Connection getConnection() {
		return connection;
	}
}
