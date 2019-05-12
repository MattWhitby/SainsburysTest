# SainsburysTest
This repository contains the Sainsbury's Java Test

## How to clone and compile
1. git clone https://github.com/MattWhitby/SainsburysTest.git
2. cd SainsburysTest/sainsburys-scraper
3. mvn clean install
4. mvn compile
5. mvn exec:java -Dexec.mainClass=com.scraper.app.ScraperApplication

Unit tests will be run when `mvn clean install` is performed. Can also run `mvn test`.

## Installing Maven tips
If you're installing Apache Maven on Windows please take a look at these instructions: https://www.mkyong.com/maven/how-to-install-maven-in-windows/

To install maven on Ubuntu do the following: `sudo apt install maven`

To install on other Linux distributions, follow these instruction: https://www.javahelps.com/2017/10/install-apache-maven-on-linux.html
