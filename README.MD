



# Minecraft Advertisement API
## Setup:


### As a library in your Maven Project 
Add all the files in the "src/main/java" directory to your project.

Then, you might need to do the following changes to your **pom.xml** file, by adding this code to the 
"\<dependecies> \</dependencies>" section:
	
```xml
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.12</version>
</dependency>
```


 To start the API, you will need to pass the required information in the initializer:
```java
MAA.initialize(JavaPlugin plugin, String domain, String public_key, String secret_key, int user_id);
```
> The parameter "domain" requires the port address of the Node server, which by default is 2000. It should look something like this "yourdomain.com:2000".	

After you've done that you can access all the methods statically as shown below. If you skip this step, the API will not work!
### NodeJS backend setup
Now to NodeJS: open the folder which contains the server and through the command-terminal run `$ npm install package.json` to install the required libraries.
 Open **index.js** and edit the array named: **allowed_ips**. There you will need to add every IP adress of minecraft server using this API. By default the local IP (127.0.0.1) is already allowed, so if the minecraft-server is hosted on the same machine, you don't have to edit anything.
 To start the server run `$ node index.js` 

In the NodeJS-server folder, you'll notice there are two .html files present. You may edit them as you wish, but make sure their names don't change (success.html and error.html).

If you've done everything correctly, when you load the NodeJS server and then the minecraft one, everything should work fine. 

## How to use:
Once everything is setup, we can get into coding! 
### Creating a Challenge
To create a "Challenge", or an adf.ly link, you can use following methods:

```java
MAA.add(Player player)
```
This method returns a string:
```java
Player player = Bukkit.getPlayerExact("Exampleplayer");
String link = MAA.addChallenge(player);
```

Alternatively you may use this method Asynchronously, adding a CreateCallback() parameter.
Example:
```java
final Player player = Bukkit.getPlayerExact("Exampleplayer");
MAA.addChallenge(player, new CreateCallback(){
	public void asyncAddPlayer(String link) {
		//Your code goes in here
	}	
});
```
**Careful**: you can only create one challenge per player!
### Events:
MAA uses the Bukkit Event-API to fetch completed challenges.
To start listening for completed challenges, you will first need to run the `RequestHandler.start()` method and then register a listener for the "ChallengeCompletionEvent":
>The event will automatically remove the completed challenge from the list, thus allowing the API to create a new one for that player.
```java
public class Example implements Listener{
	@EventHandler
	public void onChallengeComplete(ChallengeCompletionEvent e){
		e.getPlayer().sendMessage("You completed the challenge!");
	}
}
```
Then register this listener in you onEnable() method:
```java
onEnable(){
	...//your other code
	Bukkit.getPluginManager().registerEvents(new Example(), this);
	RequestHandler.start();
	...//your other code
}
```

 In case you don't like working with events, you can use the MAA.checkPlayer() method. **CAREFUL:** you should either work with events or with this method, as using both may cause some issues. 
Examples:
```java
	Player player = Bukkit.getPlayerExact("ExamplePlayer");
	boolean bool = MAA.checkPlayer(player);
```
 There is also the async variant:
```java
	final Player player = Bukkit.getPlayerExact("ExamplePlayer");
	MAA.checkPlayer(player, new CheckCallback(){
		public void asyncCheckPlayer(boolean bool){
			//your code goes in here
		}
	});
```
This method returns *false* whenever the challenge has not been completed.
Otherwise, wheter the challenge has been completed or there isn't one, the method will return always *true*.

**Rembember**: the `MAA.checkPlayer()` method, unlike `MAA.hasCompleted()` (which you will learn more about in the next paragraph), always makes an HTTP request to the NodeJS server!

### Has the challenge been completed?
You of course may need to check if the player has completed a challenge, and for that there's the MAA.hasCompleted() method, example:
```java
	Player player = Bukkit.getPlayerExact("ExamplePlayer");
	boolean bool = MAA.hasCompleted(player);
```
This method returns *false* whenever the challenge has not been completed (i.e. while the ChallengeCompletionEvent has not fired for a certain player, after you created a challenge). 
Otherwise, wheter the challenge has been completed or there isn't one, the method will return always *true*.

#### Error handling
I haven't implemented any sophisticated error handling code, but in case something goes wrong in the MAA.addChallenge method, an error-string is returned. 
There are three types: 
- "Error 1" -> problem with the NodeJS server.
- "Error 2" -> problem with Adf.ly API.
- "Error 3" -> a challenge already exists for that player.


### Suggestions
I really suggest putting the `MAA.clean()` method in `onDisable()`. This will remove any unused links of created challenges on the Adf.ly website, which otherwise you would have to do on your own.

I strongly advise against using functions which I have not listed in this README.MD file, as they are for internal API use, and messing with them could cause bugs and errors. 

## Other information
### Example:
Follow this [link](https://youtu.be/FjUq4_Bzpd4) to see an example of how one could use this API. In the [snipplet](https://github.com/hesperiusproject/MAA_OLD/tree/master/Snipplet/src) section, you will find the code used in the video.

### Known issues:
I suggest not reloading the plugin once it is enabled on the server for the following reason: allthough the MAA will still work fine, it will start outputting an error on every HTTP request in the console. This bug  is not caused by the plugin itself, but by the libraries it uses.
### Get in touch
If you have a problem or suggestion, you can email my at info@hesperius.it or send a PM to my discord **Gio#4621** .

