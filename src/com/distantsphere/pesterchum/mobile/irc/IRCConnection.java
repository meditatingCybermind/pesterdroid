package com.distantsphere.pesterchum.mobile.irc;

import java.util.regex.Pattern;

import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class IRCConnection extends PircBot {
	
	private Pattern mInitialMatch;
	
	private boolean isQuitting = false;
	private final Object isQuittingLock = new Object();
	
	public IRCConnection() {
		this.setAutoNickChange(true);
	}
	
	public void setNickname(String nickname) {
		this.setName(nickname);
	}
	
	public void setRealName(String realname) {
		// For some reason Pircbot uses version for "real name".
		// The real "version value is provided by onVersion()
		this.setVersion(realname);
	}
	
	@Override
	protected void onVersion(String sourceNick, String sourceLogin, String sourceHostname, String target) {
		this.sendRawLine(
			"NOTICE " + sourceNick + " :\u0001VERSION " +
			"Pesterchum Mobile 1.0" +
			"\u0001"
		);
	}
	
	public void setIdent(String ident) {
		this.setLogin(ident);
	}
	
	@Override
	public void onConnect() {
		
	}
	
	@Override
	public void onDisconnect() {
		super.onDisconnect();
	}
	
	@Override
	protected void onNickChange(String oldNick, String login, String hostname, String newNick) {
		
	}
	
	@Override
	protected void onJoin(String target, String sender, String login, String hostname) {
		
	}
	
	@Override
	protected void onPart(String target, String sender, String login, String hostname) {
		
	}
	
	@Override
	protected void onKick(String target, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
		
	}
	
	@Override
	protected void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason) {
		
	}
	
	@Override
	protected void onUserList(String channel, User[] users) {
		
	}
	
	@Override
	protected void onMessage(String target, String sender, String login, String hostname, String text) {
		
	}
	
	@Override
	protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) {
		
	}
	
	@Override
	protected void onMode(String target, String sourceNick, String sourceLogin, String sourceHostname, String mode) {
		// Disabled as it doubles events (e.g. onOp and onMode will be called)
	}
	
	@Override
	protected void onOp(String target, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		
	}
	
	@Override
	protected void onDeop(String target, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		
	}
	
	@Override
	protected void onVoice(String target, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		
	}
	
	@Override
	protected void onDeVoice(String target, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		
	}
	
	@Override
	protected void onSetSecret(String target, String sourceNick, String sourceLogin, String sourceHostname) {
		
	}
	
	@Override
	protected void onRemoveSecret(String target, String sourceNick, String sourceLogin, String sourceHostname) {
		
	}
	
	@Override
	protected void onSetInviteOnly(String target, String sourceNick, String sourceLogin, String sourceHostname) {
		
	}
	
	@Override
	protected void onRemoveInviteOnly(String target, String sourceNick, String sourceLogin, String sourceHostname) {
		
	}
	
	@Override
	protected void onSetModerated(String target, String sourceNick, String sourceLogin, String sourceHostname) {
		
	}
	
	@Override
	protected void onRemoveModerated(String target, String sourceNick, String sourceLogin, String sourceHostname) {
		
	}
	
	private void updateInitialMatchPattern() {
		
		//mInitialMatch = Pattern.compile(pattern)
	}
}
