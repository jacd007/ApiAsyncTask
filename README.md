# ApiAsyncTask

> Author: Darwin E. Castillo D. E-Mail: darwin.c5@gmail.com

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
	        implementation 'com.github.jacd007:ApiAsyncTask:Tag'
	}
 ```
 
 #### Maven
 ```
 	<dependency>
	    <groupId>com.github.jacd007</groupId>
	    <artifactId>ApiAsyncTask</artifactId>
	    <version>Tag</version>
	</dependency>
 ```
 #### sbt
 
 ```
    libraryDependencies += "com.github.jacd007" % "ApiAsyncTask" % "Tag"	
 ```
 
 #### leiningen
 
 ```
    :dependencies [[com.github.jacd007/ApiAsyncTask "Tag"]]	
 ``` 
 ### Step 3. Get Started
  ```
ApiTask apiTask = new ApiTask(getContext());

apiTasl.setUrl("https://your-url.com");
apiTask.setEndpoint("your/enpoint");
apiTask.setMethod("Method");
apiTask.setShownProgress(true);
apiTask.setProgressMessage("Set Message");
apiTask.setTaskComplete((response -> {
	//Code response from Api
}));

apiTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
 ```
 #### Method
	| 	value	|
Get 	| 	"GET"   |
Post	| 	"POST"	|
Put	| 	"PUT" 	|
Delete  |    "DELETE"   |

 
 
 > New Version from 0.1
