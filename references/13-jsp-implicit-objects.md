# JSP Implicit Objects

### Objects You Get for Free in Every JSP Page

> *"In a servlet you create them yourself. In a JSP they're already there."*

---

## What Are Implicit Objects?

In a servlet, you have to manually get objects like `PrintWriter`, `HttpSession`, etc.:

```java
// Servlet — you create these yourself
PrintWriter out = response.getWriter();
HttpSession session = request.getSession();
```

In a JSP page, these objects are **already available** — the container creates them for you:

```jsp
<%
    // JSP — just use them directly
    out.print("Hello");
    session.setAttribute("name", "Deepak");
%>
```

---

## The Nine Implicit Objects

| Object | Type | What It Does | Servlet Equivalent |
|--------|------|-------------|-------------------|
| `out` | `JspWriter` | Write output to the browser | `response.getWriter()` |
| `request` | `HttpServletRequest` | Read form data, headers, parameters | Method parameter |
| `response` | `HttpServletResponse` | Send response, set headers, redirect | Method parameter |
| `session` | `HttpSession` | Store data across multiple requests | `request.getSession()` |
| `application` | `ServletContext` | App-wide shared data | `getServletContext()` |
| `config` | `ServletConfig` | Servlet configuration | `getServletConfig()` |
| `pageContext` | `PageContext` | Access to all scopes and other objects | No direct equivalent |
| `page` | `Object` | Reference to the current JSP page | `this` |
| `exception` | `Throwable` | Error info (only on error pages) | No direct equivalent |

The four you'll use most: **`out`**, **`request`**, **`response`**, **`session`**.

---

## Servlet vs JSP — Side by Side

### Printing output

```java
// Servlet
PrintWriter out = response.getWriter();
out.print("Hello");
```

```jsp
<!-- JSP — out is implicit -->
<% out.print("Hello"); %>
```

### Reading form data

```java
// Servlet
String name = request.getParameter("name");
```

```jsp
<!-- JSP — request is implicit -->
<% String name = request.getParameter("name"); %>
```

### Using sessions

```java
// Servlet
HttpSession session = request.getSession();
session.setAttribute("score", "100");
```

```jsp
<!-- JSP — session is implicit -->
<% session.setAttribute("score", "100"); %>
```

---

## Practical Example — Form Handling with Implicit Objects

### index.jsp — the form

```jsp
<form action="output.jsp" method="post">
    <input type="text" name="userName" />
    <button type="submit">Submit</button>
</form>
<%
    session.setAttribute("score", "Smart Programming");
%>
```

### output.jsp — reading the data

```jsp
<%
    String name = request.getParameter("userName");
    String score = (String) session.getAttribute("score");
%>
<p>Name: <%= name %></p>
<p>Score: <%= score %></p>
```

| What Happens | Implicit Object Used |
|-------------|---------------------|
| Read form field value | `request.getParameter("userName")` |
| Read session attribute | `session.getAttribute("score")` |
| Print to the page | `out` (via `<%= %>` expression tag) |

---

## Watch Out: Casting getAttribute()

`session.getAttribute()` returns `Object`, so you must **cast** it:

```jsp
<%
    // This won't compile — getAttribute returns Object
    String name = session.getAttribute("name");

    // This works — cast to String
    String name = (String) session.getAttribute("name");
%>
```

---

## Key Takeaways

- JSP provides **nine implicit objects** that are available without creating them
- The most commonly used are `out`, `request`, `response`, and `session`
- These are the same objects you manually create in servlets — JSP just gives them to you automatically
- `session.getAttribute()` returns `Object` — always cast it to the expected type
- Implicit objects are only available in **JSP pages**, not in servlet classes
