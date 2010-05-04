Welcome to MuleForge and congratulations on creating a new MuleForge project.

As a Muleforge developer or Despot you are probably keen to get your new project started.

Getting Set Up
==============
MuleForge uses Apache Maven for working with project source code.  If you do not have the latest Apache Maven installed,
you can download it here: http://maven.apache.org/download.

You need to configure Maven for MuleForge.  There are a few simple steps outlined here: http://tinyurl.com/5vfwxc

Next, you need to add some settings for Maven to work with MuleForge.  It only takes a minute, the steps are described
here: http://www.mulesource.org/display/MULEFORGE/Environment+Setup

Creating/Migrating your Code
============================
You can create new project code using one of the Mule project wizards (Maven Archetypes) to create template code
including test cases. Depending on the type of project you want to create, you can run one of the following mvn commands
from the project root directory -

a) Transport:
     mvn mule-transport-achetype:create -DmuleVersion=2.2.1
                             -DprojectProperties=muleforge-project.properties

b) Module:
     mvn mule-module-achetype:create -DmuleVersion=2.2.1
                             -DprojectProperties=muleforge-project.properties

c) Example:
     mvn mule-example-achetype:create -DmuleVersion=2.2.1
                             -DprojectProperties=muleforge-project.properties

if you have an existing project, you need to make sure of a few things -
a) The existing project needs to follow the Maven recommended project structure.
     See: http://tinyurl.com/amty9.
b) If your project is already set up with Maven, we still recommend that the project POM.xml file used is the one
defined by the Mule project wizards.  The recommended approach here is to-
    i) copy the files over to the project root
   ii) rename your existing pom.xml file to pom.backup
  iii) run one of the project wizards above (depending on the type of project)
  iv) when asked "Are you migrating an existing Maven project to MuleForge", select [y]
   v) Answer [y] when asked to create an assembly file and pom file
  vi) When the wizard finishes, copy any non-Mule dependencies from pom.backup to pom.xml
 vii) Add entries to the assembly file for non-Mule dependencies.

The version of your project is stored at the top of the project pom.xml file.  While in development the project version
should be x.y-SNAPSHOT where 'x' is the major and 'y' the minor version number.

It is good practice on MuleForge to set the project version to the version of Mule you are using. If you are using
Mule 2.1.2 your project version should be 2.1.2-SNAPSHOT

If you have imported existing project code, it is important that you follow the coding guidelines around package names,
license header and coding styles. See coding style information here:
http://www.mulesource.org/display/MULECDEV/Coding+Conventions

Now you can check your code into SVN using the  the following:

svn ci -m "Initial checkin"

Using an IDE
==========
Once you have your project project set up with Maven you can now create project files using Maven.

If you are using the the Mule IDE or Eclipse you can generate the project files using:

mvn eclipse:eclipse

For more information about setting up see: http://tinyurl.com/6dqnk7

If you are using IntelliJ IDEA you can run the following command:

mvn idea:idea

For more information and tips for working with IntelliJ see: http://tinyurl.com/5u9eqb



Well, thats enough for you to get going, for information about working with MuleForge, source code and creating
releases see the Getting started guide: http://www.mulesource.org/display/MULEFORGE/Getting+Started

Remember, if you need help, there is a wealth of documentation here: http://www.mulesource.org/display/MULEFORGE.  You
can also get help from MuleForge support, support@muleforge.org.

Happy Muling!

The Mule Team
