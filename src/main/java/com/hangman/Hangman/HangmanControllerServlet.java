package com.hangman.Hangman;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "HangmanControllerServlet", value = "/HangmanGame")
public class HangmanControllerServlet extends HttpServlet {
    public boolean gameStart = false;

    private HangmanGame startGameInstance() {
        return new HangmanGame();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //we will receive this request when they press START GAME.
        // once we receive the request, talk to the actual Hangman.java and make an instance
        //where that instance lives in memory ... not sure but.
        //right now , lets fake it by checking by setting an attribute to TRUE, and
        //sending that attribute in the response
        //if cookies yada yada , recreate instance of existing game
        //if no cookies, create new game.
        System.out.println("GET REQUEST RECEIVED");
        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/test.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
