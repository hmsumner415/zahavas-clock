package org.zahavas.chessclock.source;

import java.text.DecimalFormat;


public class TaskTime {

	private String taskName;
	private int endTimeCounter;
	private int taskTimecounter;
	private boolean IsActive = false;
	private int startTimeCounter;
	
	public int getEndTimeCounter() {
		return endTimeCounter;
	}


	public void setEndTimeCounter(int endTC) {
		endTimeCounter = endTC;
	}


	
	public TaskTime(String ataskName, int startTC) {
		setTaskName(ataskName);
		setStartTimeCounter(startTC);
		DisplayTask();
		setIsActive(true);
		taskTimecounter = 0;
	}

	
	public int getStartTimeCounter() {
		return startTimeCounter;
	}


	public void setStartTimeCounter(int startTC) {
		startTimeCounter = startTC;
	}


	void DisplayTask()
	{
		String ST = Integer.toString(getStartTimeCounter());
		String DS = Integer.toString(getTaskTimecounter());
		System.out.println(DS+getTaskName()+ST);
	}
	
	void startTask()
	{}
	
	void endTask()
	{}
	

	public boolean isIsActive() {
		return IsActive;
	}


	public void setIsActive(boolean aisActive) {
		 IsActive = aisActive;
	}


	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String ataskName) {
		taskName = ataskName;
	}

	public int getTaskTimecounter() {
		return taskTimecounter;
	}

	public void setTaskTimecounter() {
		taskTimecounter = taskTimecounter + (endTimeCounter - startTimeCounter);
	}
	
	public void setTaskTimeCounterIncrement ()
	{
		taskTimecounter++;
	}
	
	public int getHours()
	{
		int aCounter = taskTimecounter;
		int hourCounter = aCounter /(60*60);
		return hourCounter;
	}
	
	public int getMinutes()
	{
		int aCounter = taskTimecounter;
		int minuteCounter =  aCounter - getHours() * 60*60;
		minuteCounter = minuteCounter/60;
		return minuteCounter;
	}
	
	public int getSeconds()
	{
		int secondCounter = taskTimecounter % 60;
		return secondCounter;
	}
	
	public String  convertToHourMinSec(){
		/**
		 * Class: TaskTime
		 * Method: convertToHourMinSec
		 * Dependencies: DecimalFormat
		 * 
		 * Purpose: returns TaskTimeCounter in HH:MM:SS format
		 * 
		 */
			
		int hourCounter = getHours();
		int minuteCounter = getMinutes();
		double secondCounter = getSeconds();
		
		DecimalFormat aFormatter = new DecimalFormat("00");
		String fHourCounter = aFormatter.format(hourCounter);
		String fMinuteCounter = aFormatter.format(minuteCounter);
		String fSecondCounter = aFormatter.format(secondCounter);
		String timeString = fHourCounter + ": " + fMinuteCounter + ": " + fSecondCounter;
		//String timeString = String.format("%d : %d : %d", hourCounter, minuteCounter, secondCounter);
		return timeString;
	}
	
}
