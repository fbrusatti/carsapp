HOLA!Cars web app
============
It's a web application used in a course of [Software Engineering] [se-site] in the [UNRC] [unrc] to teach concepts as 

  - Agile development process
  - Test Driven Development
  - Web Server
  - REST (REpresentational State Transfer)
  - and more...

Version
=======
0.1

Stack
=====
Cars app will use the following open source tools:

* [Java] - Java programming language.
* [Maven] - Software project management, it can manage a project's build.
* [Junit] - Framework to write repeatable tests.
* [MySql] - Open source database.
* [Activejdbc] - Java implementation of Active Record design pattern.


Installation
============
  - Install MySql
  - Create database
  ```
    $ mysql -u root
    mysql> create database carsapp_development;
  ```

  - Create users table
  ``` $ mysql -u root carsapp_development < config/schema.sql ```
  
  - Run application
  ``` ./run.sh ```

  more info in [Mea]
  

Good luck to all :)


License
=======

The MIT License (MIT)

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


  [unrc]: http://dc.exa.unrc.edu.ar/ 
  [se-site]: http://dc.exa.unrc.edu.ar/principal/node/57
  [Java]:  http://www.java.com/en/
  [Junit]: http://junit.org/
  [MySql]: http://www.mysql.com/
  [Activejdbc]: https://github.com/javalite/activejdbc
  [Maven]: http://maven.apache.org/
  [Mea]: http://mea-docta-ignorantia.appspot.com/posts/project_2014.html

