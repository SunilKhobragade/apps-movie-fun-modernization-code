package org.superbiz.moviefun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.superbiz.moviefun.albumsapi.AlbumFixtures;
import org.superbiz.moviefun.albumsapi.AlbumInfo;
import org.superbiz.moviefun.albumsapi.AlbumsClient;
import org.superbiz.moviefun.moviesapi.MovieFixtures;
import org.superbiz.moviefun.moviesapi.MovieInfo;
import org.superbiz.moviefun.moviesapi.MoviesClient;

import java.util.Map;

@Controller
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    //private final MoviesBean moviesBean;
    private final MoviesClient moviesClient;
    private final AlbumsClient albumsClient;
    //private final AlbumsBean albumsBean;
    private final MovieFixtures movieFixtures;
    private final AlbumFixtures albumFixtures;

    public HomeController(MoviesClient moviesClient, AlbumsClient albumsClient, MovieFixtures movieFixtures, AlbumFixtures albumFixtures) {
        this.moviesClient = moviesClient;
        this.albumsClient = albumsClient;
        this.movieFixtures = movieFixtures;
        this.albumFixtures = albumFixtures;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        logger.info(" ENTERING HERE : "+model);
        for (MovieInfo movie : movieFixtures.load()) {
            logger.info(" INSIDE MOVIE HERE : "+movie);
            moviesClient.addMovie(movie);
            logger.info(" AFTER MOVIE HERE : ");
        }

        for (AlbumInfo album : albumFixtures.load()) {
            logger.info(" INSIDE ALBUM HERE : "+album);
            albumsClient.addAlbum(album);
            logger.info(" AFTER ALBUM HERE : ");
        }

        logger.info(" MOVIES CLIET GET MOVIES HERE : "+moviesClient.getMovies());
        logger.info(" ALBUMS CLIET GET ALBUMS HERE : "+albumsClient.getAlbums());
        model.put("movies", moviesClient.getMovies());
        model.put("albums", albumsClient.getAlbums());

        return "setup";
    }
}
