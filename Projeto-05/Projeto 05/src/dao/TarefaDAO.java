package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.jdbc.Driver;

import model.Tarefa;
import model.Usuario;

public class TarefaDAO {

	// CONSTANTES
	private static final String DB_URL = "jdbc:mysql://localhost:3306";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

	private static final String SELECT_BY_USER_ID_SQL = "SELECT * FROM lista_tarefas.tarefa WHERE id_usuario = ?";
	private static final String INSERT_SQL = "INSERT INTO lista_tarefas.tarefa (titulo, finalizada, id_usuario) VALUES (?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE lista_tarefas.tarefa SET finalizada = ? WHERE id = ? and id_usuario = ?";
	private static final String DELETE_SQL = "DELETE FROM lista_tarefas.tarefa WHERE id = ? and id_usuario = ?";

	public static ArrayList<Tarefa> buscarTarefasDoUsuario(Usuario u) {

		ArrayList<Tarefa> tarefa = new ArrayList<>();

		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);

			Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			PreparedStatement stmt = c.prepareStatement(SELECT_BY_USER_ID_SQL);

			stmt.setInt(1, u.getId());

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String titulo = rs.getString("titulo");
				boolean finalizada = rs.getBoolean("finalizada");

				Tarefa t = new Tarefa();
				t.setId(id);
				t.setTitulo(titulo);
				t.setFinalizada(finalizada);
				t.setIdUsuario(u.getId());

				tarefa.add(t);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tarefa;

	}

	public static boolean inserirTarefa(Tarefa t) {

		boolean sucesso = false;

		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);

			Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			PreparedStatement stmt = c.prepareStatement(INSERT_SQL);

			stmt.setString(1, t.getTitulo());
			stmt.setBoolean(2, t.isFinalizada());
			stmt.setInt(3, t.getIdUsuario());

			int rowsAffected = stmt.executeUpdate();

			sucesso = rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sucesso;
	}

	public static boolean finalizarTarefa(Tarefa t) {

		boolean sucesso = false;

		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);

			Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			PreparedStatement stmt = c.prepareStatement(UPDATE_SQL);

			stmt.setBoolean(1, true);
			stmt.setInt(2, t.getId());
			stmt.setInt(3, t.getIdUsuario());

			int rowsAffected = stmt.executeUpdate();

			sucesso = rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sucesso;

	}

	public static boolean removerTarefa (Tarefa t) {
	
		boolean sucesso = false;
		
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			
			Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
					
			PreparedStatement stmt = c.prepareStatement(DELETE_SQL);
			
			stmt.setInt(1, t.getId());
			stmt.setInt(2, t.getIdUsuario());
			
			int rowsAffected = stmt.executeUpdate();
			
			sucesso = rowsAffected > 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return sucesso;
		
	}
}
