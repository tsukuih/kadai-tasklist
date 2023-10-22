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
 * Servlet implementation class EditServlet
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // DBUtilクラスのcreateEntityManager()オブジェクトでデータベースに接続し
        // EntityManagerのインスタンスを生成し、変数emにアクセス情報を格納
        EntityManager em = DBUtil.createEntityManager();

        // 該当のIDのタスクをデータベースから取得し、変数tに代入（１件のみ）
        Tasks t = em.find(Tasks.class, Integer.parseInt(request.getParameter("id")));

        // インスタンスemが不要になったので閉じる
        em.close();

        // タスク情報とセッションIDをリクエストスコープに登録
        request.setAttribute("tasks", t);
        request.setAttribute("_token", request.getSession().getId());

        // タスクIDをセッションスコープに登録
        request.getSession().setAttribute("tasks_id", t.getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
        rd.forward(request, response);

    }
}
