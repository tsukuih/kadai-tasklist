package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tasks;
import utils.DBUtil;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // リクエストパラメータから「_token」という名前のパラメータの値を取得
        String _token = request.getParameter("_token");

        if(_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();

            // エンティティマネジャーのトランザクションオブジェクトを取得し、トランザクションを開始
            em.getTransaction().begin();

            // Tasksクラスのインスタンスを作成し、変数mに代入
            Tasks t = new Tasks();

            // 各フィールドにデータを代入

            // requestクラスのcontentのリクエストパラメータの値を取得し、String型変数contentに代入
            // contentには、リクエストパラメータcontentの値が格納されている
            String content = request.getParameter("content");

            // Tasksクラスのインスタンスtのbodyフィールドに文字列contentをセット
            t.setContent(content);

            // 現在のタイムスタンプを取得
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            // TasksクラスのインスタンスtのCreated_at及びUpdated_atフィールドにタイムスタンプcurrentTimeを設定
            t.setCreated_at(currentTime);
            t.setUpdated_at(currentTime);

            // Tasksクラスのインスタンスtをデータベースにセーブ
            // エンティティマネージャー（EntityManager）のトランザクションをコミット
            // エンティティマネージャー（EntityManager）を閉じる
            em.persist(t);
            em.getTransaction().commit();
            em.close();

            //リクエストをトップページ（"/index")にリダイレクト
            response.sendRedirect(request.getContextPath() + "/index");

        }
    }

}
