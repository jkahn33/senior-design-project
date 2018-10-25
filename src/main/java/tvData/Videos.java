package tvData;

import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="video")
public class Videos 
{
	@Id
	@Column(name="vid_id")
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private int id;
	
	@Column
	private String videoName;
	@Column
	private String videoPath;
	@Column
	private Timestamp uploadDate;
	@Column;
	private String adminID;
	
	public Videos(String videoName, String videoPath, Timestamp uploadDate, String adminID)
	{
		this.videoName = videoName;
		this.videoPath = videoPath;
		this.uploadDate = uploadDate;
		this.adminID = adminID;
		
	}
}
