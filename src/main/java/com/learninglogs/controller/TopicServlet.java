package com.learninglogs.controller;

import com.learninglogs.dao.TopicDao;
import com.learninglogs.dao.TopicDaoImpl;
import com.learninglogs.entity.Topic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

/**
 * TopicServlet — handles all topic-related HTTP requests.
 *
 * URL: /topic
 *
 * GET actions:
 *   (default)      -> list all topics   -> topiclist.jsp
 *   ?action=new    -> show add form     -> topicadd.jsp
 *   ?action=edit   -> show edit form    -> topicadd.jsp (pre-filled)
 *   ?action=search -> search topics     -> topiclist.jsp (filtered)
 *
 * POST actions:
 *   action=add     -> insert new topic  -> redirect to /topic
 *   action=edit    -> update topic      -> redirect to /topic
 *   action=delete  -> delete topic      -> redirect to /topic
 */

// ============================================================
// TODO 7: Servlet Class Declaration + doGet Skeleton
// ============================================================
// Create the TopicServlet class that handles all topic HTTP requests.
//
// Steps:
//   1. Add @WebServlet("/topic") annotation above the class
//   2. Make the class extend HttpServlet
//   3. Create a TopicDao field: new TopicDaoImpl()
//   4. Override the doGet method
//   5. Get the "action" parameter from the request
//
// CONCEPTS:
// - @WebServlet("/topic") registers this servlet at the URL /topic.
//   When a browser visits /your-app/topic, Tomcat calls this class.
// - HttpServlet is the base class — you override doGet() for GET
//   requests and doPost() for POST requests.
// - The DAO field is created once and reused for every request.
// - request.getParameter("action") reads the ?action=xxx from the
//   URL. If no action parameter exists, it returns null.
//
// The complete code:
//
//   @WebServlet("/topic")
//   public class TopicServlet extends HttpServlet {
//
//       private final TopicDao topicDao = new TopicDaoImpl();
//
//       @Override
//       protected void doGet(HttpServletRequest request,
//                            HttpServletResponse response)
//               throws ServletException, IOException {
//
//           String action = request.getParameter("action");
//
//           // TODOs 8-9 go here (inside doGet)
//       }
//   }
//
// ============================================================
@WebServlet("/topic")
public class TopicServlet extends HttpServlet {

    private final TopicDao topicDao = new TopicDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // ============================================================
        // TODO 8: doGet — List All Topics (Default Action)
        // ============================================================
        // When no action parameter is provided (action is null),
        // fetch all topics from the database and display them.
        //
        // Steps:
        //   1. Check: if action is null
        //   2. Call topicDao.fetchAllTopics() to get all topics
        //   3. Store the list in the request: setAttribute("topics", topics)
        //   4. Forward to topiclist.jsp using RequestDispatcher
        //
        // CONCEPTS:
        // - request.setAttribute("topics", topics) stores data that the
        //   JSP can access via EL: ${topics} in the JSP reads this list.
        // - RequestDispatcher.forward() sends the request to a JSP for
        //   rendering. The browser URL does NOT change (server-side).
        // - The JSP path starts with /WEB-INF/ because JSPs there are
        //   protected from direct browser access — only servlets can
        //   forward to them.
        //
        // The complete code:
        //
        //   if (action == null) {
        //       ArrayList<Topic> topics = topicDao.fetchAllTopics();
        //       request.setAttribute("topics", topics);
        //       request.getRequestDispatcher("/WEB-INF/views/topiclist.jsp")
        //              .forward(request, response);
        //   }
        //
        // ============================================================
        if (action == null) {
            ArrayList<Topic> topics = topicDao.fetchAllTopics();
            request.setAttribute("topics", topics);
            request.getRequestDispatcher("/WEB-INF/views/topiclist.jsp")
                   .forward(request, response);
        }

        // ============================================================
        // TODO 9: doGet — Show Add or Edit Form
        // ============================================================
        // Handle two actions that show the topic form:
        //   - action=new  -> empty form for adding
        //   - action=edit -> pre-filled form for editing
        //
        // For "new":
        //   Just forward to topicadd.jsp (no data needed — empty form)
        //
        // For "edit":
        //   1. Get the topicid parameter from the URL
        //   2. Parse it to int: Integer.parseInt(...)
        //   3. Find the topic: topicDao.findTopicById(topicId)
        //   4. Store it: request.setAttribute("topic", topic)
        //   5. Forward to topicadd.jsp (form will pre-fill from ${topic})
        //
        // CONCEPT: The same JSP (topicadd.jsp) handles both add and
        // edit. The difference is whether a "topic" attribute exists
        // in the request. The JSP uses ${empty topic ? ...} to check.
        //
        // The complete code:
        //
        //   else if ("new".equals(action)) {
        //       request.getRequestDispatcher("/WEB-INF/views/topicadd.jsp")
        //              .forward(request, response);
        //   }
        //   else if ("edit".equals(action)) {
        //       int topicId = Integer.parseInt(request.getParameter("topicid"));
        //       Topic topic = topicDao.findTopicById(topicId);
        //       request.setAttribute("topic", topic);
        //       request.getRequestDispatcher("/WEB-INF/views/topicadd.jsp")
        //              .forward(request, response);
        //   }
        //
        // ============================================================
        else if ("new".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/topicadd.jsp")
                   .forward(request, response);
        }
        else if ("edit".equals(action)) {
            int topicId = Integer.parseInt(request.getParameter("topicid"));
            Topic topic = topicDao.findTopicById(topicId);
            request.setAttribute("topic", topic);
            request.getRequestDispatcher("/WEB-INF/views/topicadd.jsp")
                   .forward(request, response);
        }
        // ============================================================
        // TODO 16: doGet — Search Topics
        // ============================================================
        // Handle the "search" action from the search form on topiclist.jsp.
        //
        // Steps:
        //   1. Get the "search" parameter (the keyword typed by the user)
        //   2. If keyword is null or blank, show all topics (fetchAllTopics)
        //   3. Otherwise, call topicDao.searchTopics(keyword) for filtered results
        //   4. Store the results: setAttribute("topics", topics)
        //   5. Store the keyword: setAttribute("searchKeyword", keyword)
        //      so the search input keeps showing what the user searched for
        //   6. Forward to topiclist.jsp
        //
        // CONCEPT: The search form uses GET (not POST) because searching
        // is a read-only operation — it doesn't change data. The search
        // keyword appears in the URL: /topic?action=search&search=Python
        //
        // The complete code:
        //
        //   else if ("search".equals(action)) {
        //       String keyword = request.getParameter("search");
        //       ArrayList<Topic> topics;
        //       if (keyword == null || keyword.trim().isEmpty()) {
        //           topics = topicDao.fetchAllTopics();
        //       } else {
        //           topics = topicDao.searchTopics(keyword.trim());
        //       }
        //       request.setAttribute("topics", topics);
        //       request.setAttribute("searchKeyword", keyword);
        //       request.getRequestDispatcher("/WEB-INF/views/topiclist.jsp")
        //              .forward(request, response);
        //   }
        //
        // ============================================================
        else if ("search".equals(action)) {
            String keyword = request.getParameter("search");
            ArrayList<Topic> topics;
            if (keyword == null || keyword.trim().isEmpty()) {
                topics = topicDao.fetchAllTopics();
            } else {
                topics = topicDao.searchTopics(keyword.trim());
            }
            request.setAttribute("topics", topics);
            request.setAttribute("searchKeyword", keyword);
            request.getRequestDispatcher("/WEB-INF/views/topiclist.jsp")
                   .forward(request, response);
        }
    }

    // ============================================================
    // TODO 10: doPost — Add New Topic
    // ============================================================
    // Handle POST requests for adding a new topic.
    //
    // Steps:
    //   1. Override the doPost method
    //   2. Get the "action" parameter
    //   3. If action is "add":
    //      a) Get the "topic" parameter (the name typed by the user)
    //      b) Validate: if null or blank, set error and forward back
    //      c) Try to insert: topicDao.insertTopic(new Topic(name))
    //      d) If insert fails (duplicate), set error and forward back
    //      e) If success, redirect to the topic list
    //
    // CONCEPTS:
    // - Forward vs Redirect:
    //   * Forward keeps the SAME request — used for errors so the
    //     error message and user's input are still available
    //   * Redirect creates a NEW request — used after successful
    //     changes (Post-Redirect-Get pattern prevents double-submit)
    // - request.getContextPath() returns your app's base URL
    //   (e.g., "/learning-logs") for building redirect URLs
    //
    // The complete code:
    //
    //   @Override
    //   protected void doPost(HttpServletRequest request,
    //                         HttpServletResponse response)
    //           throws ServletException, IOException {
    //
    //       String action = request.getParameter("action");
    //
    //       if ("add".equals(action)) {
    //           String topicName = request.getParameter("topic");
    //
    //           if (topicName == null || topicName.trim().isEmpty()) {
    //               request.setAttribute("error", "Topic name cannot be empty.");
    //               request.getRequestDispatcher("/WEB-INF/views/topicadd.jsp")
    //                      .forward(request, response);
    //               return;
    //           }
    //
    //           boolean success = topicDao.insertTopic(new Topic(topicName.trim()));
    //
    //           if (!success) {
    //               request.setAttribute("error", "Topic already exists.");
    //               request.getRequestDispatcher("/WEB-INF/views/topicadd.jsp")
    //                      .forward(request, response);
    //               return;
    //           }
    //
    //           response.sendRedirect(request.getContextPath() + "/topic");
    //       }
    //
    //       // TODOs 11-12 go here (inside doPost)
    //   }
    //
    // ============================================================
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String topicName = request.getParameter("topic");

            if (topicName == null || topicName.trim().isEmpty()) {
                request.setAttribute("error", "Topic name cannot be empty.");
                request.getRequestDispatcher("/WEB-INF/views/topicadd.jsp")
                       .forward(request, response);
                return;
            }

            boolean success = topicDao.insertTopic(new Topic(topicName.trim()));

            if (!success) {
                request.setAttribute("error", "Topic already exists.");
                request.getRequestDispatcher("/WEB-INF/views/topicadd.jsp")
                       .forward(request, response);
                return;
            }

            response.sendRedirect(request.getContextPath() + "/topic");
        }

        // ============================================================
        // TODO 11: doPost — Edit Topic
        // ============================================================
        // Handle the "edit" action from the form submission.
        //
        // Steps:
        //   1. Get topicid and topic name from the form
        //   2. Parse topicid to int
        //   3. Validate: if name is null or blank, set error, find
        //      the original topic, and forward back to the form
        //   4. Create a Topic object with the new name
        //   5. Set the ID on it: topic.setId(topicId)
        //   6. Call topicDao.updateTopic(topic)
        //   7. Redirect to the topic list
        //
        // CONCEPT: For the error case, we need to re-set the "topic"
        // attribute so the form stays in edit mode with the original
        // data. Without this, the form would switch to add mode.
        //
        // The complete code:
        //
        //   else if ("edit".equals(action)) {
        //       int topicId = Integer.parseInt(request.getParameter("topicid"));
        //       String topicName = request.getParameter("topic");
        //
        //       if (topicName == null || topicName.trim().isEmpty()) {
        //           request.setAttribute("error", "Topic name cannot be empty.");
        //           Topic topic = topicDao.findTopicById(topicId);
        //           request.setAttribute("topic", topic);
        //           request.getRequestDispatcher("/WEB-INF/views/topicadd.jsp")
        //                  .forward(request, response);
        //           return;
        //       }
        //
        //       Topic topic = new Topic(topicName.trim());
        //       topic.setId(topicId);
        //       topicDao.updateTopic(topic);
        //       response.sendRedirect(request.getContextPath() + "/topic");
        //   }
        //
        // ============================================================
        else if ("edit".equals(action)) {
            int topicId = Integer.parseInt(request.getParameter("topicid"));
            String topicName = request.getParameter("topic");

            if (topicName == null || topicName.trim().isEmpty()) {
                request.setAttribute("error", "Topic name cannot be empty.");
                Topic topic = topicDao.findTopicById(topicId);
                request.setAttribute("topic", topic);
                request.getRequestDispatcher("/WEB-INF/views/topicadd.jsp")
                       .forward(request, response);
                return;
            }

            Topic topic = new Topic(topicName.trim());
            topic.setId(topicId);
            topicDao.updateTopic(topic);
            response.sendRedirect(request.getContextPath() + "/topic");
        }

        // ============================================================
        // TODO 12: doPost — Delete Topic
        // ============================================================
        // Handle the "delete" action from the delete form.
        //
        // Steps:
        //   1. Get topicid from the hidden form field
        //   2. Parse to int
        //   3. Call topicDao.deleteTopic(topicId)
        //   4. Redirect to the topic list
        //
        // CONCEPT: Delete uses POST (not GET) because it modifies
        // data. Browsers only support GET and POST in HTML forms —
        // there is no DELETE method in forms. The hidden input
        // name="action" value="delete" tells the servlet which
        // operation to perform.
        //
        // The database has ON DELETE CASCADE on the entries table,
        // so deleting a topic automatically deletes all its entries.
        //
        // The complete code:
        //
        //   else if ("delete".equals(action)) {
        //       int topicId = Integer.parseInt(request.getParameter("topicid"));
        //       topicDao.deleteTopic(topicId);
        //       response.sendRedirect(request.getContextPath() + "/topic");
        //   }
        //
        // ============================================================
        else if ("delete".equals(action)) {
            int topicId = Integer.parseInt(request.getParameter("topicid"));
            topicDao.deleteTopic(topicId);
            response.sendRedirect(request.getContextPath() + "/topic");
        }
    }
}
