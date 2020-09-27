# webcrawler

README (also in github)


Source Code: @GitHub: https://github.com/tegasan/webcrawler

Application dependencies
	- java 1.8
	- Spring Boot
	- Crawler4j
	- H2 (in Memory database)
	- JPARepository
	- Thymeleaf (for gui rendering)
	- Maven

How to run or execute the program (java is required to be installed in your machine)
	- download and unzip the attachment (SpringDemo-0.0.1-SNAPSHOT.jar.zip)
	- using command prompt (windows) or Terminal (MacOs)
	- go to the directory where the file being unzipped
	- run command below: 

		java -jar SpringDemo-0.0.1-SNAPSHOT.jar
		
	- open browser and run: http://localhost:8080/pages

How to run it in eclipse
	- clone the project
	- import maven project (pom.xml)
	- run as spring boot applications
	- open browser and run: http://localhost:8080/pages

Improvement:
	- form input field validation
	- add more crawling configurable input
	- add swagger+api doc for documentation
	- using pesistants database (mysql) for data discovery and analysis
	- make url result as a link
	- implement pagination and hateoas for the result list.
