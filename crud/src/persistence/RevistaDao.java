package persistence;

package persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import controller.ICRUDDao;

public class RevistaDao implements ICRUDDao<Revista> {
    private Connection connection;

    public RevistaDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Revista revista) throws SQLException {
        String sqlExemplar = "INSERT INTO Exemplar (codigo, nome, qtdPaginas) VALUES (?, ?, ?)";
        String sqlRevista = "INSERT INTO Revista (codigo, ISSN) VALUES (?, ?)";
        
        try (PreparedStatement stmtExemplar = connection.prepareStatement(sqlExemplar);
             PreparedStatement stmtRevista = connection.prepareStatement(sqlRevista)) {

            connection.setAutoCommit(false);
            
            stmtExemplar.setInt(1, revista.getCodigo());
            stmtExemplar.setString(2, revista.getNome());
            stmtExemplar.setInt(3, revista.getQtdPaginas());
            stmtExemplar.executeUpdate();
            
            stmtRevista.setInt(1, revista.getCodigo());
            stmtRevista.setString(2, revista.getISSN());
            stmtRevista.executeUpdate();
            
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public int update(Revista revista) throws SQLException {
        String sqlExemplar = "UPDATE Exemplar SET nome = ?, qtdPaginas = ? WHERE codigo = ?";
        String sqlRevista = "UPDATE Revista SET ISSN = ? WHERE codigo = ?";
        
        try (PreparedStatement stmtExemplar = connection.prepareStatement(sqlExemplar);
             PreparedStatement stmtRevista = connection.prepareStatement(sqlRevista)) {

            connection.setAutoCommit(false);
            
            stmtExemplar.setString(1, revista.getNome());
            stmtExemplar.setInt(2, revista.getQtdPaginas());
            stmtExemplar.setInt(3, revista.getCodigo());
            stmtExemplar.executeUpdate();
            
            stmtRevista.setString(1, revista.getISSN());
            stmtRevista.setInt(2, revista.getCodigo());
            int rows = stmtRevista.executeUpdate();
            
            connection.commit();
            return rows;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void delete(Revista revista) throws SQLException {
        String sqlRevista = "DELETE FROM Revista WHERE codigo = ?";
        String sqlExemplar = "DELETE FROM Exemplar WHERE codigo = ?";
        
        try (PreparedStatement stmtRevista = connection.prepareStatement(sqlRevista);
             PreparedStatement stmtExemplar = connection.prepareStatement(sqlExemplar)) {

            connection.setAutoCommit(false);
            
            stmtRevista.setInt(1, revista.getCodigo());
            stmtRevista.executeUpdate();
            
            stmtExemplar.setInt(1, revista.getCodigo());
            stmtExemplar.executeUpdate();
            
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public Revista findOne(Revista revista) throws SQLException {
        String sql = "SELECT * FROM Exemplar e JOIN Revista r ON e.codigo = r.codigo WHERE e.codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, revista.getCodigo());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Revista found = new Revista();
                found.setCodigo(rs.getInt("codigo"));
                found.setNome(rs.getString("nome"));
                found.setQtdPaginas(rs.getInt("qtdPaginas"));
                found.setISSN(rs.getString("ISSN"));
                return found;
            }
        }
        return null;
    }

    @Override
    public List<Revista> findAll() throws SQLException {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT * FROM Exemplar e JOIN Revista r ON e.codigo = r.codigo";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Revista revista = new Revista();
                revista.setCodigo(rs.getInt("codigo"));
                revista.setNome(rs.getString("nome"));
                revista.setQtdPaginas(rs.getInt("qtdPaginas"));
                revista.setISSN(rs.getString("ISSN"));
                revistas.add(revista);
            }
        }
       
