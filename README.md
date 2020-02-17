# Remote screenshot artifact

## Purpose
User is able to download the screenshot of a web URL provided as an input and store the screenshot of the page in to a directory. Please note that I would just need a Standalone java class (Nothing fancy).
100 % test coverage is a must.
Use of Java Design Patterns (wherever possible) is a plus.

## Solution
Four different approaches were considered in the beginning:

- jsoup: Java HTML Parser, it provides a very convenient API for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods. Unfortunately, printing the HTML to a canvas is not really supported, 
hence this is not an option. 
- Selenium Client: Pretty standard framework which is open source and free of cost. This is the chosen option since it allows to really simulate user interaction and take the HTML as a screenshot type.
- JXBrowser: It is a Chromium-based Swing/JavaFX web browser which allows to embed a web browser in java applications. 
This is the enterprise solution which is the best option, but there's a cost for the license.
The best part of this library is that it allows multi-threaded execution. 
Unfortunately, for the purpose of this project the cost is out of scope.
- Java plain implementation based in the standard Swing Library, this is not a good option since some web page areas might load the info as the user keeps scrolling, which is something really difficult to simulate.

As mentioned before, this program is based on **Selenium Client** along with AShot library.
It relies on having Mozilla Firefox installed in the running computer, the reason is because it is free and cross-platform.

### Linux/Mac/Windows requirements
- Install Mozilla Firefox. Version >= 60

#### MAC users known problems
macOS 10.15 (Catalina):
Due to the recent requirement from Apple that all programs must be notarized, geckodriver will not work on Catalina if you manually download it through another notarized program, such as Firefox.
Whilst we are working on a repackaging fix for this problem, you can find more details on how to work around this issue in the [macOS notarization section](https://firefox-source-docs.mozilla.org/testing/geckodriver/Notarization.html)  of the documentation.

_Source: https://github.com/mozilla/geckodriver/releases_

## Run
On Unix-like systems:

    ./mvnw clean package && java -jar target/remote-screenshot-jar-with-dependencies.jar

On Windows systems:

    ./mvnw.cmd clean package && java -jar target/remote-screenshot-jar-with-dependencies.jar

## Known issues
Even though the development is cross-platform, on mac there are some situations in which the screenshot looks cropped.
It is recommended to run the example on windows environments.

## Additional resources
JXBrowser is an enterprise solutions that allows to run on multi-threaded environments
https://jxbrowser.support.teamdev.com/support/solutions/articles/9000012884-saving-web-page-to-png-image
