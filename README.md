# ApiAsyncTask

## How to

To get a Git project into your build:

### Step 1. Add the JitPack repository to your build file

 #### Gradle
  Add it in your root build.gradle at the end of repositories:
 ```
 	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```
 
 #### Maven
 ```
 	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
 ```
 #### sbt
 
 ```
    resolvers += "jitpack" at "https://jitpack.io"
 ```
 
 #### leiningen
 
 ```
    :repositories [["jitpack" "https://jitpack.io"]]
 ``` 
 
### Step 2. Add the dependency

#### Gradle
```
 	dependencies {
	        implementation 'com.github.jacd007:DateLibrary:1.0.0'
	}
 ```
 
 #### Maven
 ```
 	<dependency>
	    <groupId>com.github.jacd007</groupId>
	    <artifactId>DateLibrary</artifactId>
	    <version>1.0.0</version>
	</dependency>
 ```
 #### sbt
 
 ```
    libraryDependencies += "com.github.jacd007" % "DateLibrary" % "1.0.0"	
 ```
 
 #### leiningen
 
 ```
    :dependencies [[com.github.jacd007/DateLibrary "1.0.0"]]	
 ``` 
 > New Version from 1.0.0 to 1.0.0.1
