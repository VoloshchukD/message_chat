Develop a simple Java application that incorporates embedded tomcat libraries.
This application should allow to open a web-page on 9090 port.

Furthermore, utilize web-sockets to create basic chat functionality,
enabling users to send messages and implement asynchronous refresh  when new message available.

Add http session support. If I open another tab
in the browser and send a message the system should understand
that it is still me (even though it is a different websocket).

And in ui: my messages should be drawn on the right, others on the left in the chat window.
- java core, no spring, no annotations (e.g. ServerEndpoint)
- embedded tomcat jars
- any ide
- html/js/jquery
- AVOID COPY/PASTE - you sign under each line