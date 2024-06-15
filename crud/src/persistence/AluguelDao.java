package persistence;

package persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import controller.ICRUDDao;

public class AluguelDao implements ICRUDDao<Aluguel> {
    private Connection connection;

    public AluguelDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Aluguel aluguel) throws SQLException {
        String sql = "INSERT INTO Aluguel (alunoRA, exemplarCodigo, dataRetirada, dataDevolucao) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, aluguel.getAluno().getRA());
            stmt.setInt(2, aluguel.getExemplar().getCodigo());
            stmt.setDate(3, new java.sql.Date(aluguel.getDataRetirada().getTime()));
            stmt.setDate(4, new java.sql.Date(aluguel.getDataDevolucao().getTime()));
            stmt.executeUpdate();
        }
    }

    @Override
    public int update(Aluguel aluguel) throws SQLException {
        String sql = "UPDATE Aluguel SET dataRetirada = ?, dataDevolucao = ? WHERE alunoRA = ? AND exemplarCodigo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(aluguel.getDataRetirada().getTime()));
            stmt.setDate(2, new java.sql.Date(aluguel.getDataDevolucao().getTime()));
            stmt.setInt(3, aluguel.getAluno().getRA());
            stmt.setInt(4, aluguel.getExemplar().getCodigo());
            return stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Aluguel aluguel) throws SQLException {
        String sql = "DELETE FROM Aluguel WHERE alunoRA = ? AND exemplarCodigo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, aluguel.getAluno().getRA());
            stmt.setInt(2, aluguel.getExemplar().getCodigo());
            stmt.executeUpdate();
        }
    }

    @Override
    public Aluguel findOne(Aluguel aluguel) throws SQLException {
        String sql = "SELECT * FROM Aluguel WHERE alunoRA = ? AND exemplarCodigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, aluguel.getAluno().getRA());
            stmt.setInt(2, aluguel.getExemplar().getCodigo());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Aluguel found = new Aluguel();
                found.setAluno(new AlunoDao(connection).findOne(new Aluno(rs.getInt("alunoRA"))));
                found.setExemplar(new ExemplarDao(connection).findOne(new Exemplar(rs.getInt("exemplarCodigo"))));
                found.setDataRetirada(rs.getDate("dataRetirada"));
                found.setDataDevolucao(rs.getDate("dataDevolucao"));
                return found;
            }
        }
        return null;
    }

    @Override
    public List<Aluguel> findAll() throws SQLException {
        List<Aluguel> alugueis = new ArrayList<>();
        String sql = "SELECT * FROM Aluguel";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Aluguel aluguel = new Aluguel();
                aluguel.setAluno(new AlunoDao(connection).findOne(new Aluno(rs.getInt("alunoRA"))));
                aluguel.setExemplar(new ExemplarDao(connection).findOne(new Exemplar(rs.getInt("exemplarCodigo"))));
                aluguel.setDataRetirada(rs.getDate("dataRetirada"));
                aluguel.setDataDevolucao(rs.getDate("dataDevolucao"));
                alugueis.add(aluguel);
            }
        }
        return alugueis;
    }
}
