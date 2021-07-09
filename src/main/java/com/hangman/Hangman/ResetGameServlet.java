package com.hangman.Hangman;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ResetGameServlet", value = "/ResetGameServlet")
public class ResetGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet from /ResetGameServlet");
        HttpSession session = request.getSession();
        HangmanGame hangmanGame = (HangmanGame) session.getAttribute("gameInstance");
        //TODO: Fix race condition below, it works but not reliable
        request.getRequestDispatcher("/resetGame.jsp").forward(request, response);
        hangmanGame.ResetGame();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost from /ResetGameServlet");
    }
}
