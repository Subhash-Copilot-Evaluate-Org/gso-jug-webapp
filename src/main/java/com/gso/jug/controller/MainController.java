package com.gso.jug.controller;

import com.gso.jug.model.Poll;
import com.gso.jug.model.Raffle;
import com.gso.jug.repository.RaffleRepository;
import com.gso.jug.service.PollService;
import com.gso.jug.service.RaffleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final RaffleRepository raffleRepository;
	private final RaffleService raffleService;
	private final PollService pollService;
	
	@GetMapping("/")
	public String getDashBoard(Model model) {
		return "home-page/home-main";
	}
	
	@GetMapping("/jobs")
	public String getJobsBoard(Model model) {
		return "jobs-page/jobs-main";
	}
	
	@GetMapping("/location")
	public String getLocationPage(Model model) {
		return "location-page/location-main";
	}

	@PostMapping(value = "/raffle" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Raffle enterRaffle(@RequestBody Raffle raffle) {
		Raffle exisiting = raffleRepository.findByEmail(raffle.getEmail().trim());

		if(null != exisiting) {
			return exisiting;
		}

		return raffleRepository.save(raffle);
	}

	@GetMapping(value = "/draw", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Raffle draw(){
	  return raffleService.draw();
	}

	@GetMapping("/raffle-main")
	public String getRafflle(Model model) {
		List<Raffle> raffleList = raffleRepository.findAll();
		raffleList.sort((a,b) -> (a.isPicked() && b.isPicked()) || (!a.isPicked() && !b.isPicked()) ? a.getId().compareTo(b.getId()) : a.isPicked() ? -1 : 1);
		model.addAttribute("raffleList", raffleList );
		return "raffle-page/raffle-main";
	}

	@GetMapping("/poll")
	public String getPollPage(Model model) {
		Poll activePoll = pollService.getActivePoll();
		model.addAttribute("poll", activePoll);
		return "poll-page/poll-main";
	}

	@PostMapping(value = "/poll/vote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Poll> vote(@RequestBody Map<String, Long> request) {
		Long optionId = request.get("optionId");
		Poll result = pollService.vote(optionId);
		if (result == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/poll/results/{pollId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Poll> getPollResults(@PathVariable Long pollId) {
		Poll poll = pollService.getPollById(pollId);
		if (poll == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(poll);
	}

}
