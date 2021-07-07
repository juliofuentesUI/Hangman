package com.hangman.Hangman;

import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import com.hangman.Hangman.JsonRedirect;


@WebServlet(name = "HangmanControllerServlet", value = "/HangmanGame")
public class HangmanControllerServlet extends HttpServlet {
    final String ENV_DOMAIN = "localhost";
    public static int cookieCount = 0;
    private UUID uniqueId;
    private Cookie cookie;

    private HangmanGame startGameInstance() {
        //pass in optional ID? Who knows.
        return new HangmanGame();
    }

    private Cookie createCookie(String name, String uuid) {
        cookie = new Cookie(name, uuid);
        cookie.setComment("Stores uuid to retrieve state from server");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 60 * 1000);
        cookie.setDomain(ENV_DOMAIN);
        return cookie;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //check for cookies first...see if they exists THAT WAY, also keep hasStarted flag in session object why not
        System.out.println("GET REQUEST RECEIVED");
        HttpSession session = request.getSession();
        HangmanGame hangmanGame = (HangmanGame) session.getAttribute("gameInstance");
        String startGame = request.getParameter("startGame"); //probably won't need this startGame param
        // TODO: Don't check  a session this way.
        if (session.getAttribute("hasStarted") == null ) {
            //This confirms session is brand new or game needs to reset & create a new game.
            System.out.println("New game starting");
            HangmanGame game = startGameInstance();
            uniqueId = UUID.randomUUID();
            session.setAttribute("hasStarted", true);
            session.setAttribute("sessionId", uniqueId.toString());
            session.setAttribute("gameInstance", game);
            Cookie cookie = this.createCookie("gameId" , uniqueId.toString());
            response.addCookie(cookie);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/hangman.jsp");
            dispatcher.forward(request, response);
            //load up the game .. probably on a diff  JSP page
            //make JSP page access data only from sessions cope object called "session" in jsp context
            //TODO: May need to add else if livesRemaining <= 0
        } else {
            //TODO: Separate into some authentication method
            Cookie[] cookies = request.getCookies();
            String sessionId = session.getAttribute("sessionId").toString();
            System.out.println("Session ID IS : ");
            System.out.println(sessionId);
            for(Cookie cookie: cookies) {
                if (cookie.getName().equals("gameId") && sessionId.equals(cookie.getValue())) {
                    System.out.println("Session ID matches cookie id!");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/hangman.jsp");
                    dispatcher.forward(request, response);
                }
            }
            //if for loop ends...and nothing matches..just redirect them somewhere
            //their game already exists!
            //do the validation to confirm if their game exists already
            //RIGHT NOW WE'LL GET A BLANK PAGE ONCE A SESSION ALREADY EXISTS!!
            //pass what is in session
            //basically forward the request and response object to the same hangman.jsp page, but the
            //request,response object will be stubbed in from existing data in session object.
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //the Letter buttons A-Z will each make a ajax post request to this method
        //it will interact with the current gameInstance attached to the session.
        System.out.println("POST REQUEST RECEIVED");
        String letter = request.getParameter("letter");
        System.out.println("letter value is : " + letter);
        HttpSession session = request.getSession();
        HangmanGame hangmanGame = (HangmanGame) session.getAttribute("gameInstance");
        Integer lives_remaining = hangmanGame.checkLetter(letter);
        if (lives_remaining == 0) {
            //trigger redirect here
            SetAllowOriginInHeader(response);
//            response.setStatus(HttpServletResponse.SC_FOUND);
            PrintWriter printWriter = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JsonRedirect jsonObj = new JsonRedirect("/ResetGameServlet");
            String jsonObjString = new Gson().toJson(jsonObj);
            printWriter.print(jsonObjString);
            printWriter.flush();
            //use GSON to make instance of an object that has location url in it
//            response.sendRedirect("/ResetGameServlet");
//            request.getRequestDispatcher("/ResetGameServlet").forward(request, response);
        } else {
            SetAllowOriginInHeader(response);
            response.setStatus(HttpServletResponse.SC_OK);
            //remember its this STATUS CODE that triggers client side success callback
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.println("POST received, updated Hangman state, please refresh on client side to see results");
        }
        //probably don't need to session.setAttribute again, this should all be reference types
        //we should eventually do json format so we can send back objects
    }

    private void SetAllowOriginInHeader(HttpServletResponse response) {
        //dont forget, there's something in regards to "PRE FLIGHT"
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
    }
}
