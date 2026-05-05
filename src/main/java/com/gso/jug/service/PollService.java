package com.gso.jug.service;

import com.gso.jug.model.Poll;
import com.gso.jug.model.PollOption;
import com.gso.jug.repository.PollOptionRepository;
import com.gso.jug.repository.PollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PollService {

    private final PollRepository pollRepository;
    private final PollOptionRepository pollOptionRepository;

    public Poll getActivePoll() {
        return pollRepository.findFirstByActiveTrue();
    }

    public Poll getPollById(Long pollId) {
        return pollRepository.findById(pollId).orElse(null);
    }

    public Poll vote(Long optionId) {
        if (!pollOptionRepository.existsById(optionId)) {
            return null;
        }
        pollOptionRepository.incrementVoteCount(optionId);
        PollOption option = pollOptionRepository.findById(optionId).orElse(null);
        if (option != null) {
            return pollRepository.findById(option.getPoll().getId()).orElse(null);
        }
        return null;
    }
}
