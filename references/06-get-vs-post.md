# GET vs POST

### The Two HTTP Methods You'll Use Most

> *"GET puts data in the URL, POST hides it in the body — that one difference changes everything."*

---

## What Are HTTP Methods?

HTTP methods (also called **HTTP verbs**) define what action the client wants to perform on a server resource. There are several (PUT, DELETE, HEAD, etc.) but the two you'll use most are:

- **GET** — retrieve/read something
- **POST** — send/submit something

---

## The Key Difference: Where Does the Data Go?

### GET — data in the URL

```
http://localhost:8081/learning-logs/topic?name1=deepak&pass1=deepak123
```

The form data is **appended to the URL** as query parameters — visible to everyone.

### POST — data in the request body

```
http://localhost:8081/learning-logs/topic
```

The form data is sent in the **HTTP request body** — not visible in the URL.

---

## Side-by-Side Comparison

| Aspect | GET | POST |
|--------|-----|------|
| **Data location** | URL query string (visible) | Request body (hidden) |
| **Security** | Low — data exposed in URL, browser history, server logs | Higher — data not in URL |
| **Speed** | Faster | Slightly slower |
| **Data size** | Limited by URL length (~2048 chars) | No practical limit |
| **File uploads** | Not possible | Supported |
| **Use case** | Loading pages, search queries | Form submissions, login, sensitive data |
| **Servlet method** | `doGet()` | `doPost()` |

---

## How It Looks in HTML

The `method` attribute on the `<form>` tag decides which HTTP method is used:

```html
<!-- GET request (default if method is omitted) -->
<form action="topic" method="get">
    <input type="text" name="search" />
    <button type="submit">Search</button>
</form>

<!-- POST request -->
<form action="topic" method="post">
    <input type="text" name="topicName" />
    <input type="password" name="password" />
    <button type="submit">Submit</button>
</form>
```

---

## How It Looks in the Servlet

Each HTTP method maps to a different servlet method:

```java
@WebServlet("/topic")
public class TopicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // handles GET requests — loading pages
        String search = request.getParameter("search");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // handles POST requests — form submissions
        String name = request.getParameter("topicName");
    }
}
```

---

## What Happens If the Method Doesn't Match?

If the form sends a **POST** request but the servlet only has `doGet()`:

```
HTTP 405 — Method Not Allowed
```

The fix: implement the matching method, or implement both if your servlet handles both types of request.

---

## When to Use Which

| Scenario | Use |
|----------|-----|
| Loading a page (topic list, add form) | **GET** |
| Submitting a form (add topic, login) | **POST** |
| Sending passwords or sensitive data | **POST** (never GET) |
| Bookmarkable/shareable URLs | **GET** |
| Search with query parameters | **GET** |
| Uploading files | **POST** |

---

## Key Takeaways

- **GET** sends data in the URL — fast but visible, use for page loads and searches
- **POST** sends data in the request body — hidden from the URL, use for form submissions
- Never send sensitive data (passwords, emails) via GET
- `doGet()` handles GET requests, `doPost()` handles POST requests
- If the wrong method is sent to a servlet, you get a **405 Method Not Allowed** error
- A servlet can implement both `doGet()` and `doPost()` to handle both types
