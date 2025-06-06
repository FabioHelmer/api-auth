package com.fh.ApiAuth.controllers;

import com.fh.ApiAuth.controllers.dto.CreateTweetDto;
import com.fh.ApiAuth.controllers.dto.FeedDto;
import com.fh.ApiAuth.controllers.dto.FeedItemDto;
import com.fh.ApiAuth.entities.Role;
import com.fh.ApiAuth.entities.Tweet;
import com.fh.ApiAuth.repository.TweetRepository;
import com.fh.ApiAuth.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetController(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedDto> feed(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        var tweets = tweetRepository.findAll(
                        PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
                .map(tweet ->
                        new FeedItemDto(
                                tweet.getId(),
                                tweet.getContent(),
                                tweet.getUser().getUsername())
                );

        return ResponseEntity.ok(new FeedDto(
                tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements()));
    }


    @PostMapping("/tweet")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto dto, JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));
        Tweet tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(dto.content());
        tweetRepository.save(tweet);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long idTweet, JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));
        var tweet = tweetRepository.findById(idTweet)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get().getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if (isAdmin || tweet.getUser().getId().equals(UUID.fromString(token.getName()))) {
            tweetRepository.deleteById(idTweet);

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        return ResponseEntity.ok().build();
    }


}
