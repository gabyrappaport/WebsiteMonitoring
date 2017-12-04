package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DAOUtility {

	/**
	 * Initializes the prepared query based on the connection passed as an argument,
	 * with the SQL query and the given objects.
	 *
	 * @param connexion
	 * @param sql
	 * @param returnGeneratedKeys
	 * @param objets
	 * @return
	 * @throws SQLException
	 */
	public static PreparedStatement initializationPreparedRequest(Connection connexion, String sql,
			boolean returnGeneratedKeys, Object... objets) throws SQLException {
		PreparedStatement preparedStatement = connexion.prepareStatement(sql,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		for (int i = 0; i < objets.length; i++) {
			preparedStatement.setObject(i + 1, objets[i]);
		}
		return preparedStatement;
	}

	/**
	 * Silent closing of the connexion
	 *
	 * @param connexion
	 */

	public static void silentClosing(Connection connexion) {
		if (connexion != null) {
			try {
				connexion.close();
			} catch (SQLException e) {
				System.out.println("Failed to close Connexion : " + e.getMessage());
			}
		}
	}

	/**
	 * Silent closing of the resultset
	 *
	 * @param resultSet
	 */
	public static void silentClosing(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("Failed to close ResultSet : " + e.getMessage());
			}
		}
	}

	/**
	 * Silent closing of the resultset, statement and connexion
	 *
	 * @param resultSet
	 * @param statement
	 * @param connexion
	 */
	public static void silentClosing(ResultSet resultSet, Statement statement, Connection connexion) {
		DAOUtility.silentClosing(resultSet);
		DAOUtility.silentClosing(statement);
		DAOUtility.silentClosing(connexion);
	}

	/**
	 * Silent closing of the statement
	 *
	 * @param statement
	 */
	public static void silentClosing(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Failed to close Statement : " + e.getMessage());
			}
		}
	}

	/**
	 * Silent closing of the statement and connexion
	 *
	 * @param statement
	 * @param connexion
	 */
	public static void silentClosing(Statement statement, Connection connexion) {
		DAOUtility.silentClosing(statement);
		DAOUtility.silentClosing(connexion);
	}

	private DAOUtility() {
	}
}