package controler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Bolla;
import model.Parrilla;

@WebServlet("/BingoControler")
public class BingoControler extends HttpServlet {

    private String idPartida;
    private Parrilla parrilla;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameterMap().containsKey("accio")) {
            String accio = request.getParameter("accio");
            Bolla bolla = new Bolla();
            switch (accio) {
                case "iniciar":
                    this.idPartida = "XXXX";
                    this.parrilla = new Parrilla();
                    bolla.setValor(-1);
                    break;
                case "bolla":
                    if (this.parrilla.getComptador() < this.parrilla.getNUMBOLLES()) {
                        bolla = this.parrilla.treureBolla();
                    }
                    else{
                        //HEM DE DIR QUE HEM ACABAT
                    }
                    break;
                case "reiniciar":
                    this.parrilla = new Parrilla();
                    bolla.setValor(-1);
                    break;
                default:
                    break;
            }
            request.setAttribute("parrilla", this.parrilla);
            request.setAttribute("bollaActual", bolla);
            request.setAttribute("idPartida", this.idPartida);
            request.getRequestDispatcher("bingo.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
