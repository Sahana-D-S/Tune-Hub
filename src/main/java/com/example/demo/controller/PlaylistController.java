package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;

@Controller
public class PlaylistController {
	@Autowired
	SongService service;
	
	@Autowired
	PlaylistService playlistService;
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
	List<Song> songsList=service.fetchAllSongs();
	model.addAttribute("songs", songsList);
	System.out.println("playlist addded");
	return "createPlaylist";

}
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		playlistService.addPlaylist(playlist);
		List<Song> songList = playlist.getSongs();
		for(Song s:songList) {
			s.getPlaylists().add(playlist);
			service.updateSong(s);
			//update song object in db
		}
				
		
		
		return "adminHome";
	}
	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model) {
		List<Playlist> allPlaylists=playlistService.fetchAllPlaylists();
		model.addAttribute("allPlaylists", allPlaylists);
		return "displayPlaylist";
	}
	
}