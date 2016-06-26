# Movie App

Hey, this app will take data from the MovieDB API and show the posters in the Main screen. On each click of the poster, the details of the movie will show. You can also sort the movies by popularity as well as user ratings.

> This is a Udacity project [for android nanodegree](https://www.udacity.com/)
> For using the app, you must generate the MOVIEDB API Key and insert it into the code.

You can get the API Key from [here](https://www.themoviedb.org/documentation/api)


The code below is where you have to insert the api key:
```java
public class MainActivity extends AppCompatActivity {
    //*Please use your own API Key in the below URL's
    private final String popularurl="http://api.themoviedb.org/3/movie/popular?api_key=";//here*
    private final String highestRatedUrl="http://api.themoviedb.org/3/movie/top_rated?api_key=";//and here*
```
[MainActivity.java](/app/src/main/java/com/example/dishantkaushik/movieapp/MainActivity.java)


Version 2.0
> Created a local content provider. Which means that you can now save your favourite movies locally
