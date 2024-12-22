package by.vsu.ist.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
public class ConnectionPool {
    private boolean ready = false;
    private final Queue<Connection> freeConnections = new ConcurrentLinkedQueue<>();
    public Connection getConnection() {
        Connection connection = null;
        while(connection == null) {
            connection = freeConnections.poll();
            if(connection != null) {
                boolean isConnectionValid;
                try {
                    isConnectionValid = connection.isValid(0);
                } catch(SQLException e) {
                    isConnectionValid = false;
                }
                if(!isConnectionValid) {
                    remove(connection);
                    connection = null;
                }
            } else {
                throw new RuntimeException("There is no free connection");
            }
        }
        return new ConnectionWrapper(connection);
    }
    public synchronized void init(int size)  {
        if(size > 0) {
            try {
                while(size-- > 0) {
                    freeConnections.add(DatabaseConnector.getConnection());
                }
                ready = true;
            } catch(SQLException e) {
                destroy();
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Pool size should be greater 0");
        }
    }
    public void destroy() {
        synchronized(freeConnections) {
            freeConnections.forEach(this::remove);
            freeConnections.clear();
            ready = false;
        }
    }
    void release(Connection connection) {
        if(ready) {
            freeConnections.add(connection);
        } else {
            remove(connection);
        }
    }
    void remove(Connection connection) {
        try { connection.close(); } catch(SQLException ignored) {}
    }
    private static final ConnectionPool instance = new ConnectionPool();
    private ConnectionPool() {}
    public static ConnectionPool getInstance() {
        return instance;
    }
}