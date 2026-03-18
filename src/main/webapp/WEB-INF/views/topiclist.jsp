<%-- ============================================================
     TODO 1: JSP Directives
     ============================================================
     Add TWO directives at the very top of this file (before the
     DOCTYPE declaration):

     1. Page directive — tells the server this is a JSP page:
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>

     2. JSTL taglib directive — imports the core tag library so
        you can use <c:forEach> and <c:if>:
        <%@ taglib prefix="c" uri="jakarta.tags.core" %>

     CONCEPT: JSP directives configure the page. The page directive
     sets the content type (HTML + UTF-8). The taglib directive
     imports JSTL — Java's Standard Tag Library — which provides
     tags like <c:forEach> for looping (instead of writing Java
     scriptlet code in your JSP).

     JSTL 3.0 uses "jakarta.tags.core" as the URI (older versions
     used "http://java.sun.com/jsp/jstl/core").

     The complete code:

       <%@ page contentType="text/html;charset=UTF-8" language="java" %>
       <%@ taglib prefix="c" uri="jakarta.tags.core" %>
     ============================================================ --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

  <%-- ============================================================
       TODO 2: Head Section + Header + Navbar
       ============================================================
       Convert the static HTML head, header, and navbar to use
       dynamic paths with ${pageContext.request.contextPath}.

       1. Head section with contextPath CSS links:
          - Two <link> tags: main.css and topic-list.css
          - href="${pageContext.request.contextPath}/static/css/main.css"
          - href="${pageContext.request.contextPath}/static/css/topic-list.css"

       2. Header — same structure as Week 3, but update links:
          - Logo <a> href="${pageContext.request.contextPath}/topic"
          - Image src="${pageContext.request.contextPath}/static/images/book.png"

       3. Navbar — three items:
          - First li: empty (no back link on list page)
          - Second li: "Topic Lists" title
          - Third li: "+New Topic" link to ?action=new

       CONCEPT: ${pageContext.request.contextPath} resolves to your
       app's root URL (e.g., "/learning-logs"). In Week 3, HTML files
       used relative paths like "../static/css/main.css" because they
       lived in pages/. JSP files in WEB-INF/views/ are served through
       servlets, so relative paths won't work — you need absolute
       paths starting from the app root.

       The complete structure:

         <head>
           <meta charset="UTF-8" />
           <meta name="viewport" content="width=device-width, initial-scale=1.0" />
           <title>Learning Log</title>
           <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css" />
           <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/topic-list.css" />
         </head>

         (inside body, inside div.page:)
         <header class="header">
           <div class="logo">
             <a href="${pageContext.request.contextPath}/topic" style="text-decoration: none">
               <img src="${pageContext.request.contextPath}/static/images/book.png" alt="LL" />
             </a>
             <h3>Learning Log</h3>
           </div>
           <div class="usersession">
             <h3>Username</h3>
             <a href="#" class="logout">Logout</a>
           </div>
         </header>

         <nav class="navbar">
           <ul>
             <li></li>
             <li><p>Topic Lists</p></li>
             <li><a href="${pageContext.request.contextPath}/topic?action=new">+New Topic</a></li>
           </ul>
         </nav>

       Wireframe: See wireframes/topic-list.png
       ============================================================ --%>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Learning Log</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/topic-list.css" />
  </head>

  <body>
    <div class="page">

      <header class="header">
        <div class="logo">
          <a href="${pageContext.request.contextPath}/topic" style="text-decoration: none">
            <img src="${pageContext.request.contextPath}/static/images/book.png" alt="LL" />
          </a>
          <h3>Learning Log</h3>
        </div>
        <div class="usersession">
          <h3>Username</h3>
          <a href="#" class="logout">Logout</a>
        </div>
      </header>

      <nav class="navbar">
        <ul>
          <li></li>
          <li><p>Topic Lists</p></li>
          <li><a href="${pageContext.request.contextPath}/topic?action=new">+New Topic</a></li>
        </ul>
      </nav>

      <%-- ============================================================
           TODO 3: Dynamic Content — Search + JSTL forEach Loop
           ============================================================
           Replace the hardcoded topic item from Week 3 with a dynamic
           loop that displays topics from the database.

           1. Search bar — same as Week 3 but with contextPath action:
              - form action="${pageContext.request.contextPath}/topic"
              - Hidden input: name="action" value="search"

           2. Topic list — use <c:forEach> to loop over ${topics}:

              <c:forEach var="topic" items="${topics}" varStatus="status">
                ...one topic item per iteration...
              </c:forEach>

              Inside the loop, use EL expressions:
              - ${status.count} gives the 1-based loop number (1, 2, 3...)
              - ${topic.name} calls topic.getName() automatically
              - ${topic.id} calls topic.getId()

           3. Each topic item has:
              - Topic link: displays "${status.count}. ${topic.name}"
              - Edit link: href with ?action=edit&topicid=${topic.id}
              - Delete form: POST with hidden action=delete and topicid

           CONCEPT: ${topics} is an ArrayList<Topic> set by the servlet
           using request.setAttribute("topics", topics). JSTL's
           <c:forEach> iterates over it like Java's for-each loop.
           EL (Expression Language) accesses bean properties by calling
           the getter automatically — ${topic.name} calls getName().

           The complete content structure:

             <main class="content">
               <div class="search">
                 <form action="${pageContext.request.contextPath}/topic" method="get">
                   <input type="hidden" name="action" value="search" />
                   <label for="search">Topic: </label>
                   <input type="text" name="search" placeholder="Search..." value="${searchKeyword}" />
                   <button type="submit">SEARCH</button>
                 </form>
               </div>
               <div class="topicContainer">
                 <ul>
                   <c:forEach var="topic" items="${topics}" varStatus="status">
                     <li class="topicItem">
                       <a class="topic" href="#">${status.count}. ${topic.name}</a>
                       <a class="edit" href="${pageContext.request.contextPath}/topic?action=edit&topicid=${topic.id}">Edit</a>
                       <form action="${pageContext.request.contextPath}/topic" method="post">
                         <input type="hidden" name="action" value="delete" />
                         <input type="hidden" name="topicid" value="${topic.id}" />
                         <button class="submit" type="submit"
                           onclick="return confirm('Are you sure you want to delete?');">
                           Delete
                         </button>
                       </form>
                     </li>
                   </c:forEach>
                 </ul>
               </div>
             </main>
           ============================================================ --%>
      <main class="content">
        <div class="search">
          <form action="${pageContext.request.contextPath}/topic" method="get">
            <input type="hidden" name="action" value="search" />
            <label for="search">Topic: </label>
            <input type="text" name="search" placeholder="Search..." value="${searchKeyword}" />
            <button type="submit">SEARCH</button>
          </form>
        </div>
        <div class="topicContainer">
          <ul>
            <c:forEach var="topic" items="${topics}" varStatus="status">
              <li class="topicItem">
                <a class="topic" href="#">${status.count}. ${topic.name}</a>
                <a class="edit" href="${pageContext.request.contextPath}/topic?action=edit&topicid=${topic.id}">Edit</a>
                <form action="${pageContext.request.contextPath}/topic" method="post">
                  <input type="hidden" name="action" value="delete" />
                  <input type="hidden" name="topicid" value="${topic.id}" />
                  <button class="submit" type="submit"
                    onclick="return confirm('Are you sure you want to delete?');">
                    Delete
                  </button>
                </form>
              </li>
            </c:forEach>
          </ul>
        </div>
      </main>

      <footer class="footer">
        <h3>&copy; Learning Logs</h3>
      </footer>

    </div>
  </body>
</html>
