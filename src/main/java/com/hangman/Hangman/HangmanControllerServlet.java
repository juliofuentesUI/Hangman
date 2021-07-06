package com.hangman.Hangman;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
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
        //get session if it exists already, which it almost likely will.
        //check if session has an attribute to signify they've been here already.
        //if the user is in the same session, but they're pressing START GAME , i will extract
        //the existing ID of the session (which is gonna be the same ID on the cookie, and load up the last
        //state they were in!
        String startGame = request.getParameter("startGame");
        //if were starting a new game AND there's no existing session ID, create ID + cookie + new game instance
        //every user will automatically get the JSESSIONID unique to them...but i still want to make my own cookie.
        //make sure the conditional below checks to se if were starting a game and if the user is BRAND NEW
        // TODO: IMAGINE A SCENARIO WHERE THEY REFRESH THE PAGE AND DONT GO THROUGH THE START GAME HYPERLINK
        // TODO: If they refresh the page their game will already exists...SO in the ELSE BLOCK
        // TODO: DO EITHER A) FIND THE EXISTING GAME INSTANCE IN MEMORY THAT CORRELATES OR TWO, JUST
        // TODO: RECREATE a new Hangman game instance but pass in STATE.
        // IF THAT HAPPENS, WE WON'T GET A startGame equals TRUE parameter. So we should do
        // if startGame.equals('true") || session exists and validate with cookie id!
        // we may need to create a new JSP page as an intermediary loading page.
        // validating may take a bit?
        if (startGame.equals("true") && session.getAttribute("hasStarted") == null) {
            //This confirms session is brand new, create a new game.
            System.out.println("New game starting");
            HangmanGame game = startGameInstance();
            uniqueId = UUID.randomUUID();
            session.setAttribute("hasStarted", true);
            session.setAttribute("sessionId", uniqueId.toString());
            session.setAttribute("gameInstance", game);
            Cookie cookie = this.createCookie("gameID" , uniqueId.toString());
            response.addCookie(cookie);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/hangman.jsp");
            dispatcher.forward(request, response);
            //load up the game .. probably on a diff  JSP page
            //make JSP page access data only from sessions cope object called "session" in jsp context
        } else {
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
        //
    }
}
