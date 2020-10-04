/*
 * Song.java by:
 * Hunter Brady & Alex Rivera
 * hb306 & aer136
 */

package application;

public class Song implements Comparable<Song>{

	public String songName;
	public String songArtist;
	public String album;
	public String year;

	public Song(String songName, String songArtist)  {
		this.songName = songName;
		this.songArtist = songArtist;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getSongArtist() {
		return songArtist;
	}

	public void setSongArtist(String songArtist) {
		this.songArtist = songArtist;
	}

	public String getDetails() {
		return album + " " + year;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public void setYear(String year) {
		this.year = year;
	}
	public void isEmpty() {
		
	}
	//To use Collections.sort(obslist<Songs>) we must make our own compareTo method givene by the interface Comparable<T>
	public int compareTo(Song s) {
		int val = songName.compareTo(s.songName);
		if(val==0) {
			return songArtist.compareTo(s.songArtist);
		}
		return songName.compareTo(s.songName);
		
	}
	@Override
	public String toString() {
		return String.format("%s - %s", songName, songArtist);
	}

}
