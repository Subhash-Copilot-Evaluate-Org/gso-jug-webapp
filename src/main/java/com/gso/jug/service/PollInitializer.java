package com.gso.jug.service;

import com.gso.jug.model.Poll;
import com.gso.jug.model.PollOption;
import com.gso.jug.repository.PollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class PollInitializer {

    private final PollRepository pollRepository;

    @PostConstruct
    public void initializeDefaultPoll() {
        if (pollRepository.count() == 0) {
            Poll poll = new Poll();
            poll.setQuestion("What Java version are you primarily using in production?");
            poll.setActive(true);

            PollOption opt1 = new PollOption();
            opt1.setOptionText("Java 8");
            opt1.setPoll(poll);

            PollOption opt2 = new PollOption();
            opt2.setOptionText("Java 11");
            opt2.setPoll(poll);

            PollOption opt3 = new PollOption();
            opt3.setOptionText("Java 17");
            opt3.setPoll(poll);

            PollOption opt4 = new PollOption();
            opt4.setOptionText("Java 21 or later");
            opt4.setPoll(poll);

            poll.setOptions(Arrays.asList(opt1, opt2, opt3, opt4));
            pollRepository.save(poll);
        }
    }
}
