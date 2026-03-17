<%-- ============================================================
     TODO 4: JSP Directives
     ============================================================
     Add TWO directives at the very top of this file (before the
     DOCTYPE declaration):

     1. Page directive:
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>

     2. JSTL taglib directive:
        <%@ taglib prefix="c" uri="jakarta.tags.core" %>

     Same pattern as topiclist.jsp — every JSP page needs these.

     The complete code:

       <%@ page contentType="text/html;charset=UTF-8" language="java" %>
       <%@ taglib prefix="c" uri="jakarta.tags.core" %>
     ============================================================ --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

  <%-- ============================================================
       TODO 5: Head Section + Header + Navbar
       ============================================================
       Same pattern as topiclist.jsp, but with different CSS file
       and different navbar text.

       1. Head — link main.css + topic-add.css (not topic-list.css)

       2. Header — identical to topiclist.jsp

       3. Navbar — different text:
          - First li: Back link to topic list
          - Second li: Dynamic title using EL ternary operator
          - Third li: empty

       CONCEPT: The EL ternary operator works like Java's:
         ${condition ? valueIfTrue : valueIfFalse}

       ${empty topic ? 'Add Topic' : 'Edit Topic'} checks if a
       "topic" attribute exists in the request. If the servlet
       set a topic (for editing), it shows "Edit Topic". If not
       (adding new), it shows "Add Topic". One JSP handles both!

       The complete structure:

         <head>
           <meta charset="UTF-8" />
           <meta name="viewport" content="width=device-width, initial-scale=1.0" />
           <title>Learning Log</title>
           <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css" />
           <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/topic-add.css" />
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
             <li><a href="${pageContext.request.contextPath}/topic">&lt; Back (Topic List)</a></li>
             <li><p>${empty topic ? 'Add Topic' : 'Edit Topic'}</p></li>
             <li></li>
           </ul>
         </nav>

       Wireframe: See wireframes/topic-add.png
       ============================================================ --%>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Learning Log</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/topic-add.css" />
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
          <li><a href="${pageContext.request.contextPath}/topic">&lt; Back (Topic List)</a></li>
          <li><p>${empty topic ? 'Add Topic' : 'Edit Topic'}</p></li>
          <li></li>
        </ul>
      </nav>

      <%-- ============================================================
           TODO 6: Form Content with EL Expressions
           ============================================================
           Build the form that submits to the TopicServlet via POST.

           Key differences from the Week 3 HTML form:
           1. form action uses contextPath
           2. Hidden input sends the action ("add" or "edit")
           3. Error message displayed conditionally with <c:if>
           4. Input value pre-filled for edit mode

           CONCEPTS:
           - <c:if test="${not empty error}"> only renders its content
             if the "error" attribute exists (set by servlet on failure)
           - ${empty topic ? 'add' : 'edit'} sets the hidden action
             based on whether we're adding or editing
           - <c:if test="${not empty topic}"> adds a hidden topicid
             field only in edit mode (the servlet needs the ID)
           - value="${empty topic ? '' : topic.name}" pre-fills the
             input with the existing topic name when editing

           The complete content structure:

             <main class="content">
               <div class="topicContainer">
                 <c:if test="${not empty error}">
                   <p style="color: red; text-align: center; padding: 5px;">${error}</p>
                 </c:if>
                 <form action="${pageContext.request.contextPath}/topic" method="post">
                   <input type="hidden" name="action" value="${empty topic ? 'add' : 'edit'}" />
                   <c:if test="${not empty topic}">
                     <input type="hidden" name="topicid" value="${topic.id}" />
                   </c:if>
                   <div class="topicItem">
                     <label for="topic">Topic: </label>
                     <input type="text" placeholder="Topic Name" name="topic"
                            id="topic" value="${empty topic ? '' : topic.name}" />
                   </div>
                   <div class="submit">
                     <button type="submit">Save</button>
                   </div>
                 </form>
               </div>
             </main>
           ============================================================ --%>
      <main class="content">
        <div class="topicContainer">
          <c:if test="${not empty error}">
            <p style="color: red; text-align: center; padding: 5px;">${error}</p>
          </c:if>
          <form action="${pageContext.request.contextPath}/topic" method="post">
            <input type="hidden" name="action" value="${empty topic ? 'add' : 'edit'}" />
            <c:if test="${not empty topic}">
              <input type="hidden" name="topicid" value="${topic.id}" />
            </c:if>
            <div class="topicItem">
              <label for="topic">Topic: </label>
              <input type="text" placeholder="Topic Name" name="topic"
                     id="topic" value="${empty topic ? '' : topic.name}" />
            </div>
            <div class="submit">
              <button type="submit">Save</button>
            </div>
          </form>
        </div>
      </main>

      <footer class="footer">
        <h3>&copy; Learning Logs</h3>
      </footer>

    </div>
  </body>
</html>
