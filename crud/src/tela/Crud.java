import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AlunoController")
public class AlunoController extends HttpServlet {

    private ICRUDDao<Aluno> alunoDao;

    @Override
    public void init() throws ServletException {
        // Inicializar o DAO, pode ser uma implementação fictícia ou um mock para testes
        alunoDao = new AlunoDaoImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String RA = request.getParameter("RA");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");

        Aluno aluno = new Aluno();
        aluno.setRA(Integer.parseInt(RA));
        aluno.setNome(nome);
        aluno.setEmail(email);

        try {
            alunoDao.insert(aluno);
            List<Aluno> alunos = alunoDao.findAll();
            request.setAttribute("alunos", alunos);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/aluno.jsp").forward(request, response);
    }
}
