package dao;

import java.sql.SQLException;

public class DAOFactory {
	public IDAOManager createDAOManager() throws SQLException {
		return new DAOManagerJDBCImpl();
	}
}
