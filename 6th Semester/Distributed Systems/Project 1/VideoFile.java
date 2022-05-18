import java.io.Serializable;
import java.util.*;

public class VideoFile implements Serializable {
	private String videoName;
	private String channelName;
	private String dateCreated;
	private String length;
	private String framerate;
	private String frameWidth;
	private String frameHeight;
	private ArrayList<String> associatedHashtags = new ArrayList<String>() ;
	private byte[] videoFileChunk;

	public VideoFile(String videoName) {
		this.videoName=videoName;
	}

	public VideoFile(String videoName, String channelName, String dateCreated, String length, String framerate, String frameWidth, String frameHeight) {
		this.videoName = videoName;
		this.channelName = channelName;
		this.dateCreated = dateCreated;
		this.length = length;
		this.framerate = framerate;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
	}

	public VideoFile(String videoName, String channelName, String dateCreated, String length) {
		this.videoName = videoName;
		this.channelName = channelName;
		this.dateCreated = dateCreated;
		this.length = length;
	}
	
	public String getVideoName(){
		return videoName;
	}
	
	public String getChannelName(){
		return channelName;
	}
	
	public String getDateCreated(){
		return dateCreated;
	}
	
	public String getFramerate(){
		return framerate;
	}
	
	public String getFrameWidth(){
		return frameWidth;
	}
	
	public String getFrameHeight(){
		return frameHeight;
	}

	public String getLength(){
		return length;
	}
	
	public ArrayList<String> getAssociatedHashtags(){
		return associatedHashtags;
	}
	

	public byte[] getVideoFileChunk(){
		return videoFileChunk;
	}
	
	public void setVideoName(String videoName){
		this.videoName = videoName;
	}
	
	public void setChannelName(String channelName){
		this.channelName = channelName;
	}
	
	public void setDateCreated(String dateCreated){
		this.dateCreated = dateCreated;
	}
	
	public void setFramerate(String framerate){
		this.framerate = framerate;
	}
	
	public void setFrameWidth(String frameWidth){
		this.frameWidth = frameWidth;
	}
	
	public void setFrameHeight(String frameHeight){
		this.frameHeight = frameHeight;
	}
	
	public void setAssociatedHashtags(ArrayList<String> associatedHashtags ){
		this.associatedHashtags = associatedHashtags;
	}
	
	public void setVideoFileChunk(byte[] videoFileChunk){
		this.videoFileChunk = videoFileChunk;
	}
}