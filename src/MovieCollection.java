import javax.lang.model.type.ArrayType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> castsList;
    private ArrayList<String> genreList;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        castsList = castsList();
        genreList = genreList();
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortResultsString(ArrayList<String> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            String temp = listToSort.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private ArrayList<String> castsList() {
        ArrayList<String> casts = new ArrayList<String>();
        for (int i = 0; i != movies.size(); i++) {
            String listOfCast = movies.get(i).getCast();
            while (listOfCast.indexOf("|") != -1) {
                String cast = listOfCast.substring(0, listOfCast.indexOf("|"));
                if (!(casts.contains(cast))) {
                    casts.add(cast);
                }
                listOfCast = listOfCast.substring(listOfCast.indexOf("|") + 1);
            }
        }
        sortResultsString(casts);
        return casts;
    }

    private void searchCast()
    {
        ArrayList<String> casts = castsList;
        System.out.print("Please enter a cast name: ");
        String cast = scanner.nextLine();
        String castTest = cast.toLowerCase();
        ArrayList<String> castChosen = new ArrayList<String>();
        for (int i = 0; i != casts.size(); i++) {
            String name = casts.get(i).toLowerCase();
            if (name.indexOf(castTest) != -1) {
                castChosen.add(casts.get(i));
            }
        }
        System.out.println("Here are the following casts with the name " + cast + ":");
        for (int i = 0; i != castChosen.size(); i++) {
            String name = castChosen.get(i);
            int choiceNum = i + 1;
            System.out.println(choiceNum + "." + name);
        }
        System.out.println("Which cast would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String selectedCast = castChosen.get(choice - 1);
        ArrayList<Movie> moviesIsIn = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++) {
            String castMovie = movies.get(i).getCast();
            if (castMovie.indexOf(selectedCast) != -1) {
                moviesIsIn.add(movies.get(i));
            }
        }
        sortResults(moviesIsIn);
        System.out.println(selectedCast + " is involved with the following movies:");
        for (int i = 0; i != moviesIsIn.size(); i++) {
            String movieTitle = moviesIsIn.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println(choiceNum + "." + movieTitle);
        }
        System.out.println("Which movie do you want to learn more about?\nEnter a number: ");
        choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = moviesIsIn.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 0; i != movies.size(); i++) {
            String keywords = movies.get(i).getKeywords();
            if (keywords.indexOf(searchTerm) != -1) {
                results.add(movies.get(i));
            }
        }
        sortResults(results);
        for (int i = 0; i != results.size(); i++) {
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println(choiceNum + "." + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private ArrayList<String> genreList() {
        ArrayList<String> genres = new ArrayList<String>();
        for (int i = 0; i != movies.size(); i++) {
            String listOfGenre = movies.get(i).getGenres();
            while (listOfGenre.indexOf("|") != -1) {
                String genre = listOfGenre.substring(0, listOfGenre.indexOf("|"));
                if (!(genres.contains(genre))) {
                    genres.add(genre);
                }
                listOfGenre = listOfGenre.substring(listOfGenre.indexOf("|") + 1);
            }
        }
        sortResultsString(genres);
        return genres;
    }

    private void listGenres()
    {
        ArrayList<String> genres = genreList;
        System.out.println("These are the possible available genres: ");
        for (int i = 0; i != genres.size(); i++) {
            String genre = genres.get(i);
            int choiceNum = i + 1;
            System.out.println(choiceNum + "." + genre);
        }
        System.out.println("Which genre would you like to search for?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String selectedGenre = genres.get(choice - 1);
        ArrayList<Movie> moviesInGenre = new ArrayList<Movie>();
        for (int i = 0; i != movies.size(); i++) {
            String genresHas = movies.get(i).getGenres();
            if (genresHas.indexOf(selectedGenre) != -1) {
                moviesInGenre.add(movies.get(i));
            }
        }
        sortResults(moviesInGenre);
        System.out.println("The genre, " + selectedGenre + ", apply for the following movies:");
        for (int i = 0; i != moviesInGenre.size(); i++) {
            String movieTitle = moviesInGenre.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println(choiceNum + "." + movieTitle);
        }
        System.out.println("Which movie would you like to search for?");
        System.out.print("Enter number: ");
        choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = moviesInGenre.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Movie> temporary = new ArrayList<>();
        temporary.addAll(movies);
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        while (movieList.size() < 50) {
            Movie bestMovie = temporary.get(0);
            double highestRate = bestMovie.getUserRating();
            int bestMovieIndex = 0;
            for (int i = 1; i != temporary.size(); i++) {
                Movie movie = temporary.get(i);
                if (movie.getUserRating() > highestRate) {
                    bestMovie = movie;
                    highestRate = movie.getUserRating();
                    bestMovieIndex = i;
                }
            }
            movieList.add(bestMovie);
            temporary.remove(bestMovieIndex);
        }
        for (int i = 1; i != movieList.size(); i++)
        {
            Movie temp = movieList.get(i);
            double tempRate = temp.getUserRating();
            int possibleIndex = i;
            while (possibleIndex > 0 && tempRate > movieList.get(possibleIndex - 1).getUserRating()) {
                movieList.set(possibleIndex, movieList.get(possibleIndex - 1));
                possibleIndex--;
            }
            movieList.set(possibleIndex, temp);
        }
        System.out.println("These are the top 50 rated movies: ");
        for (int i = 0; i != movieList.size(); i++) {
            Movie movie = movieList.get(i);
            int choiceNum = i + 1;
            System.out.println(choiceNum + "." + movie.getTitle() + ": " + movie.getUserRating());
        }
        System.out.println("Which movie would you like to search for?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = movieList.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> temporary = new ArrayList<>();
        temporary.addAll(movies);
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        while (movieList.size() < 50) {
            Movie bestMovie = temporary.get(0);
            int highestRev = bestMovie.getRevenue();
            int bestMovieIndex = 0;
            for (int i = 1; i != temporary.size(); i++) {
                Movie movie = temporary.get(i);
                if (movie.getRevenue() > highestRev) {
                    bestMovie = movie;
                    highestRev = movie.getRevenue();
                    bestMovieIndex = i;
                }
            }
            movieList.add(bestMovie);
            temporary.remove(bestMovieIndex);
        }
        for (int i = 1; i != movieList.size(); i++)
        {
            Movie temp = movieList.get(i);
            double tempRate = temp.getRevenue();
            int possibleIndex = i;
            while (possibleIndex > 0 && tempRate > movieList.get(possibleIndex - 1).getRevenue()) {
                movieList.set(possibleIndex, movieList.get(possibleIndex - 1));
                possibleIndex--;
            }
            movieList.set(possibleIndex, temp);
        }
        System.out.println("These are the top 50 rated movies: ");
        for (int i = 0; i != movieList.size(); i++) {
            Movie movie = movieList.get(i);
            int choiceNum = i + 1;
            System.out.println(choiceNum + "." + movie.getTitle() + ": " + movie.getRevenue());
        }
        System.out.println("Which movie would you like to search for?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = movieList.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}