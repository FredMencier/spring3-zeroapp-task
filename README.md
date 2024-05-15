# spring3-zeroapp-task
Sample App With Springboot3

Basic app : Task app with JPA
- 4 enities (Person, Address, Task, Audit)
- Application Managed EntityManager
  - Utilisation du fichier persistence.xml
  - PersistenceHelper pour créer EMF et EM
- Base MySql for Server
- Base H2 in Memory for TU
- WebApp : index.html
- 1 Servlet : TaskSearchServlet
    - search by id (response text/html)
- - 1 Servlet : TaskSearchServlet
- search by id (response application/json)
- 1 Servlet : TaskCreateServlet
    - ajout d'une task
- Logging (Slf4j)
- Properties with .properties file

Reference :

https://www.tutorialspoint.com/servlets/servlets-form-data.htm

https://www.geeksforgeeks.org/spring-boot-interceptor/
