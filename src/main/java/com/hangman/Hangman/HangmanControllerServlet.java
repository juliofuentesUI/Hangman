package com.hangman.Hangman;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;


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
        String startGame = request.getParameter("startGame");
        // TODO: IMAGINE A SCENARIO WHERE THEY REFRESH THE PAGE AND DONT GO THROUGH THE START GAME HYPERLINK
        // TODO: If they refresh the page their game will already exists...SO in the ELSE BLOCK
        // TODO: DO EITHER A) FIND THE EXISTING GAME INSTANCE IN MEMORY THAT CORRELATES OR TWO, JUST
        // TODO: RECREATE a new Hangman game instance but pass in STATE.
        // IF THAT HAPPENS, WE WON'T GET A startGame equals TRUE parameter. So we should do
        // if startGame.equals('true") || session exists and validate with cookie id!
        // we may need to create a new JSP page as an intermediary loading page.
        // TODO: Don't check  a session this way.
        if (startGame.equals("true") && session.getAttribute("hasStarted") == null) {
            //This confirms session is brand new, create a new game.
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
        hangmanGame.checkLetter(letter);
        //should be hangmanGame.updateLetterMap(letter);
        SetAllowOriginInHeader(response);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println("POST received, updated Hangman state, please refresh on client side");

        //probably don't need to session.setAttribute again, this should all be reference types
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/hangman.jsp");
//        dispatcher.forward(request, response);
//        System.out.println("ABOUT TO RE-DIRECT ");
//        response.sendRedirect("/hangman.jsp");
        //forward request,response to hangman.jsp page with updated values
        //we should eventually do json format so we can send back objects
        //lets try to leave AJAX to just sending POST and not expecting anything back
        //if request is bad, send SC_NOT_FOUND
        //try sending back response without setting header

    }

    private void SetAllowOriginInHeader(HttpServletResponse response) {
        //dont forget, there's something in regards to "PRE FLIGHT"
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
    }
}
