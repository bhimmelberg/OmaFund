package datamodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @since J2SE-1.8
 CREATE TABLE appointmentKucera (
  id INT NOT NULL AUTO_INCREMENT,    
  DATE VARCHAR(30) NOT NULL,   
  TIME VARCHAR(30) NOT NULL,
  LOCATION VARCHAR(30) NOT NULL,
  DESCRIPTION VARCHAR(50) NOT NULL,   
  PRIMARY KEY (id));
 */
@Entity
@Table(name = "appointmentKucera")
public class UserModel implements Comparable<UserModel>{

   @Id  // primary key
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id") // specify the column name. Without it, it will use method name
   private Integer id;

   @Column(name = "DATE")
   private String DATE;

   @Column(name = "TIME")
   private String TIME;
   
   @Column(name = "LOCATION")
   private String LOCATION;
   
   @Column(name = "DESCRIPTION")
   private String DESCRIPTION;

   public UserModel() {
   }

   public UserModel(Integer id, String DATE, String TIME, String LOCATION, String DESCRIPTION) {
      this.id = id;
      this.DATE = DATE;
      this.TIME = TIME;
      this.LOCATION = LOCATION;
      this.DESCRIPTION = DESCRIPTION;
   }

   public UserModel(String DATE, String TIME, String LOCATION, String DESCRIPTION) {
	  this.DATE = DATE;
	  this.TIME = TIME;
	  this.LOCATION = LOCATION;
	  this.DESCRIPTION = DESCRIPTION;
   }

   public Integer getId() {
      return id;
   }
   
   public void setId(Integer id) {
      this.id = id;
   }

   public String getDate() {
      return DATE;
   }

   public void setDate(String date) {
      this.DATE = date;
   }
   
   public String getTime() {
      return TIME;
   }

   public void setTime(String time) {
      this.TIME = time;
   }
	   
   public String getLocation() {
      return LOCATION;
   }

   public void setLocation(String location) {
      this.LOCATION = location;
   }
		   
   public String getDescription() {
      return DESCRIPTION;
   }

   public void setDescription(String description) {
      this.DESCRIPTION = description;
   }

   @Override
   public String toString() {
      return "Appointment: " + this.id + ", " + this.DATE + ", " + this.TIME + ", " + this.LOCATION + ", " + this.DESCRIPTION;
   }
   
   @Override
   public int compareTo(UserModel o) {
	   SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	   try {
		   Date date1 = dateFormat.parse(this.DATE);
		   Date date2 = dateFormat.parse(o.DATE);
		   return date1.compareTo(date2);
	   }
	   catch (ParseException e)
	   {
		   return 0;
	   }
   }
}