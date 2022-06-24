 JSP Pages are in  (https://github.com/NoLongerDoomer/DeltaX/tree/main/src/main/webapp/WEB-INF/views)
 Java DAO here : (https://github.com/NoLongerDoomer/DeltaX/blob/main/src/main/java/com/deltax/app/dao/SpotifyAppDaoImpl.java)
 Controller here : (https://github.com/NoLongerDoomer/DeltaX/blob/main/src/main/java/com/deltax/app/controller/HomeController.java)
 
Here in this Readme file i'll attach screenshots as well as important queries.

Database Used : MySQL

![Tables](https://github.com/NoLongerDoomer/DeltaX/blob/main/screenshots/tables.png)

 Main User Interface : 
 
 ![Main Page](https://github.com/NoLongerDoomer/DeltaX/blob/main/screenshots/main-page.png)
 
 Rate Tracks (Upon Clicking rate button) :
 
 ![Rate Tracks](https://github.com/NoLongerDoomer/DeltaX/blob/main/screenshots/rate-track.png)
 
 Add tracks Page : 
 
 ![Add Tracks](https://github.com/NoLongerDoomer/DeltaX/blob/main/screenshots/add-tracks.png)
 
 Add Artist Page Using Modal and AJAX : 
 
 ![Add Artist](https://github.com/NoLongerDoomer/DeltaX/blob/main/screenshots/add-artist.png)
 
 SQL Query Used to get Top 10 tracks : 
 
select avg(r.ratings) as ratings, t.track_name, t.image_url, t.date_of_release, group_concat(distinct a.artist_name order by a.artist_id) as artists from m_tracks t 
left outer join artist_track_list atl on t.track_id = atl.track_id left outer join m_artist a on atl.artist_id = a.artist_id
left outer join user_reviews r on r.track_id = t.track_id  group by t.track_id order by avg(r.ratings) desc limit 10;

SQL Query Used to Get top artists : 

select a.artist_name , a.date_of_birth, group_concat(distinct t.track_name order by r.ratings) as tracks from m_tracks t
left outer join artist_track_list atl on t.track_id = atl.track_id left outer join m_artist a on atl.artist_id = a.artist_id
left outer join user_reviews r on r.track_id = t.track_id  group by a.artist_id order by avg(r.ratings) desc limit 10;
