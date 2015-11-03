# Development manual

##Source Control##
If you don't already have git installed on your machine you can install it by running this command in the command line: `sudo apt-get install git-core`.

Next you have to clone the git repository : `git clone https://github.com/smarties66/Hugb_TicTacToe.git`.

##Build Environment##
You will need to clone this repository to Ubuntu Linux machine or Mac OS.

Other things you will need are:
- [Oraclejdk8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Gradle](http://gradle.org/gradle-download/)
- [Travis CI] ()
- [Heroku] ()

You will need Oracle JDK 8 to be able to compile the project.

Gradle will handle installations of all dependecies.

Travis is our continous integration tool, that runs build and test files that are in the project.

Heroku is for continous delivery tool that deploys the project if all Travis builds and tests pass.


