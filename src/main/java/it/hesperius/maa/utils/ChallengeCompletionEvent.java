package it.hesperius.maa.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import it.hesperius.maa.MAA;

public class ChallengeCompletionEvent extends Event {

	private final Player playerName;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public ChallengeCompletionEvent(Player playerName) {
		this.playerName = playerName; 
		MAA.getChallenge(playerName).setBool(true);
		RequestHandler.deleteLink(MAA.getChallenge(playerName).getID());
		MAA.hasCompleted(playerName);
	}

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public Player getPlayer() {
		return this.playerName;
	}

}
