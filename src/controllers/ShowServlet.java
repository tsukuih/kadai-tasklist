package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tasks;
import utils.DBUtil;

/**
 * Servlet implementation class ShowServlet
 */
@WebServlet("/show")
public class ShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // DBUtilクラスのcreateEntityManager()オブジェクトでデータベースに接続し
        // EntityManagerのインスタンスを生成し、変数emにアクセス情報を格納
        EntityManager em = DBUtil.createEntityManager();

        // 該当のIDのメッセージをデータベースから取得し、変数tに代入（１件のみ）
        Tasks t = em.find(Tasks.class, Integer.parseInt(request.getParameter("id")));

        // インスタンスemが不要になったので閉じる
        em.close();

        // このサーブレット（ShowServlet.java）からJSP（show.jsp）に該当する課題のデータを渡す
        // ①setAttribute()オブジェクトでリクエストオブジェクトに属性をセット、第１引数：属性の名前、第２引数：属性の値
        request.setAttribute("tasks", t);

        // ②JSPの該当するデータを渡す
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/show.jsp");
        rd.forward(request, response);

    }

}
