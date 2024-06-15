package persistence;


	package persistence;

	import java.sql.*;
	import java.util.ArrayList;
	import java.util.List;
	import controller.ICRUDDao;

	public class AlunoDao implements ICRUDDao<Aluno> {
	    private Connection connection;

	    public AlunoDao(Connection connection) {
	        this.connection = connection;
	    }

	    @Override
	    public void insert(Aluno aluno) throws SQLException {
	        String sql = "INSERT INTO Aluno (RA, nome, email) VALUES (?, ?, ?)";
	        
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setInt(1, aluno.getRA());
	            stmt.setString(2, aluno.getNome());
	            stmt.setString(3, aluno.getEmail());
	            stmt.executeUpdate();
	        }
	    }

	    @Override
	    public int update(Aluno aluno) throws SQLException {
	        String sql = "UPDATE Aluno SET nome = ?, email = ? WHERE RA = ?";
	        
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setString(1, aluno.getNome());
	            stmt.setString(2, aluno.getEmail());
	            stmt.setInt(3, aluno.getRA());
	            return stmt.executeUpdate();
	        }
	    }

	    @Override
	    public void delete(Aluno aluno) throws SQLException {
	        String sql = "DELETE FROM Aluno WHERE RA = ?";
	        
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setInt(1, aluno.getRA());
	            stmt.executeUpdate();
	        }
	    }

	    @Override
	    public Aluno findOne(Aluno aluno) throws SQLException {
	        String sql = "SELECT * FROM Aluno WHERE RA = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setInt(1, aluno.getRA());
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                Aluno found = new Aluno();
	                found.setRA(rs.getInt("RA"));
	                found.setNome(rs.getString("nome"));
	                found.setEmail(rs.getString("email"));
	                return found;
	            }
	        }
	        return null;
	    }

	    @Override
	    public List<Aluno> findAll() throws SQLException {
	        List<Aluno> alunos = new ArrayList<>();
	        String sql = "SELECT * FROM Aluno";
	        try (Statement stmt = connection.createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {
	            while (rs.next()) {
	                Aluno aluno = new Aluno();
	                aluno.setRA(rs.getInt("RA"));
	                aluno.setNome(rs.getString("nome"));
	                aluno.setEmail(rs.getString("email"));
	                alunos.add(aluno);
	            }
	        }
	        return alunos;
	    }
	}
