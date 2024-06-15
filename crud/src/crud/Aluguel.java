package crud;

import java.util.Date;

public class Aluguel {
    private Aluno aluno;
    private Exemplar exemplar;
    private Date dataRetirada;
    private Date dataDevolucao;

    public Aluguel() {}

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public Date getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(Date dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public String toString() {
        return "Aluguel{" +
                "aluno=" + aluno +
                ", exemplar=" + exemplar +
                ", dataRetirada=" + dataRetirada +
                ", dataDevolucao=" + dataDevolucao +
                '}';
    }
}
