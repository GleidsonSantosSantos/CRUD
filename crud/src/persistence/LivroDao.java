package sql;

package persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import controller.ICRUDDao;

public class LivroDao implements ICRUDDao<Livro> {
    private Connection connection;

    public LivroDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Livro livro) throws SQLException {
        String sqlExemplar = "INSERT INTO Exemplar (codigo, nome, qtdPaginas) VALUES (?, ?, ?)";
        String sqlLivro = "INSERT INTO Livro (codigo, ISBN, edicao) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmtExemplar = connection.prepareStatement(sqlExemplar);
             PreparedStatement stmtLivro = connection.prepareStatement(sqlLivro)) {

            connection.setAutoCommit(false);
            
            stmtExemplar.setInt(1, livro.getCodigo());
            stmtExemplar.setString(2, livro.getNome());
            stmtExemplar.setInt(3, livro.getQtdPaginas());
            stmtExemplar.executeUpdate();
            
            stmtLivro.setInt(1, livro.getCodigo());
            stmtLivro.setString(2, livro.getISBN());
            stmtLivro.setInt(3, livro.getEdicao());
            stmtLivro.executeUpdate();
            
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public int update(Livro livro) throws SQLException {
        String sqlExemplar = "UPDATE Exemplar SET nome = ?, qtdPaginas = ? WHERE codigo = ?";
        String sqlLivro = "UPDATE Livro SET ISBN = ?, edicao = ? WHERE codigo = ?";
        
        try (PreparedStatement stmtExemplar = connection.prepareStatement(sqlExemplar);
             PreparedStatement stmtLivro = connection.prepareStatement(sqlLivro)) {

            connection.setAutoCommit(false);
            
            stmtExemplar.setString(1, livro.getNome());
            stmtExemplar.setInt(2, livro.getQtdPaginas());
            stmtExemplar.setInt(3, livro.getCodigo());
            stmtExemplar.executeUpdate();
            
            stmtLivro.setString(1, livro.getISBN());
            stmtLivro.setInt(2, livro.getEdicao());
            stmtLivro.setInt(3, livro.getCodigo());
            int rows = stmtLivro.executeUpdate();
            
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
    public void delete(Livro livro) throws SQLException {
        String sqlLivro = "DELETE FROM Livro WHERE codigo = ?";
        String sqlExemplar = "DELETE FROM Exemplar WHERE codigo = ?";
        
        try (PreparedStatement stmtLivro = connection.prepareStatement(sqlLivro);
             PreparedStatement stmtExemplar = connection.prepareStatement(sqlExemplar)) {

            connection.setAutoCommit(false);
            
            stmtLivro.setInt(1, livro.getCodigo());
            stmtLivro.executeUpdate();
            
            stmtExemplar.setInt(1, livro.getCodigo());
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
    public Livro findOne(Livro livro) throws SQLException {
        String sql = "SELECT * FROM Exemplar e JOIN Livro l ON e.codigo = l.codigo WHERE e.codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, livro.getCodigo());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Livro found = new Livro();
                found.setCodigo(rs.getInt("codigo"));
                found.setNome(rs.getString("nome"));
                found.setQtdPaginas(rs.getInt("qtdPaginas"));
                found.setISBN(rs.getString("ISBN"));
                found.setEdicao(rs.getInt("edicao"));
                return found;
            }
        }
        return null;
    }

    @Override
    public List<Livro> findAll() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Exemplar e JOIN Livro l ON e.codigo = l.codigo";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setCodigo(rs.getInt("codigo"));
                livro.setNome(rs.getString("nome"));
                livro.setQtdPaginas(rs.getInt("qtdPaginas"));
                livro.setISBN(rs.getString("ISBN"));
                livro.setEdicao(rs.getInt("edicao"));
                livros.add(livro);
            }
        }
        return livros;
    }
}
