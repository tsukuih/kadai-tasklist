package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tasks;


/**
 * Servlet implementation class NewServlet
 */
@WebServlet("/new")
public class NewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // CSRF対策
        // フォームからhidden要素で送られた値とセションに格納された同一であれば送信を受け付ける
        // サイト外からPOST送信された投稿を拒否できる、セションIDを利用
        request.setAttribute("_token", request.getSession().getId());

        // Messageのインスタンスを生成しルクエストスコープに格納
        // フォームのページを表示するために必要
        // 画面表示時のエラー回避のため、とりあえず"文字数0のデータをフォームに渡すため"
        request.setAttribute("tasks", new Tasks());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/new.jsp");
        rd.forward(request, response);

    }
}
