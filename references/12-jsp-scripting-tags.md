# JSP Scripting Tags in Detail

### Declaration, Scriptlet, and Expression Tags

> *"Declaration for class-level stuff, scriptlet for logic, expression for printing — that's all three."*

---

## The Three Scripting Tags

| Tag | Syntax | Purpose | Where It Ends Up in the Generated Servlet |
|-----|--------|---------|------------------------------------------|
| **Declaration** | `<%! ... %>` | Declare class-level variables and methods | Outside `_jspService()` — at class level |
| **Scriptlet** | `<% ... %>` | Write Java logic (loops, conditions, etc.) | Inside `_jspService()` — as local code |
| **Expression** | `<%= ... %>` | Print a value to the page | Inside `_jspService()` — as `out.print(...)` |

Understanding **where the code ends up** in the generated servlet is the key difference between these tags.

---

## Declaration Tag — `<%! ... %>`

Declares variables and methods at the **class level** of the generated servlet. These exist for the lifetime of the servlet, not just one request.

```jsp
<%!
    String name = "Deepak";
    int count = 0;
%>

<%!
    public int add(int a, int b) {
        return a + b;
    }
%>
```

In the generated servlet, this becomes:

```java
public class index_jsp extends HttpJspBase {
    String name = "Deepak";      // ← class level
    int count = 0;               // ← class level

    public int add(int a, int b) {  // ← class level method
        return a + b;
    }

    public void _jspService(...) {
        // request handling code goes here
    }
}
```

---

## Scriptlet Tag — `<% ... %>`

Writes Java code inside the `_jspService()` method. Used for logic, local variables, conditions, loops, and any procedural code.

```jsp
<%
    String greeting = "Hello";
    int length = greeting.length();
%>

<%
    if (length > 3) {
        out.print("<p>Long greeting!</p>");
    } else {
        out.print("<p>Short greeting.</p>");
    }
%>

<%
    for (int i = 0; i < 5; i++) {
        out.print("<p>Item " + i + "</p>");
    }
%>
```

Variables declared in scriptlets are **local** to the service method — they're created fresh on every request.

---

## Expression Tag — `<%= ... %>`

Prints a value directly to the page. It's a shorthand for `out.print(...)` — no semicolon needed.

```jsp
<p>Name: <%= name %></p>
<p>Sum: <%= add(10, 20) %></p>
<p>Date: <%= new java.util.Date() %></p>
<p>Random: <%= Math.random() %></p>
```

### Scriptlet print vs Expression tag

These two lines produce the same output:

```jsp
<!-- Scriptlet way -->
<% out.print(name); %>

<!-- Expression way (cleaner) -->
<%= name %>
```

The expression tag is less verbose — use it when you just need to output a value.

---

## Where Each Tag Goes in the Generated Servlet

```java
public class index_jsp extends HttpJspBase {

    // ──── Declaration tag code goes HERE (class level) ────
    String name = "Deepak";

    public int add(int a, int b) {
        return a + b;
    }
    // ──────────────────────────────────────────────────────

    public void _jspService(HttpServletRequest request, HttpServletResponse response) {

        // ──── Scriptlet tag code goes HERE ────
        String greeting = "Hello";
        int length = greeting.length();
        // ──────────────────────────────────────

        // ──── Expression tag code goes HERE (as out.print) ────
        out.print(name);
        out.print(add(10, 20));
        // ──────────────────────────────────────────────────────
    }
}
```

---

## Quick Reference

| What You Want to Do | Use This Tag | Example |
|---------------------|-------------|---------|
| Declare a variable shared across requests | `<%! %>` | `<%! int count = 0; %>` |
| Declare a reusable method | `<%! %>` | `<%! public int add(int a, int b) { return a+b; } %>` |
| Write a loop | `<% %>` | `<% for (int i=0; i<5; i++) { %> ... <% } %>` |
| Write an if/else | `<% %>` | `<% if (x > 0) { %> ... <% } %>` |
| Declare a local variable | `<% %>` | `<% String s = "hello"; %>` |
| Print a variable | `<%= %>` | `<%= name %>` |
| Print a method result | `<%= %>` | `<%= add(2,3) %>` |
| Print the current date | `<%= %>` | `<%= new java.util.Date() %>` |

---

## Key Takeaways

- **Declaration** (`<%! %>`) — class-level variables and methods, exists for the servlet's lifetime
- **Scriptlet** (`<% %>`) — Java logic inside `_jspService()`, local to each request
- **Expression** (`<%= %>`) — shorthand for `out.print()`, no semicolon needed
- The generated servlet file shows exactly where each tag's code is placed
- Expression tags are cleaner than scriptlet `out.print()` for simple output
- In our project, we use **JSTL + EL** instead of scripting tags — but knowing these helps you read older JSP code
