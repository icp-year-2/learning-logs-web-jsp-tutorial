# JSP Tags Overview

### The Three Types of Tags in JSP

> *"Scripting tags let you write Java. Directive tags configure the page. Action tags do things."*

---

## The Three Categories

| Category | Purpose | Example |
|----------|---------|---------|
| **Scripting Tags** | Embed Java code directly in the JSP | `<% int x = 5; %>` |
| **Directive Tags** | Give instructions to the JSP container | `<%@ page import="java.util.List" %>` |
| **Action Tags** | Perform tasks like forwarding or including pages | `<jsp:forward page="profile.jsp" />` |

---

## 1. Scripting Tags — Embedding Java

Three subtypes, each with different syntax:

| Tag | Syntax | Purpose | Example |
|-----|--------|---------|---------|
| **Scriptlet** | `<% ... %>` | Write Java code (logic, loops, etc.) | `<% String name = "Deepak"; %>` |
| **Declaration** | `<%! ... %>` | Declare variables or methods | `<%! int count = 0; %>` |
| **Expression** | `<%= ... %>` | Output a value to the page | `<%= name %>` |

### Example — all three together

```jsp
<%! String name = "Deepak"; %>

<% int length = name.length(); %>

<p>Name: <%= name %></p>
<p>Length: <%= length %></p>
```

| Tag | What It Does Here |
|-----|------------------|
| `<%! ... %>` | Declares the `name` variable |
| `<% ... %>` | Runs Java logic to calculate length |
| `<%= ... %>` | Outputs the values into the HTML |

---

## 2. Directive Tags — Configuring the Page

Directive tags tell the JSP container how to process the page. They don't produce output.

Syntax: `<%@ directive attribute="value" %>`

| Directive | Purpose | Example |
|-----------|---------|---------|
| **page** | Configure page settings — imports, content type, error page | `<%@ page import="java.util.List" %>` |
| **include** | Include another file's content at translation time | `<%@ include file="header.jsp" %>` |
| **taglib** | Declare a tag library (like JSTL) for use in the page | `<%@ taglib prefix="c" uri="..." %>` |

### Common page directive attributes

```jsp
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, java.util.ArrayList" %>
<%@ page errorPage="error.jsp" %>
```

---

## 3. Action Tags — Doing Things

Action tags perform specific tasks using XML-style syntax.

| Tag | Purpose | Example |
|-----|---------|---------|
| `<jsp:include>` | Include another page at runtime | `<jsp:include page="header.jsp" />` |
| `<jsp:forward>` | Forward the request to another resource | `<jsp:forward page="profile.jsp" />` |
| `<jsp:useBean>` | Create or find a JavaBean | `<jsp:useBean id="topic" class="com.learninglogs.model.Topic" />` |
| `<jsp:setProperty>` | Set a property on a bean | `<jsp:setProperty name="topic" property="name" value="Java" />` |
| `<jsp:getProperty>` | Get a property from a bean | `<jsp:getProperty name="topic" property="name" />` |

---

## Extended Tag Libraries

Beyond the built-in tags, JSP supports external tag libraries that you declare with the **taglib** directive:

| Library | What It Provides | Declaration |
|---------|-----------------|-------------|
| **JSTL** (JSP Standard Tag Library) | Loops, conditionals, formatting | `<%@ taglib prefix="c" uri="jakarta.tags.core" %>` |
| **Spring MVC Tags** | Form binding, validation | `<%@ taglib prefix="form" uri="..." %>` |

We use **JSTL** in this module — it replaces most scripting tags with cleaner, HTML-like syntax.

---

## Scripting Tags vs JSTL — What We Actually Use

While scripting tags work, they mix Java into HTML. **JSTL + Expression Language (EL)** is the modern approach:

| Approach | Show a variable | Loop through a list |
|----------|----------------|-------------------|
| **Scriptlet** | `<%= name %>` | `<% for (Topic t : topics) { %> ... <% } %>` |
| **JSTL + EL** | `${name}` | `<c:forEach var="t" items="${topics}"> ... </c:forEach>` |

We use JSTL + EL in our project — scriptlets are shown here so you can recognise them in older code.

---

## Key Takeaways

- JSP has three tag categories: **Scripting** (Java code), **Directive** (page config), **Action** (tasks)
- Scripting tags have three subtypes: **scriptlet** `<% %>`, **declaration** `<%! %>`, **expression** `<%= %>`
- Directive tags configure the page: **page**, **include**, **taglib**
- Action tags use XML syntax for tasks like forwarding and bean management
- JSTL is an external tag library that provides cleaner alternatives to scripting tags
- We use **JSTL + EL** instead of scriptlets in our project
